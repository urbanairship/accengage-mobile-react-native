package com.reactlibrary.push;

import android.util.Log;

import com.ad4screen.sdk.A4S;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.uimanager.IllegalViewOperationException;

public class RNAccPushModule extends ReactContextBaseJavaModule {
    private static final String TAG = "AccPush";

    private Promise mIsEnabledPromise;
    private Promise mIsLockedPromise;
    private Promise mGetTokenPromise;

    private final ReactApplicationContext mReactContext;

    public RNAccPushModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.mReactContext = reactContext;
    }

    @Override
    public String getName() {
        return "RNAccPush";
    }

    @ReactMethod
    public void setEnabled(Boolean enabled) {
        Log.i(TAG, "setEnabled " + enabled);
        A4S.get(mReactContext).setPushEnabled(enabled);
    }

    @ReactMethod
    public void isEnabled(Promise promise) {
        synchronized (this) {
            if (mIsEnabledPromise != null) {
                Log.w(TAG, "isEnabled Promise is replaced by a new one");
            }
            mIsEnabledPromise = promise;
            A4S.get(mReactContext).isPushEnabled(new A4S.Callback<Boolean>() {
                @Override
                public void onResult(Boolean isEnabled) {
                    Promise promise;
                    synchronized (RNAccPushModule.this) {
                        promise = mIsEnabledPromise;
                        mIsEnabledPromise = null;
                    }
                    if (promise == null) {
                        Log.e(TAG, "Promise is null for isEnabled");
                        return;
                    }
                    try {
                        promise.resolve(isEnabled);
                    } catch (IllegalViewOperationException e) {
                        promise.reject(TAG + " ERROR_IS_ENABLED", e);
                    }
                }

                @Override
                public void onError(int i, String error) {
                    Log.e(TAG, "An error is appeared for isEnabled: " + error);
                }
            } );
        }
    }

    @ReactMethod
    public void setLocked(Boolean locked) {
        A4S.get(mReactContext).setPushNotificationLocked(locked);
    }

    @ReactMethod
    public void isLocked(Promise promise) {
        synchronized (this) {
            if (mIsLockedPromise != null) {
                Log.w(TAG, "isLocked Promise is replaced by a new one");
            }
            mIsLockedPromise = promise;
            A4S.get(mReactContext).isPushEnabled(new A4S.Callback<Boolean>() {
                @Override
                public void onResult(Boolean isEnabled) {
                    Promise promise;
                    synchronized (RNAccPushModule.this) {
                        promise = mIsLockedPromise;
                        mIsLockedPromise = null;
                    }
                    if (promise == null) {
                        Log.e(TAG, "Promise is null for isLocked");
                        return;
                    }
                    try {
                        promise.resolve(isEnabled);
                    } catch (IllegalViewOperationException e) {
                        promise.reject(TAG + " ERROR_IS_LOCKED", e);
                    }
                }

                @Override
                public void onError(int i, String error) {
                    Log.e(TAG, "An error is appeared for isLocked: " + error);
                }
            } );
        }
    }

    @ReactMethod
    public void setToken(String token) {
        A4S.get(mReactContext).sendPushToken(token);
    }

    @ReactMethod
    public void getToken(Promise promise) {
        synchronized (this) {
            if (mGetTokenPromise != null) {
                Log.w(TAG, "getToken Promise is replaced by a new one");
            }
            mGetTokenPromise = promise;
            A4S.get(mReactContext).getPushToken(new A4S.Callback<String>() {
                @Override
                public void onResult(String token) {
                    Promise promise;
                    synchronized (RNAccPushModule.this) {
                        promise = mGetTokenPromise;
                        mGetTokenPromise = null;
                    }
                    if (promise == null) {
                        Log.e(TAG, "Promise is null for getToken");
                        return;
                    }
                    try {
                        promise.resolve(token);
                    } catch (IllegalViewOperationException e) {
                        promise.reject(TAG + " ERROR_GET_TOKEN", e);
                    }
                }

                @Override
                public void onError(int i, String error) {
                    Log.e(TAG, "An error is appeared for getToken: " + error);
                }
            });
        }
    }
}
