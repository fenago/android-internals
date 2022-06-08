#include "Cpu.h"
#include <utils/Log.h>

#include <fcntl.h>
#include <sys/types.h>
#include <sys/stat.h>

namespace vendor {
namespace eightman {
namespace cpu {
namespace V1_0 {
namespace implementation {

// conservative|powersave|performance|schedutil
static const char SCALING_GOVERNOR[] = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_governor";

static int write_value(const char *file, const char *value)
{
    int to_write, written, ret, fd;

    fd = TEMP_FAILURE_RETRY(open(file, O_WRONLY));
    if (fd < 0) {
	ALOGE("write_value failed to open");
        return -1;
    }

    to_write = strlen(value) + 1;
    written = TEMP_FAILURE_RETRY(write(fd, value, to_write));
    if (written == -1) {
        ret = -2;
    } else if (written != to_write) {
        ret = -3;
    } else {
        ret = 0;
    }

    errno = 0;
    close(fd);

    return ret;
}
	
Return<bool> Cpu::setScalingGovernor(const hidl_string& governor) {
    ALOGI("Cpu::setScalingGovernor data=(%s)", governor.c_str());
    return write_value(SCALING_GOVERNOR, governor.c_str()) == 0;
}

Return<void> Cpu::getScalingGovernor(getScalingGovernor_cb _hidl_cb) {
    char str[20];
    int fd;
    ssize_t ret = 0;
    struct stat info;
    void *data = NULL;
    size_t size;

    // If open returns error code EINTR, retry again until error code
    // is not a temporary failure
    fd = TEMP_FAILURE_RETRY(open(SCALING_GOVERNOR, O_RDONLY));
    if (fd < 0) {
        _hidl_cb("error can open fd");
        return Void();
    }

    fstat(fd, &info);
    size = info.st_size;
    data = malloc(size);
    if (data == NULL) {
        _hidl_cb("error can't malloc");
        goto exit;
    }

    ret = read(fd, data, size);
    if (ret < 0) {
    	_hidl_cb("error reading fd");
	goto exit;
    }

    snprintf(str, sizeof(str), "%s", (const unsigned char*)data);
    ALOGI("Cpu::getScalingGovernor data=(%s)", str);
    _hidl_cb(str);

exit:
    close(fd);
    free(data);
    return Void();
}

// Methods from ::android::hidl::base::V1_0::IBase follow.
ICpu* HIDL_FETCH_ICpu(const char* /* name */) {
    return new Cpu();
}

}  // namespace implementation
}  // namespace V1_0
}  // namespace cpu
}  // namespace eightman
}  // namespace vendor
