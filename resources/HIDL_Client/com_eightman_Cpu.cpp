#define LOG_TAG "COM_EIGHTMAN_CPU"
#define LOG_NDEBUG 1

#include <vendor/eightman/cpu/1.0/ICpu.h>
#include "jni.h"

#include <limits.h>

#include <android-base/chrono_utils.h>
#include <utils/Timers.h>
#include <utils/misc.h>
#include <utils/String8.h>
#include <utils/Log.h>

using android::hardware::Return;
using android::hardware::Void;
using vendor::eightman::cpu::V1_0::ICpu;
using android::String8;
using android::sp;

namespace cpu {

sp<ICpu> gCpuHal = nullptr;
bool gCpuHalExists = true;
std::mutex gCpuHalMutex;

// Check validity of current handle to the cpu HAL service,
// and call getService() if necessary.
// The caller must be holding gCpuHalMutex.
static bool getCpuHal() {
    if (gCpuHalExists && gCpuHal == nullptr) {
        gCpuHal = ICpu::getService(); // Proxy to the service
        if (gCpuHal != nullptr) {
            ALOGI("Loaded cpu HAL service");
        } else {
            ALOGI("Couldn't load cpu HAL service");
            gCpuHalExists = false;
        }
    }
    return gCpuHal != nullptr;
}

// ----------------------------------------------------------------------------

static void nativeInit(JNIEnv* env, jobject /* obj */) {
    gCpuHalMutex.lock();
    getCpuHal();
    gCpuHalMutex.unlock();
}

// Check if a call to a cpu HAL function failed; if so, log the failure and invalidate the
// current handle to the cpu HAL service. The caller must be holding gCpuHalMutex.
static void processReturn(const Return<void> &ret, const char* functionName) {
    if (!ret.isOk()) {
        ALOGE("%s() failed: cpu HAL service not available.", functionName);
        gCpuHal = nullptr;
    }
}

static jstring getScalingGovernor(JNIEnv* env, jclass /* clazz */) { // static method has jclass
    jstring jstr = NULL;

    std::lock_guard<std::mutex> lock(gCpuHalMutex);
    if (getCpuHal()) {
        android::base::Timer t;

        Return<void> ret = gCpuHal->getScalingGovernor([&](android::hardware::hidl_string result) {
            // Callback
            ALOGD("getScalingGovernor result %s", result.c_str());
            jstr = env->NewStringUTF(result.c_str());  // new Java String object
        });
        processReturn(ret, "getScalingGovernor");

        if (t.duration() > 20ms) {
            ALOGW("Excessive delay in getScalingGovernor");
        }
    }

    return jstr;
}

static jboolean setScalingGovernor(JNIEnv*  env, jclass /* clazz */, jstring governor) {
    ALOGD("nativeSetScalingGovernor");
    std::lock_guard<std::mutex> lock(gCpuHalMutex);
    bool ret = false;
    if (getCpuHal()) {
        android::base::Timer t;

        const char* cGovernor = env->GetStringUTFChars(governor, NULL);
        ret = gCpuHal->setScalingGovernor(cGovernor);
        env->ReleaseStringUTFChars(governor, cGovernor);

        if (t.duration() > 20ms) {
            ALOGW("Excessive delay in setScalingGovernor");
        }
    }

    return ret;
}

// ----------------------------------------------------------------------------

static const char *classPathName = "com/eightman/Cpu";

static JNINativeMethod methods[] = {
    { "nativeInit", "()V", (void*) nativeInit },
    { "nativeGetScalingGovernor", "()Ljava/lang/String;", (void*) getScalingGovernor },
    { "nativeSetScalingGovernor", "(Ljava/lang/String;)Z", (void*) setScalingGovernor },
};

// Register several native methods for one class.
static int registerNativeMethods(JNIEnv* env, const char* className,
    JNINativeMethod* gMethods, int numMethods)
{
    jclass clazz;

    ALOGE("registerNativeMethods '%s'", className);

    clazz = env->FindClass(className);
    if (clazz == NULL) {
        ALOGE("Native registration unable to find class '%s'", className);
        return JNI_FALSE;
    }
    if (env->RegisterNatives(clazz, gMethods, numMethods) < 0) {
        ALOGE("RegisterNatives failed for '%s'", className);
        return JNI_FALSE;
    }

    return JNI_TRUE;
}

int register_eightman_Cpu(JNIEnv* env) {
    if (!registerNativeMethods(env, classPathName, methods,
            sizeof(methods) / sizeof(methods[0]))) {
        return JNI_FALSE;
    }

    return JNI_TRUE;
}

} /* namespace cpu */
