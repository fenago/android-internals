#define LOG_TAG "vendor.eightman.cpu-service"

#include <android-base/logging.h>
#include <android/binder_process.h>
#include <binder/ProcessState.h>

int main() {
    // Enable vndbinder to allow vendor-to-vendor binder calls.
    android::ProcessState::initWithDriver("/dev/vndbinder");

    ABinderProcess_startThreadPool();

    LOG(INFO) << "Cpu aidl service for eightman is starting.,,";

    LOG(INFO) << "TO-DO: Initialized eightman cpu";

    ABinderProcess_joinThreadPool();

    return EXIT_FAILURE;  // should not reach
}
