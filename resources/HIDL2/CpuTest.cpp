#include <vendor/eightman/cpu/1.0/ICpu.h>
#include <hidl/Status.h>
#include <hidl/LegacySupport.h>
#include <utils/misc.h>
#include <hidl/HidlSupport.h>
#include <iostream>
#include <cstdlib>
#include <string>

using ::android::sp;
using vendor::eightman::cpu::V1_0::ICpu;
using android::hardware::hidl_string;

int main(int argc, char *argv[]) {
    if (argc != 2) {
        std::cout << "USAGE ./cpu_client <conservative|powersave|performance|schedutil>\n";
        exit(0);
    }

    android::sp<ICpu> cpuService = ICpu::getService();
    if (cpuService == nullptr) {
        std::cout << "Failed to get cpu service\n";
        exit(-1);
    }

    cpuService->getScalingGovernor([&](hidl_string result) {
        std::cout << "getScalingGovernor:" << result << std::endl;
    });

    bool result = cpuService->setScalingGovernor(argv[1]);
    std::cout << "setScalingGovernor:" << result << std::endl;

    cpuService->getScalingGovernor([&](hidl_string result) {
        std::cout << "getScalingGovernor:" << result << std::endl;
    });

    return 0;
}
