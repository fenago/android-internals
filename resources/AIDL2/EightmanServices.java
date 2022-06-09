package com.eightman;

import android.app.Application;
import android.util.Log;
import com.eightman.Cpu;
import android.content.IntentFilter;
import vendor.eightman.cpu.V1_0.ICpu;
import android.os.ServiceManager;
import android.os.IBinder;

public class EightmanServices extends Application {
    private static final String TAG = "EightmanServices";

    private static final String ICPU_AIDL_INTERFACE = "vendor.eightman.cpu.ICpu/default";

    EightmanBroadcastReceiver eightmanBroadcastReceiver = new EightmanBroadcastReceiver();
    private static Cpu cpu; // JAVA -> JNI -> HIDL Cpp Proxy
    private static ICpu cpuHJ; // HIDL Java Proxy
    private static vendor.eightman.cpu.ICpu cpuAJ; // AIDL Java Proxy

    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "onCreate()");
        cpu = new Cpu();

        Log.d(TAG, "HIDL-Cpp getScalingGovernor => " + cpu.getScalingGovernor());
        Log.d(TAG, "HIDL-Cpp setScalingGovernor(performance) => " + cpu.setScalingGovernor("performance"));

        try {
            cpuHJ = ICpu.getService(true);
            Log.d(TAG, "HIDL-Java getScalingGovernor => " + cpuHJ.getScalingGovernor());
            Log.d(TAG, "HIDL-Java setScalingGovernor(powersave) => " + cpuHJ.setScalingGovernor("powersave"));
        } catch (android.os.RemoteException e) {
            Log.e(TAG, "ICpu error", e);
        }

        IBinder binder = ServiceManager.getService(ICPU_AIDL_INTERFACE);
        if (binder == null) {
            Log.e(TAG, "Getting " + ICPU_AIDL_INTERFACE + " service daemon binder failed");
        } else {
            cpuAJ = vendor.eightman.cpu.ICpu.Stub.asInterface(binder);
            if (cpuAJ == null) {
                Log.e(TAG, "Getting ICpu Aidl daemon interface failed");
            } else {
                try {
                    Log.d(TAG, "AIDL-Java getScalingGovernor => " +
                         cpuAJ.getScalingGovernor());
                    Log.d(TAG, "AIDL-Java setScalingGovernor(conservative) => " +
                         cpuAJ.setScalingGovernor("conservative"));
                } catch (android.os.RemoteException e) {
                    Log.e(TAG, "ICPU-AIDL error", e);
                }
            }
        }

        IntentFilter filter = new IntentFilter("com.eightman.CPU");
        registerReceiver(eightmanBroadcastReceiver, filter);
    }

    public void onTerminate() {
        super.onTerminate();
        Log.d(TAG, "Terminated");
    }

    // JAVA -> JNI -> HIDL Cpp Proxy
    public static Cpu getCpu() {
        return cpu;
    }

    // HIDL Java Proxy
    public static ICpu getCpuHJ() {
        return cpuHJ;
    }

    // AIDL Java Proxy
    public static vendor.eightman.cpu.ICpu getCpuAJ() {
        return cpuAJ;
    }
}
