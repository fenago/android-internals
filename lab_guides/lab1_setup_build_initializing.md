Establishing a Build Environment
================================


This section describes how to set up your local work environment to
build the Android source files. You must use Linux; building under
either MacOS or Windows isn\'t supported. Keep in mind the important
change to the `/usr/bin/python` binary as well.


Setting up a Linux build environment
-------------------------------------

These instructions apply to all branches, including
`master`.

The Android build is routinely tested in house on Ubuntu LTS (14.04) and
Debian testing. Most other distributions should have the required build
tools available.

For Gingerbread (2.3.x) and higher versions, including the
`master` branch, a 64-bit environment is
required. Older versions can be compiled on 32-bit systems.

**Hardware requirements**

Your development workstation should meet or exceed these hardware requirements:

- A 64-bit environment is required for Android 2.3.x (Gingerbread) and higher versions, including the master branch. You can compile older versions on 32-bit systems.
- At least 250GB of free disk space to check out the code and an extra 150 GB to build it. If you conduct multiple builds, you need additional space.
- At least 16 GB of available RAM is required, but Google recommends 64 GB.


### Installing required packages (Ubuntu 18.04)

You need a 64-bit version of Ubuntu.


```
sudo apt-get update

sudo apt-get install git-core gnupg flex bison build-essential zip curl zlib1g-dev gcc-multilib g++-multilib libc6-dev-i386 libncurses5 lib32ncurses5-dev x11proto-core-dev libx11-dev lib32z1-dev libgl1-mesa-dev libxml2-utils xsltproc unzip fontconfig
```

