package com.eightman;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class EightmanBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "EightmanBR";

    @Override
    public void onReceive(Context context, Intent intent) {
        String scalingGovernor = intent.getStringExtra("setScalingGovernor");
        if(scalingGovernor != null) {
            if(EightmanServices.getCpu().setScalingGovernor(scalingGovernor)) {
                Log.d(TAG, "Succesfuly setScalingGovernor(" + scalingGovernor + ")");
            } else {
                Log.e(TAG, "Failed calling setScalingGovernor(" + scalingGovernor + ")");
            }
        }
    }
}
