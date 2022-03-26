Establishing a Build Environment
================================


This section describes how to set up your local work environment to
build the Android source files. You must use Linux; building under
either MacOS or Windows isn\'t supported. Keep in mind the important
change to the `/usr/bin/python` binary as
well.

For an overview of the entire code-review and code-update process, see
[](https://source.android.com/setup/contribute/life-of-a-patch).

**Note:** All commands in this site are preceded by a dollar sign (\$)
to differentiate them from output or entries within files. Use the
*Click to copy* feature located at the top right of each command box to
copy all lines without the dollar signs, or triple-click each line to
copy it individually without the dollar sign.

Choosing a branch
-------------------------------------------------------------------

Some requirements for the build environment are determined by the
version of the source code that you plan to compile. For a full list of
available branches, see [Build
Numbers](https://source.android.com/setup/start/build-numbers). You can
also choose to download and build the latest source code (called
`master`), in which case you simply omit the
branch specification when you initialize the repository.

After selecting a branch, follow the appropriate instructions below to
set up your build environment.

[Setting up a Linux build environment]
--------------------------------------------------------------------------------------

These instructions apply to all branches, including
`master`.

The Android build is routinely tested in house on Ubuntu LTS (14.04) and
Debian testing. Most other distributions should have the required build
tools available.

For Gingerbread (2.3.x) and higher versions, including the
`master` branch, a 64-bit environment is
required. Older versions can be compiled on 32-bit systems.

**Note:** See
[Requirements](https://source.android.com/setup/build/requirements) for
the complete list of hardware and software requirements, then follow the
detailed instructions for Ubuntu below.

### Installing required packages (Ubuntu 18.04)

You need a 64-bit version of Ubuntu.


```
sudo apt-get update

sudo apt-get install git-core gnupg flex bison build-essential zip curl zlib1g-dev gcc-multilib g++-multilib libc6-dev-i386 libncurses5 lib32ncurses5-dev x11proto-core-dev libx11-dev lib32z1-dev libgl1-mesa-dev libxml2-utils xsltproc unzip fontconfig
```

