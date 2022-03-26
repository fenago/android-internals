

Establishing a Build Environment {#establishing-a-build-environment .devsite-page-title}
================================


This section describes how to set up your local work environment to
build the Android source files. You must use Linux; building under
either MacOS or Windows isn\'t supported. Keep in mind the important
change to the `/usr/bin/python`{translate="no" dir="ltr"} binary as
well.

For an overview of the entire code-review and code-update process, see
[](https://source.android.com/setup/contribute/life-of-a-patch).

**Note:** All commands in this site are preceded by a dollar sign (\$)
to differentiate them from output or entries within files. Use the
*Click to copy* feature located at the top right of each command box to
copy all lines without the dollar signs, or triple-click each line to
copy it individually without the dollar sign.

[Choosing a branch]{.devsite-heading role="heading" aria-level="2"} {#choosing-a-branch data-text="Choosing a branch" role="presentation"}
-------------------------------------------------------------------

Some requirements for the build environment are determined by the
version of the source code that you plan to compile. For a full list of
available branches, see [Build
Numbers](https://source.android.com/setup/start/build-numbers). You can
also choose to download and build the latest source code (called
`master`{translate="no" dir="ltr"}), in which case you simply omit the
branch specification when you initialize the repository.

After selecting a branch, follow the appropriate instructions below to
set up your build environment.

[Setting up a Linux build environment]{.devsite-heading role="heading" aria-level="2"} {#setting-up-a-linux-build-environment data-text="Setting up a Linux build environment" role="presentation"}
--------------------------------------------------------------------------------------

These instructions apply to all branches, including
`master`{translate="no" dir="ltr"}.

The Android build is routinely tested in house on Ubuntu LTS (14.04) and
Debian testing. Most other distributions should have the required build
tools available.

For Gingerbread (2.3.x) and higher versions, including the
`master`{translate="no" dir="ltr"} branch, a 64-bit environment is
required. Older versions can be compiled on 32-bit systems.

**Note:** See
[Requirements](https://source.android.com/setup/build/requirements) for
the complete list of hardware and software requirements, then follow the
detailed instructions for Ubuntu below.

### [Installing required packages (Ubuntu 18.04)]{.devsite-heading role="heading" aria-level="3"} {#installing-required-packages-ubuntu-1804 data-text="Installing required packages (Ubuntu 18.04)" role="presentation"}

You need a 64-bit version of Ubuntu.

::: {.devsite-code-buttons-container role="group" aria-label="Action buttons"}
:::

``` {.devsite-terminal translate="no" dir="ltr" is-upgraded=""}
sudo apt-get install git-core gnupg flex bison build-essential zip curl zlib1g-dev gcc-multilib g++-multilib libc6-dev-i386 libncurses5 lib32ncurses5-dev x11proto-core-dev libx11-dev lib32z1-dev libgl1-mesa-dev libxml2-utils xsltproc unzip fontconfig
```

### [Installing required packages (Ubuntu 14.04)]{.devsite-heading role="heading" aria-level="3"} {#installing-required-packages-ubuntu-1404 data-text="Installing required packages (Ubuntu 14.04)" role="presentation"}

You need a 64-bit version of Ubuntu (14.04 is recommended).

::: {.devsite-code-buttons-container role="group" aria-label="Action buttons"}
:::

``` {.devsite-terminal translate="no" dir="ltr" is-upgraded=""}
sudo apt-get install git-core gnupg flex bison gperf build-essential zip curl zlib1g-dev gcc-multilib g++-multilib libc6-dev-i386 libncurses5 lib32ncurses5-dev x11proto-core-dev libx11-dev lib32z-dev libgl1-mesa-dev libxml2-utils xsltproc unzip
```

**Note:** If you\'re using LDAP and want to run ART host tests, also
install the `libnss-sss:i386`{translate="no" dir="ltr"} package.

### [Installing required packages (Ubuntu 12.04) ]{.devsite-heading role="heading" aria-level="3"} {#installing-required-packages-ubuntu-1204 data-text="Installing required packages   (Ubuntu 12.04) " role="presentation"}

You may use Ubuntu 12.04 to build older versions of Android. Version
12.04 isn\'t supported on `master`{translate="no" dir="ltr"} or recent
releases.

::: {.devsite-code-buttons-container role="group" aria-label="Action buttons"}
:::

``` {translate="no" dir="ltr" is-upgraded=""}
sudo apt-get install git gnupg flex bison gperf build-essential zip curl libc6-dev libncurses5-dev:i386 x11proto-core-dev libx11-dev:i386 libreadline6-dev:i386 libgl1-mesa-glx:i386 libgl1-mesa-dev g++-multilib mingw32 tofrodos python-markdown libxml2-utils xsltproc zlib1g-dev:i386
sudo ln -s /usr/lib/i386-linux-gnu/mesa/libGL.so.1 /usr/lib/i386-linux-gnu/libGL.so
```

### [Installing required packages (Ubuntu 10.04-11.10) ]{.devsite-heading role="heading" aria-level="3"} {#installing-required-packages-ubuntu-1004-1110 data-text="Installing required   packages (Ubuntu 10.04-11.10) " role="presentation"}

Building on Ubuntu 10.04-11.10 is no longer supported, but may be useful
for building older releases of AOSP.

::: {.devsite-code-buttons-container role="group" aria-label="Action buttons"}
:::

``` {.devsite-terminal translate="no" dir="ltr" is-upgraded=""}
sudo apt-get install git gnupg flex bison gperf build-essential zip curl zlib1g-dev libc6-dev lib32ncurses5-dev ia32-libs x11proto-core-dev libx11-dev lib32readline5-dev lib32z-dev libgl1-mesa-dev g++-multilib mingw32 tofrodos python-markdown libxml2-utils xsltproc
```

On Ubuntu 10.10:

::: {.devsite-code-buttons-container role="group" aria-label="Action buttons"}
:::

``` {.devsite-terminal translate="no" dir="ltr" is-upgraded=""}
sudo ln -s /usr/lib32/mesa/libGL.so.1 /usr/lib32/mesa/libGL.so
```

On Ubuntu 11.10:

::: {.devsite-code-buttons-container role="group" aria-label="Action buttons"}
:::

``` {.devsite-terminal translate="no" dir="ltr" is-upgraded=""}
sudo apt-get install libx11-dev:i386
```

### [Configuring USB access]{.devsite-heading role="heading" aria-level="3"} {#configuring-usb-access data-text="Configuring USB access" role="presentation"}

Install a community-maintained default set of `udev`{translate="no"
dir="ltr"} rules for all Android devices by following the instructions
to [Set up a device for
development](https://developer.android.com/studio/run/device.html#setting-up){.external}.

### [Using a separate output directory]{.devsite-heading role="heading" aria-level="3"} {#using-a-separate-output-directory data-text="Using a separate output directory" role="presentation"}

By default, the output of each build is stored in the
`out/`{translate="no" dir="ltr"} subdirectory of the matching source
tree. You can override this by exporting the `OUT_DIR`{translate="no"
dir="ltr"} environment variable. For example:

::: {.devsite-code-buttons-container role="group" aria-label="Action buttons"}
:::

``` {.devsite-terminal translate="no" dir="ltr" is-upgraded=""}
export OUT_DIR=out_mytarget
```

This will cause the output of the build to be stored in the
`out_mytarget/`{translate="no" dir="ltr"} subdirectory of the source
tree. When using the same source tree to build for multiple targets, it
is recommended to use a separate `OUT_DIR`{translate="no" dir="ltr"} for
each target.

On some machines with multiple storage devices, builds are faster when
storing the source files and the output on separate volumes. For
additional performance, the output can be stored on a file system
optimized for speed instead of crash robustness, because all files can
be regenerated if the file system is corrupted.

To set this up, export the `OUT_DIR_COMMON_BASE`{translate="no"
dir="ltr"} variable to point to the location where your output
directories will be stored.

::: {.devsite-code-buttons-container role="group" aria-label="Action buttons"}
:::

``` {.devsite-terminal translate="no" dir="ltr" is-upgraded=""}
export OUT_DIR_COMMON_BASE=<path-to-your-out-directory>
```

The output directory for each separate source tree is named after the
directory holding the source tree. For instance, if you have source
trees `/source/master1`{translate="no" dir="ltr"} and
`/source/master2`{translate="no" dir="ltr"} and if
`OUT_DIR_COMMON_BASE`{translate="no" dir="ltr"} is set to
`/output`{translate="no" dir="ltr"}, the output directories are
`/output/master1`{translate="no" dir="ltr"} and
`/output/master2`{translate="no" dir="ltr"}.

Avoid having multiple source trees stored in directories that have the
same name, as the source trees would end up sharing an output directory,
with unpredictable results. This is only supported on Jelly Bean (4.1)
and higher, including the `master`{translate="no" dir="ltr"} branch.
