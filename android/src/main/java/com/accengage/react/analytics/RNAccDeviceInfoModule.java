package com.accengage.react.analytics;

import android.os.Bundle;
import android.util.Log;

import com.ad4screen.sdk.A4S;
import com.ad4screen.sdk.service.modules.profile.DeviceInformation;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableNativeMap;
import com.accengage.react.common.Utils;

import java.util.Map;

public class RNAccDeviceInfoModule extends ReactContextBaseJavaModule {
    private static final String TAG = "AccDeviceInfo";

    private final ReactApplicationContext mReactContext;
    private DeviceInformation mDeviceInformation;

    public RNAccDeviceInfoModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.mReactContext = reactContext;
    }

    @Override
    public String getName() {
        return "RNAccDeviceInfo";
    }

    @ReactMethod
    public void updateDeviceInformation(String method, String key, String value) {
        mDeviceInformation = new DeviceInformation();
        switch (method) {
            case "set":
                mDeviceInformation.set(key, value);
                A4S.get(mReactContext).updateDeviceInformation(mDeviceInformation);
                break;
            case "delete":
                mDeviceInformation.delete(key);
                A4S.get(mReactContext).updateDeviceInformation(mDeviceInformation);
                break;
            case "increment":
            case "decrement":
                double input;
                try {
                    input = Double.parseDouble(value);
                } catch (NumberFormatException e) {
                    Log.e("UpdateDeviceInfo | ", "Increment without a numeric value !");
                    break;
                }
                if (method.equals("increment")) {
                    mDeviceInformation.increment(key, input);
                } else {
                    mDeviceInformation.decrement(key, input);
                }
                A4S.get(mReactContext).updateDeviceInformation(mDeviceInformation);
                break;
        }
    }

    @ReactMethod
    public void updateDeviceInfo(ReadableMap deviceInfoMap) {
        if (!(deviceInfoMap instanceof ReadableNativeMap)) {
            Log.e(TAG, "updateDeviceInfo: deviceInfoMap is not a js object");
            return;
        }

        Bundle bundle = new Bundle();
        Map<String, Object> info = Utils.recursivelyDeconstructReadableMap(deviceInfoMap);
        for (Map.Entry<String, Object> entry : info.entrySet()) {
            if (!(entry.getValue() instanceof String)) {
                Log.w(TAG, "updateDeviceInfo: key '" + entry.getKey() + "' doesn't contain a String object");
                continue;
            }
            bundle.putString(entry.getKey(), (String) entry.getValue());
        }
        A4S.get(mReactContext).updateDeviceInfo(bundle);
    }
}
