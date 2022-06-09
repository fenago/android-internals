package com.eightman;

import android.app.Application;
import android.util.Log;
import com.eightman.Cpu;
import android.content.IntentFilter;

public class EightmanServices extends Application {
    private static final String TAG = "EightmanServices";

    EightmanBroadcastReceiver eightmanBroadcastReceiver = new EightmanBroadcastReceiver();
    private static Cpu cpu; // JAVA -> JNI -> HIDL

    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "onCreate()");
        cpu = new Cpu();

        Log.d(TAG, "getScalingGovernor => " + cpu.getScalingGovernor());
        Log.d(TAG, "setScalingGovernor(performance) => " + cpu.setScalingGovernor("performance"));

        IntentFilter filter = new IntentFilter("com.eightman.CPU");
        registerReceiver(eightmanBroadcastReceiver, filter);
    }

    public void onTerminate() {
        super.onTerminate();
        Log.d(TAG, "Terminated");
    }

    public static Cpu getCpu() {
        return cpu;
    }
}
