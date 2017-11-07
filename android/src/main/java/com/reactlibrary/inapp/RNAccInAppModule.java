package com.reactlibrary.inapp;

import android.util.Log;

import com.ad4screen.sdk.A4S;
import com.ad4screen.sdk.InApp;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.IllegalViewOperationException;

import java.util.HashMap;
import java.util.Map;

public class RNAccInAppModule extends ReactContextBaseJavaModule {

    private static final String TAG = "AccInApp";

    private Callback mReadySuccessCallback;
    private Callback mDisplayedSuccessCallback;

    private final ReactApplicationContext mReactContext;

    public RNAccInAppModule(ReactApplicationContext reactContext) {
        super(reactContext);
        mReactContext = reactContext;
    }

    @Override
    public String getName() {
        return "RNAccInApp";
    }

    @ReactMethod
    public void setInAppReadyCallback(Callback callback) {
        synchronized (this) {
            if (mReadySuccessCallback != null) {
                Log.w(TAG, "Success Ready Callback for InApp messages is replaced by a new one");
            }
            mReadySuccessCallback = callback;
        }
        A4S.get(mReactContext).setInAppReadyCallback(false, new com.ad4screen.sdk.A4S.Callback<InApp>() {
            @Override
            public void onResult(final InApp inapp) {
                WritableMap inAppMap = convertToInAppMap(inapp);

                Callback callback;
                synchronized (RNAccInAppModule.this) {
                    callback = mReadySuccessCallback;
                    mReadySuccessCallback = null;
                }
                try {
                    callback.invoke(inAppMap);
                } catch (IllegalViewOperationException e) {
                    Log.e(TAG, "setInAppReadyCallback exception: " + e);
                }
                // Clean setInAppReadyCallback, react native can't reuse callbacks
                A4S.get(mReactContext).setInAppReadyCallback(false, null);
            }

            @Override
            public void onError(int error, String errorMessage) {
                Log.e(TAG, "setInAppReadyCallback error: " + error + ", message: " + errorMessage);
                // Clean setInAppReadyCallback, react native can't reuse callbacks
                A4S.get(mReactContext).setInAppReadyCallback(false, null);
            }
        });
    }

    @ReactMethod
    public void setInAppDisplayedCallback(Callback callback) {
        synchronized (this) {
            if (mDisplayedSuccessCallback != null) {
                Log.w(TAG, "Success Inflated Callback for InApp messages is replaced by a new one");
            }
            mDisplayedSuccessCallback = callback;

            A4S.get(mReactContext).setInAppDisplayedCallback(new A4S.Callback<InApp>() {
                @Override
                public void onResult(InApp inapp) {
                    WritableMap inAppMap = convertToInAppMap(inapp);
                    Callback callback;
                    synchronized (RNAccInAppModule.this) {
                        callback = mDisplayedSuccessCallback;
                        mDisplayedSuccessCallback = null;
                    }
                    try {
                        callback.invoke(inAppMap);
                    } catch (IllegalViewOperationException e) {
                        Log.e(TAG, "setInAppReadyCallback exception: " + e);
                    }
                    // Clean setInAppDisplayedCallback, react native can't reuse callbacks
                    A4S.get(mReactContext).setInAppDisplayedCallback(null);
                }

                @Override
                public void onError(int error, String errorMessage) {
                    Log.e(TAG, "setInAppDisplayedCallback error: " + error + ", message: " + errorMessage);
                    // Clean setInAppDisplayedCallback, react native can't reuse callbacks
                    A4S.get(mReactContext).setInAppDisplayedCallback(null);
                }
            });
        }
    }

    private WritableMap convertToInAppMap(InApp inapp) {
        WritableMap inAppMap = Arguments.createMap();
        WritableMap displayParamsMap = Arguments.createMap();
        WritableMap customParamsMap = Arguments.createMap();

        //In-App id
        String id = inapp.getId();
        //In-App template resource id
        String displayTemplate = inapp.getDisplayTemplate();
        HashMap<String,String> displayParameters = inapp.getDisplayParameters();
        //In-App custom parameters
        HashMap<String,String> customParameters = inapp.getCustomParameters();

        inAppMap.putString("messageId", id);
        inAppMap.putString("displayTemplate", displayTemplate);
        for (Map.Entry<String, String> param : displayParameters.entrySet()) {
            displayParamsMap.putString(param.getKey(), param.getValue());
        }
        for (Map.Entry<String, String> param : customParameters.entrySet()) {
            customParamsMap.putString(param.getKey(), param.getValue());
        }
        inAppMap.putMap("displayParams", displayParamsMap);
        inAppMap.putMap("customParams", customParamsMap);

        return inAppMap;
    }
}
