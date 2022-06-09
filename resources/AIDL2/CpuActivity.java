package com.eightman;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CpuActivity extends Activity {
    private TextView cppHIDLRetTV;
    private TextView javaHIDLRetTV;
    private TextView javaAIDLRetTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpu);

        cppHIDLRetTV = findViewById(R.id.cppHIDLRetTV);
        javaHIDLRetTV = findViewById(R.id.javaHIDLRetTV);
        javaAIDLRetTV = findViewById(R.id.javaAIDLRetTV);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    private void refresh() {
        setValue(cppHIDLRetTV, EightmanServices.getCpu().getScalingGovernor().trim());

        try {
            setValue(javaHIDLRetTV, EightmanServices.getCpuHJ().getScalingGovernor().trim());
        } catch (android.os.RemoteException e) {
            Log.e("CpuActivity", "cpu HIDL Java proxy returned error", e);
        }

        try {
            setValue(javaAIDLRetTV, EightmanServices.getCpuAJ().getScalingGovernor().trim());
        } catch (android.os.RemoteException e) {
            Log.e("CpuActivity", "cpu AIDL Java proxy returned error", e);
        }
    }

    public void onFetchValue(View view) {
        refresh();
    }

    public void onClick(View view) {
        String setValue = ((Button)view).getText().toString();

        // [---------------- com.eightman ---------------------------]
        // [ android_app: /system_ext/app/EightmanServices/          ]
        // [                     |/\/\|                              ]
        // [ java_sdk_library: /vendor/framework/com.eightman.jar    ]
        // [                     |/\/\|                              ]
        // [ cc_library_shared: apk:/lib/x86/libeightman.so          ]
        // [                     |/\/\|                              ]
        // [ hidl_interface: apk:/lib/x86/vendor.eightman.cpu@1.0.so ]
        // [---------------------------------------------------------]
        //                         ||
        //                    /dev/hwbinder
        //                         ||
        // [------------- vendor.eightman.cpu@1.0-service -------------]
        // [ hidl_interface: /vendor/lib/vendor.eightman.cpu@1.0.so    ]
        // [                     |/\/\|                                ]
        // [ cc_binary: /vendor/bin/hw/vendor.eightman.cpu@1.0-service ]
        // [-----------------------------------------------------------]
        setSuccess(cppHIDLRetTV, EightmanServices.getCpu().setScalingGovernor(setValue));

        // [---------------- com.eightman ----------------------------]
        // [ android_app: /system_ext/app/EightmanServices/           ]
        // [                     |/\/\|                               ]
        // [ hidl_interface: apk:classes.dex:vendor.eightman.cpu.V1_0 ]
        // [----------------------------------------------------------]
        //                         ||
        //                    /dev/hwbinder
        //                         ||
        // [------------- vendor.eightman.cpu@1.0-service -------------]
        // [ hidl_interface: /vendor/lib/vendor.eightman.cpu@1.0.so    ]
        // [                     |/\/\|                                ]
        // [ cc_binary: /vendor/bin/hw/vendor.eightman.cpu@1.0-service ]
        // [-----------------------------------------------------------]
        try {
            setSuccess(javaHIDLRetTV, EightmanServices.getCpuHJ().setScalingGovernor(setValue));
        } catch (android.os.RemoteException e) {
            Log.e("CpuActivity", "cpu HIDL Java proxy returned error", e);
        }

        // [---------------- com.eightman ----------------------------]
        // [ android_app: /system_ext/app/EightmanServices/           ]
        // [                     |/\/\|                               ]
        // [ aidl_interface: apk:classes.dex:vendor.eightman.cpu.ICpu ]
        // [----------------------------------------------------------]
        //                         ||
        //                    /dev/vndbinder
        //                         ||
        // [------------- vendor.eightman.cpu@1.0-service ----------------------]
        // [ aidl_interface: /vendor/lib/vendor.eightman.cpu-V1-ndk_platform.so ]
        // [                     |/\/\|                                         ]
        // [ cc_binary: /vendor/bin/hw/vendor.eightman.cpu-service              ]
        // [--------------------------------------------------------------------]
        try {
            setSuccess(javaAIDLRetTV, EightmanServices.getCpuAJ().setScalingGovernor(setValue));
        } catch (android.os.RemoteException e) {
            Log.e("CpuActivity", "cpu AIDL Java proxy returned error", e);
        }
    }

    private void setValue(TextView textValue, String value){
        textValue.setText(value);
        textValue.setTextColor(getColor(R.color.darkGray));
    }

    private void setSuccess(TextView textValue, boolean success){
        if (success) {
            textValue.setText("Success");
            textValue.setTextColor(getColor(R.color.success));
        } else {
            textValue.setText("Error");
            textValue.setTextColor(getColor(R.color.error));
        }
    }
}
