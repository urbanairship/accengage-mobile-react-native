
package com.reactlibrary.analytics;

import android.util.Log;

import com.ad4screen.sdk.A4S;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableNativeMap;
import com.facebook.react.bridge.Callback;
import com.reactlibrary.common.Utils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RNAccTrackingModule extends ReactContextBaseJavaModule {

    private static final String TAG = "AccTracking";

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
    public void trackEvent(int key) {
        Log.d(TAG, "trackEvent key: " + key);
        A4S.get(getReactApplicationContext()).trackEvent(key);
    }

    @ReactMethod
    public void trackEvent(int key, ReadableMap customData) {
        if (customData == null) {
            this.trackEvent(key);
            return;
        }
        if (!(customData instanceof ReadableNativeMap)) {
            Log.w(TAG, "Custom data is sent in unsuported type and ignored");
            this.trackEvent(key);
            return;
        }

        Map hashMap = Utils.recursivelyDeconstructReadableMap(customData);
        JSONObject jsonObject = new JSONObject(hashMap);

        Log.d(TAG, "trackEvent key: " + key + ", obj: " + jsonObject.toString());
        A4S.get(getReactApplicationContext()).trackEvent(key, jsonObject.toString());
    }

}