#ifndef VENDOR_EIGHTMAN_CPU_V1_0_CPU_H
#define VENDOR_EIGHTMAN_CPU_V1_0_CPU_H

#include <vendor/eightman/cpu/1.0/ICpu.h>

namespace vendor {
namespace eightman {
namespace cpu {
namespace V1_0 {
namespace implementation {

using ::android::hardware::hidl_string;
using ::android::hardware::Return;
using ::android::hardware::Void;
using ::android::sp;
using ::vendor::eightman::cpu::V1_0::ICpu;

struct Cpu : public ICpu {
public:
    // Methods from ::vendor::eightman::cpu::V1_0::ICpu follow.
    Return<bool> setScalingGovernor(const hidl_string& governor) override;
    Return<void> getScalingGovernor(getScalingGovernor_cb _hidl_cb) override;
};

extern "C" ICpu* HIDL_FETCH_ICpu(const char* name);

}  // namespace implementation
}  // namespace V1_0
}  // namespace cpu
}  // namespace eightman
}  // namespace vendor

#endif
