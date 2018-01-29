package com.reactlibrary.analytics;

import android.os.Bundle;
import android.util.Log;

import com.ad4screen.sdk.A4S;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

/**
 * Created by aperykasza on 11/14/17.
 */

public class RNAccViewsModule extends ReactContextBaseJavaModule {
    private static final String TAG = "AccDeviceInfo";
    private static final String E_LAYOUT_ERROR = "E_LAYOUT_ERROR";

    private final ReactApplicationContext mReactContext;

    public RNAccViewsModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.mReactContext = reactContext;
    }

    @Override
    public String getName() {
        return "RNAccViews";
    }

    @ReactMethod
    public void setView(String viewName) {
        Log.i(TAG, "Received view " + viewName);
        A4S.get(mReactContext).setView(viewName);
    }

    @ReactMethod
    public void dismissView(String viewName) {
        Log.i(TAG, "Received view " + viewName);
        A4S.get(mReactContext).dismissView(viewName);
    }
}
