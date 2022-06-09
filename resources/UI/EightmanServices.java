package com.eightman;

import android.app.Application;
import android.util.Log;
import com.eightman.Cpu;
import android.content.IntentFilter;
import vendor.eightman.cpu.V1_0.ICpu;

public class EightmanServices extends Application {
    private static final String TAG = "EightmanServices";

    EightmanBroadcastReceiver eightmanBroadcastReceiver = new EightmanBroadcastReceiver();
    private static Cpu cpu; // JAVA -> JNI -> HIDL Cpp Proxy
    private static ICpu cpuHJ; // HIDL Java Proxy

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
}
