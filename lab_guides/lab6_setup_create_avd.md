

Using Android Emulator Virtual Devices
======================================

You can use Android Emulator to create emulations of Android devices
that run your own custom Android system images. You can also share your
custom Android system images so that other people can run emulations of
them. In addition, you can add multi-display support to Android Emulator
emulations.

Android Emulator architecture
-------------------------------------------------------------------------------

Android Emulator allows you to run emulations of Android devices on
Windows, macOS or Linux machines. The Android Emulator runs the Android
operating system in a virtual machine called an Android Virtual Device
(AVD). The AVD contains the full [Android software
stack], and it runs as
if it were on a physical device. Figure 1 is a diagram of the Android
Emulator\'s high-level architecture. For more information about the
emulator, see [Run apps on the Android
Emulator](https://developer.android.com/studio/run/emulator){.external}.

![](./images/emulator-design.png)

**Figure 1.** Android Emulator architecture

Building AVD images
---------------------

Each AVD includes an Android system image, which runs in that AVD. The
AVD Manager includes some system images. And you can build custom AVD
system images from your source code and create device emulations to run
them.


To build and run an AVD system image:

1.  Make sure the Android source is downloaded in `~/aosp` folder.

2.  Build an AVD system image. This is the same process as building an Android device
    system image. For example, to build a x86 32-bit AVD:



    ```
    cd ~/aosp
    source ./build/envsetup.sh
    lunch sdk_phone_x86
    make -j6
    ```

    If you prefer to build an x86 64-bit AVD, run `lunch` for the 64-bit target:

    
    

    ```
    lunch sdk_phone_x86_64
    ```

3.  Run the AVD system image in the Android Emulator:

    
    

    ```
    emulator
    ```

Figure 2 shows an example of the Android Emulator running an AVD.

![](./images/emulator-run-ui.png)

**Figure 2.** Android Emulator running an AVD
