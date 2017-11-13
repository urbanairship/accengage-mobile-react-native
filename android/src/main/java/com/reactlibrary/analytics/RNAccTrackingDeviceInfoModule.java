package com.reactlibrary.analytics;

import android.os.Bundle;
import android.util.Log;

import com.ad4screen.sdk.A4S;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

/**
 * Created by aperykasza on 11/10/17.
 */

public class RNAccTrackingDeviceInfoModule extends ReactContextBaseJavaModule {
    private static final String TAG = "AccDeviceInfo";
    private static final String E_LAYOUT_ERROR = "E_LAYOUT_ERROR";

    private final ReactApplicationContext reactContext;

    public RNAccTrackingDeviceInfoModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "RNAccTrackingDeviceInfo";
    }

    @ReactMethod
    public void updateDeviceInfo(String key, String value) {
        Log.i(TAG, "Received key " + key + " and value " + value);
        Bundle bundle = new Bundle();
        bundle.putString(key, value);
        A4S.get(getReactApplicationContext()).updateDeviceInfo(bundle);
    }
}
