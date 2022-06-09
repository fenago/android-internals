#include "Cpu.h"
#include <utils/Log.h>

#include <fcntl.h>
#include <sys/types.h>
#include <sys/stat.h>

namespace aidl::vendor::eightman::cpu {

// conservative|powersave|performance|schedutil
static const char SCALING_GOVERNOR[] = \
        "/sys/devices/system/cpu/cpu0/cpufreq/scaling_governor";

static int write_value(const char *file, const char *value)
{
    int to_write, written, ret, fd;

    fd = TEMP_FAILURE_RETRY(open(file, O_WRONLY));
    if (fd < 0) {
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

ndk::ScopedAStatus Cpu::getScalingGovernor(std::string* _aidl_return) {
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
        ndk::ScopedAStatus::fromServiceSpecificError(-1);
    }

    fstat(fd, &info);
    size = info.st_size;
    data = malloc(size);
    if (data == NULL) {
        *_aidl_return = "error can't malloc";
        goto exit;
    }

    ret = read(fd, data, size);
    if (ret < 0) {
        *_aidl_return = "error reading fd";
        goto exit;
    }

    snprintf(str, sizeof(str), "%s", (const unsigned char*)data);
    ALOGI("Cpu::getScalingGovernor data=(%s)", str);
    *_aidl_return = str;

exit:
    close(fd);
    free(data);
    return ndk::ScopedAStatus::ok();
}

ndk::ScopedAStatus Cpu::setScalingGovernor(const std::string& in_governor, bool* _aidl_return) {
    ALOGI("Cpu::setScalingGovernor data=(%s)", in_governor.c_str());
    *_aidl_return = write_value(SCALING_GOVERNOR, in_governor.c_str()) == 0;
    return ndk::ScopedAStatus::ok();
}

}  // namespace aidl::vendor::eightman::cpu
