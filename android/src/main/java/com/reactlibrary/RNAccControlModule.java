package com.reactlibrary;

import android.os.Bundle;
import android.util.Log;

import com.ad4screen.sdk.A4S;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class RNAccControlModule extends ReactContextBaseJavaModule {
    private static final String TAG = "AccControl";
    private static final String E_LAYOUT_ERROR = "E_LAYOUT_ERROR";

    private final ReactApplicationContext mReactContext;

    public RNAccControlModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.mReactContext = reactContext;
    }

    @Override
    public String getName() {
        return "RNAccControl";
    }

    @ReactMethod
    public void setOptinData(boolean isEnabled) {
        Log.i(TAG, "setOptinData to " + isEnabled);
        OptinType optinData;
        //changing boolean into OptinType
        if (isEnabled == true) {
            optinData = OptinType.YES;
        } else {
            optinData = OptinType.NO;
        }
        A4S.get(mReactContext).setOptinData(mReactContext, optinData);
    }

    @ReactMethod
    public void setOptinGeoloc(boolean isEnabled) {
        Log.i(TAG, "setOptinGeoloc to " + isEnabled);
        OptinType optinGeoloc;
        //changing boolean into OptinType
        if (isEnabled == true) {
            optinGeoloc = OptinType.YES;
        } else {
            optinGeoloc = OptinType.NO;
        }
        A4S.get(mReactContext).setOptinGeoloc(mReactContext, optinData);
    }
}
