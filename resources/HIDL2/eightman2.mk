DEVICE_FRAMEWORK_COMPATIBILITY_MATRIX_FILE := device/google/cuttlefish/shared/device_framework_matrix.xml

PRODUCT_PACKAGES += \
    vendor.eightman.cpu@1.0 \
    vendor.eightman.cpu@1.0-service

PRODUCT_PACKAGES_DEBUG += eightman_cpu_tester
