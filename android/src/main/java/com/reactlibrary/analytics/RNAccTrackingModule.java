
package com.reactlibrary.analytics;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

public class RNAccTrackingModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;

    public RNAccTrackingModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "RNAccTracking";
    }

    @ReactMethod
    public void getMsg() {
        android.util.Log.d("AccTrackinModule", "getMsg from AccTracking!!!!");
    }

}