#define LOG_TAG "vendor.eightman.cpu@1.0-service"

#include <vendor/eightman/cpu/1.0/ICpu.h>
#include <log/log.h>
#include <hidl/HidlTransportSupport.h>

#include "Cpu.h"

using android::sp;
using android::status_t;
using android::OK;

// libhwbinder:
using android::hardware::configureRpcThreadpool;
using android::hardware::joinRpcThreadpool;

// Generated HIDL files
using vendor::eightman::cpu::V1_0::ICpu;
using vendor::eightman::cpu::V1_0::implementation::Cpu;

using namespace vendor;

int main(int /* argc */, char** /* argv */) {
    ALOGI("Cpu Service 1.0 for eightman is starting.");

    // Android Strong Pointer (don't GC until exit)
    android::sp<ICpu> service = new Cpu();
    if (service == nullptr) {
        ALOGE("Can not create an instance of ICpu HAL, exiting.");
        return 1;
    }

    // system/libhidl/transport/include/hidl/HidlTransportSupport.h
    // Configures the threadpool used for handling incoming RPC calls in this process:
    // @param maxThreads maximum number of threads in this process
    // @param callerWillJoin whether the caller will join the threadpool later.
    configureRpcThreadpool(1, true /*callerWillJoin*/);

    // registerAsService calls registerAsServiceInternal in
    // system/libhidl/transport/ServiceManagement.cpp
    // registerAsServiceInternal registers with hwservicemanager
    status_t status = service->registerAsService();
    if (status != OK) {
        ALOGE("Could not register service for ICpu HAL (%d), exiting.", status);
        return 1;
    }
    ALOGI("Cpu Service is ready");

    // system/libhidl/transport/include/hidl/HidlTransportSupport.h
    // Joins a threadpool that you configured earlier
    joinRpcThreadpool();

    // In normal operation, we don't expect the thread pool to exit
    ALOGE("Cpu Service is shutting down");
    return 1;
}
