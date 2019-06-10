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

public class RNAccStatesModule extends ReactContextBaseJavaModule {
    private static final String TAG = "AccStates";

    private final ReactApplicationContext mReactContext;

    public RNAccStatesModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.mReactContext = reactContext;
    }

    @Override
    public String getName() {
        return "RNAccStates";
    }

    @ReactMethod
    public void setState(String name, String value) {
        A4S.get(mReactContext).putState(name, value);
    }

}
