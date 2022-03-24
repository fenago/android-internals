Android Debug Bridge (ADB)
==========================


[Android Debug Bridge
(ADB)](https://developer.android.com/studio/command-line/adb)
lets you connect your development workstation directly to your Android
device so you can install packages and evaluate your changes. To learn
the details of ADB, review its
[README](https://cs.android.com/android/platform/superproject/+/master:packages/modules/adb/README.md).

[Confirming you have ADB]
-------------------------------------------------------------------------

The `adb` tool is part of the Android Open
Source Project (AOSP). ADB is available after you\'ve
[built](https://source.android.com/setup/build/adb#build-adb) or
[downloaded](https://source.android.com/setup/build/adb#download-adb)
ADB.

To confirm you have the ADB binary, run the following command in a
terminal:



``` 
adb version
```

If you have ADB installed, you see its version in the output.

[Building ADB]
--------------------------------------------------------------

If you don\'t yet have ADB, [build
Android](https://source.android.com/setup/build/building). In summary:

1.  Check out the Android source code.

2.  Set up your environment and build Android:

    

    ``` 
    source build/envsetup.sh
    lunch <target>
    m
    ```

Downloading ADB
---------------

You can also download ADB as part of [Android
Studio](https://developer.android.com/studio) or in the
standalone [SDK Platform
Tools](https://developer.android.com/studio/releases/platform-tools#downloads).

[Enable USB debugging]
----------------------------------------------------------------------

Now [enable USB Debugging](https://developer.android.com/studio/command-line/adb#Enabling)
on the Android device to allow the connection. You\'re also prompted
when plugging the device into your machine.
