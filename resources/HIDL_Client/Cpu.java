package com.eightman;

public class Cpu {
    static final String TAG = "com.eightman.Cpu";
    static final boolean DEBUG = false;

    private native void nativeInit();
    private static native boolean nativeSetScalingGovernor(String governor);
    private static native String nativeGetScalingGovernor();

    public Cpu() {
        synchronized (this) {
            nativeInit();
            getScalingGovernor(); // Can be removed
        }
    }

    public boolean setScalingGovernor(String governor) {
        return nativeSetScalingGovernor(governor);
    }

    public String getScalingGovernor() {
        return nativeGetScalingGovernor();
    }

    static {
        System.loadLibrary("eightman"); // libeightman.so
    }
}
