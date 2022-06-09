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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpu);

        cppHIDLRetTV = findViewById(R.id.cppHIDLRetTV);
        javaHIDLRetTV = findViewById(R.id.javaHIDLRetTV);
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
    }

    public void onFetchValue(View view) {
        refresh();
    }

    public void onClick(View view) {
        String setValue = ((Button)view).getText().toString();

        // [---------------- com.eightman --------------------------]
        // [ android_app: /vendor/app/EightmanServices/             ]
        // [                     |/\/\|                             ]
        // [ java_sdk_library: /vendor/framework/com.eightman.jar   ]
        // [                     |/\/\|                             ]
        // [ cc_library_shared: /vendor/lib/libeightman.so          ]
        // [                     |/\/\|                             ]
        // [ hidl_interface: /vendor/lib/vendor.eightman.cpu@1.0.so ]
        // [--------------------------------------------------------]
        //                         ||
        //                    /dev/hwbinder
        //                         ||
        // [------------- vendor.eightman.cpu@1.0-service -------------]
        // [ hidl_interface: /vendor/lib/vendor.eightman.cpu@1.0.so    ]
        // [                     |/\/\|                                ]
        // [ cc_binary: /vendor/bin/hw/vendor.eightman.cpu@1.0-service ]
        // [-----------------------------------------------------------]
        setSuccess(cppHIDLRetTV, EightmanServices.getCpu().setScalingGovernor(setValue));

        // [---------------- com.eightman -----------------------]
        // [ android_app: /vendor/app/EightmanServices/          ]
        // [                     |/\/\|                          ]
        // [ hidl_interface: vendor.eightman.cpu@1.0-JAVA source ]
        // [-----------------------------------------------------]
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
