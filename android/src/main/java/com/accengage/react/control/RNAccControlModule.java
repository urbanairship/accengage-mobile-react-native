package com.accengage.react.control;

import android.os.Bundle;
import android.util.Log;

import com.ad4screen.sdk.A4S;
import com.ad4screen.sdk.OptinType;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.uimanager.IllegalViewOperationException;

public class RNAccControlModule extends ReactContextBaseJavaModule {
    private static final String TAG = "AccControl";
    private static final String E_LAYOUT_ERROR = "E_LAYOUT_ERROR";

    private final ReactApplicationContext mReactContext;
    private Promise mIsOptinDataEnabledPromise;
    private Promise mIsOptinGeolocEnabledPromise;

    public RNAccControlModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.mReactContext = reactContext;
    }

    @Override
    public String getName() {
        return "RNAccControl";
    }

    @ReactMethod
    public void setOptinDataEnabled(boolean isEnabled) {
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
    public void setOptinGeolocEnabled(boolean isEnabled) {
        Log.i(TAG, "setOptinGeoloc to " + isEnabled);
        OptinType optinGeoloc;
        //changing boolean into OptinType
        if (isEnabled == true) {
            optinGeoloc = OptinType.YES;
        } else {
            optinGeoloc = OptinType.NO;
        }
        A4S.get(mReactContext).setOptinGeoloc(mReactContext, optinGeoloc);
    }

    @ReactMethod
    public void isOptinDataEnabled(Promise promise) {
        synchronized (this) {
            if (mIsOptinDataEnabledPromise != null) {
                Log.w(TAG, "isOptinDataEnabledPromise Promise is replaced by a new one");
            }
            mIsOptinDataEnabledPromise = promise;
            Boolean mIsOptinDataEnabled;

            OptinType optinData;
            optinData = A4S.get(mReactContext).getOptinDataStatus(mReactContext);

            if (optinData == OptinType.YES) {
                mIsOptinDataEnabled = true;
            } else {
                mIsOptinDataEnabled = false;
            }

            Log.d("A4SRN", "mIsOptinDataEnabled : " + mIsOptinDataEnabled);

            if (promise == null) {
                Log.e(TAG, "Promise is null for isOptinDataEnabled");
                return;
            }
            try {
                promise.resolve(mIsOptinDataEnabled);
            } catch (IllegalViewOperationException e) {
                promise.reject("ERROR_OPTIN_DATA_UNKNOWN", e);
            }
        }
    }

    @ReactMethod
    public void isOptinGeolocEnabled(Promise promise) {
        synchronized (this) {
            if (mIsOptinGeolocEnabledPromise != null) {
                Log.w(TAG, "isOptinGeolocEnabledPromise Promise is replaced by a new one");
            }
            mIsOptinGeolocEnabledPromise = promise;
            Boolean mIsOptinGeolocEnabled;

            OptinType optinGeoloc;
            optinGeoloc = A4S.get(mReactContext).getOptinGeolocStatus(mReactContext);

            if (optinGeoloc == OptinType.YES) {
                mIsOptinGeolocEnabled = true;
            } else {
                mIsOptinGeolocEnabled = false;
            }

            Log.d("A4SRN", "mIsOptinGeolocEnabled: " + mIsOptinGeolocEnabled);

            if (promise == null) {
                Log.e(TAG, "Promise is null for isOptinGeolocEnabled");
                return;
            }
            try {
                promise.resolve(mIsOptinGeolocEnabled);
            } catch (IllegalViewOperationException e) {
                promise.reject("ERROR_OPTIN_DATA_UNKNOWN", e);
            }
        }
    }
}