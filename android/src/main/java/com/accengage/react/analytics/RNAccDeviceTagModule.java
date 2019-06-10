package com.accengage.react.analytics;

import android.support.annotation.Nullable;
import android.util.Log;

import com.accengage.react.common.Utils;
import com.ad4screen.sdk.A4S;
import com.ad4screen.sdk.DeviceTag;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;

import java.util.Date;
import java.util.Map;

public class RNAccDeviceTagModule extends ReactContextBaseJavaModule {
    private static final String TAG = "AccDeviceTag";

    private final ReactApplicationContext mReactContext;

    public RNAccDeviceTagModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.mReactContext = reactContext;
    }

    @Override
    public String getName() {
        return "RNAccDeviceTag";
    }

    @ReactMethod
    public void setDeviceTag(String category, String identifier, @Nullable ReadableMap items) {
        Log.d("RNDeviceTag", "setDeviceTag");
        DeviceTag tag = new DeviceTag(category, identifier);
        if (items != null) {
            Map<String, Object> info = Utils.recursivelyDeconstructReadableMap(items);
            Log.d("RNDeviceTag", items.toString());
            for (Map.Entry<String, Object> entry : info.entrySet()) {
                switch (entry.getValue().getClass().getName()) {
                    case "java.lang.String":
                        tag.addCustomParameter(entry.getKey(), (String) entry.getValue());
                        Log.d("RNDeviceTag", "Adding CustomParam : " + entry.getKey() + " " + entry.getValue());
                        break;
                    case "java.lang.Integer":
                        tag.addCustomParameter(entry.getKey(), (int) entry.getValue());
                        break;
                    case "java.util.Date":
                        tag.addCustomParameter(entry.getKey(), (Date) entry.getValue());
                        break;
                    case "java.lang.Boolean":
                        tag.addCustomParameter(entry.getKey(), (Boolean) entry.getValue());
                        break;
                }
            }
        }
        Log.d("RNDeviceTag", "Sending Device Tag Category : " + category + " ID : " + identifier);
        A4S.get(mReactContext).setDeviceTag(tag);
    }

    @ReactMethod
    public void deleteDeviceTag(String category, String id) {
        DeviceTag tag = new DeviceTag(category, id);
        A4S.get(mReactContext).deleteDeviceTag(tag);
    }

}
