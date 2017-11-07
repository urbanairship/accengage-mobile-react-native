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

    private Callback mSuccessCallback;
    private Callback mErrorCallback;

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
    public void setInAppReadyCallback(Callback successCallback, Callback errorCallback) {
        synchronized (this) {
            if (mSuccessCallback != null) {
                Log.i(TAG, "Success Ready Callback for InApp messages is replaced by a new one");
            }
            mSuccessCallback = successCallback;
            if (mErrorCallback != null) {
                Log.i(TAG, "Success Ready Callback for InApp messages is replaced by a new one");
            }
            mErrorCallback = errorCallback;
        }
        A4S.get(mReactContext).setInAppReadyCallback(false, new com.ad4screen.sdk.A4S.Callback<InApp>() {
            @Override
            public void onResult(final InApp inapp) {
                //In-App id
                String id = inapp.getId();
                //In-App template resource id
                String displayTemplate = inapp.getDisplayTemplate();
                HashMap<String,String> displayParameters = inapp.getDisplayParameters();
                //In-App custom parameters
                HashMap<String,String> customParameters = inapp.getCustomParameters();

                WritableMap inAppMap = Arguments.createMap();
                WritableMap displayParamsMap = Arguments.createMap();
                WritableMap customParamsMap = Arguments.createMap();
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

                Callback callback;
                synchronized (RNAccInAppModule.this) {
                    callback = mSuccessCallback;
                    mSuccessCallback = null;
                }
                try {
                    callback.invoke(inAppMap);
                } catch (IllegalViewOperationException e) {
                    mErrorCallback.invoke(e.getMessage());
                }
                // Clean setInAppReadyCallback, react native can't reuse callbacks
                A4S.get(mReactContext).setInAppReadyCallback(false, null);
            }

            @Override
            public void onError(int error, String errorMessage) {
                Callback callback;
                synchronized (RNAccInAppModule.this) {
                    callback = mErrorCallback;
                    mErrorCallback = null;
                }
                callback.invoke(errorMessage);
                // Clean setInAppReadyCallback, react native can't reuse callbacks
                A4S.get(mReactContext).setInAppReadyCallback(false, null);
            }
        });
    }
}
