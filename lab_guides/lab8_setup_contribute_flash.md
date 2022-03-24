

Android Flash Tool
==================

[Android Flash Tool](https://flash.android.com/) allows you
to flash an Android build to your device for development and testing. To
get started, you need a development machine and an Android device.

[Minimum requirements to run the flashing tool]
-----------------------------------------------------------------------------------------------

Your development machine must meet these requirements:

-   Browser: Any browser supporting
    [WebUSB](https://caniuse.com/#search=Webusb), such as
    [Chrome](https://www.google.com/chrome/) or Edge 79+.
-   Platforms:
    -   Linux
    -   macOS
    -   Chrome OS
    -   Windows (requires an additional USB driver)

### [Installing Windows drivers]

To work with fastboot and flash devices on a Windows machine, you need a
customized USB driver from the Android SDK. For more details, see
[Install OEM USB
drivers](https://developer.android.com/studio/run/oem-usb) on
the Android Developers site.

**Caution:** If you have [Android Debug Bridge
(adb)](https://developer.android.com/studio/command-line/adb)
installed on your machine, stop the adb service with the command below
before proceeding as the service interferes with the flashing process.



``` 
adb kill-server
```

[Device requirements]
---------------------------------------------------------------------

You can flash a new version of Android to these supported devices:

-   [DragonBoard 845c
    (db845c)](https://wiki.linaro.org/AOSP/db845c)
-   [HiKey 960](https://source.android.com/setup/build/devices)
-   [HiKey](https://source.android.com/setup/build/devices)
-   [Glass Enterprise Edition
    2](https://www.google.com/glass)
    [builds](https://developers.google.com/glass-enterprise/downloads/system-images)
-   Pixel 2 and newer

[Preparing your device]
-----------------------------------------------------------------------

Before you can flash a build to your device, you must prepare your
device:

1.  Enable [**Developer options** and **USB
    debugging**](https://developer.android.com/studio/debug/dev-options#enable).
2.  Enable **OEM Unlocking** in the **Developer options** menu. If your
    bootloader is already unlocked, this option is grayed out with
    **Bootloader is already unlocked**.
3.  If you\'re having trouble enabling OEM Unlocking, make sure:
    1.  Your device is connected to the internet.
    2.  Your device has checked in with Google, which may not be the
        case just because your device recently connected to the
        internet. To force a check in, enter **\*\#\*\#CHECKIN\#\*\#\***
        (\*\#\*\#2432546\#\*\#\*) in the Dialer (no SIM required). After
        entering the number (no need to press call), the text disappears
        and a success notification appears.

**Note:** Some devices require carrier intervention to be unlocked.
Contact your carrier for more details.

[Flashing your device]
----------------------------------------------------------------------

1.  Connect your device directly to your development machine (no hubs,
    adapters, extenders, or monitors).
2.  Open [flash.android.com](https://flash.android.com/) in a
    browser on your development machine. It opens to the Welcome page.
3.  Allow the flash tool to communicate with your test device through
    adb by accepting the popup that says **Allow site access to your ADB
    keys in order to communicate with devices**.
4.  Click **Add new device**.
5.  Select your device from the list and click **Connect**. This list
    may not contain the full device name.
6.  On your device's screen, select **Always allow from this computer**
    and click **OK** to accept the USB debugging connection.
7.  Select the connected device in your browser.
8.  Search for and select your desired build from the list. You can also
    select options, such as wiping the device or force flashing all
    partitions.
9.  Click **Install** to start the process. The device reboots and
    enters fastboot mode.
10. After **Flash Complete** appears, disconnect the device from the USB
    cable.

**Note:** If this is the first time you\'re flashing this device on this
machine, your machine loses the connection to the device and asks you to
find it again. The next time you flash the same device on that same
machine, you won\'t see the request to refind the device. After
reconnecting, the flashing resumes and starts downloading the images and
performing all the required steps.

### [USB transfer errors]

Sometimes Android devices have trouble communicating through certain USB
ports or hubs due to the high throughput of data. To improve
reliability:

-   Don\'t use USB hubs. This includes connections through monitors.
-   Don\'t use USB extension cables or adapters if possible.
-   Try a different USB port. Rear ports are often more reliable than
    front ones.
-   If you\'re using a USB C port, try a USB A port instead.

[Back to latest public build]
-----------------------------------------------------------------------------

If you want to return your Pixel device to the public build you can
flash back to the latest factory image and lock your device
[here](https://flash.android.com/back-to-public).
