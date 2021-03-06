#include "jni.h"
#include "utils/Log.h"
#include "utils/misc.h"


namespace cpu {
     int register_eightman_Cpu(JNIEnv* env);
};

using namespace cpu;

extern "C" jint JNI_OnLoad(JavaVM* vm, void* /* reserved */)
{
    JNIEnv* env = NULL;
    jint result = -1;

    if (vm->GetEnv((void**) &env, JNI_VERSION_1_4) != JNI_OK) {
        ALOGE("GetEnv failed!");
        return result;
    }
    ALOG_ASSERT(env, "Could not retrieve the env!");

    cpu::register_eightman_Cpu(env);
    return JNI_VERSION_1_4;
}
