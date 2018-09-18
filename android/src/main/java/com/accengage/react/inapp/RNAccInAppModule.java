package com.accengage.react.inapp;

import android.util.Log;

import com.ad4screen.sdk.A4S;
import com.ad4screen.sdk.InApp;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.IllegalViewOperationException;

import java.util.HashMap;
import java.util.Map;

public class RNAccInAppModule extends ReactContextBaseJavaModule {

    private static final String TAG = "AccInApp";

    private Callback mReadyCallback;
    private Callback mDisplayedCallback;
    private Callback mClickedCallback;
    private Callback mClosedCallback;
    private Promise mIsInAppLockedPromise;

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
    public void setReadyCallback(Callback callback) {
        synchronized (this) {
            if (mReadyCallback != null) {
                Log.w(TAG, "Success Ready Callback for InApp messages is replaced by a new one");
            }
            mReadyCallback = callback;
        }
        A4S.get(mReactContext).setInAppReadyCallback(false, new com.ad4screen.sdk.A4S.Callback<InApp>() {
            @Override
            public void onResult(final InApp inapp) {
                WritableMap inAppMap = convertToInAppMap(inapp);

                Callback callback;
                synchronized (RNAccInAppModule.this) {
                    callback = mReadyCallback;
                    mReadyCallback = null;
                }
                try {
                    callback.invoke(inAppMap);
                } catch (IllegalViewOperationException e) {
                    Log.e(TAG, "setReadyCallback exception: " + e);
                }
                // Clean setInAppReadyCallback, react native can't reuse callbacks
                A4S.get(mReactContext).setInAppReadyCallback(false, null);
            }

            @Override
            public void onError(int error, String errorMessage) {
                Log.e(TAG, "setReadyCallback error: " + error + ", message: " + errorMessage);
                // Clean setInAppReadyCallback, react native can't reuse callbacks
                A4S.get(mReactContext).setInAppReadyCallback(false, null);
            }
        });
    }

    @ReactMethod
    public void setDisplayedCallback(Callback callback) {
        synchronized (this) {
            if (mDisplayedCallback != null) {
                Log.w(TAG, "Success Displayed Callback for InApp messages is replaced by a new one");
            }
            mDisplayedCallback = callback;

            A4S.get(mReactContext).setInAppDisplayedCallback(new A4S.Callback<InApp>() {
                @Override
                public void onResult(InApp inapp) {
                    WritableMap inAppMap = convertToInAppMap(inapp);
                    Callback callback;
                    synchronized (RNAccInAppModule.this) {
                        callback = mDisplayedCallback;
                        mDisplayedCallback = null;
                    }
                    try {
                        callback.invoke(inAppMap);
                    } catch (IllegalViewOperationException e) {
                        Log.e(TAG, "setDisplayedCallback exception: " + e);
                    }
                    // Clean setInAppDisplayedCallback, react native can't reuse callbacks
                    A4S.get(mReactContext).setInAppDisplayedCallback(null);
                }

                @Override
                public void onError(int error, String errorMessage) {
                    Log.e(TAG, "setDisplayedCallback error: " + error + ", message: " + errorMessage);
                    // Clean setInAppDisplayedCallback, react native can't reuse callbacks
                    A4S.get(mReactContext).setInAppDisplayedCallback(null);
                }
            });
        }
    }

    @ReactMethod
    public void setClickedCallback(Callback callback) {
        synchronized (this) {
            if (mClickedCallback != null) {
                Log.w(TAG, "Success Clicked Callback for InApp messages is replaced by a new one");
            }
            mClickedCallback = callback;

            A4S.get(mReactContext).setInAppClickedCallback(new A4S.Callback<InApp>() {
                @Override
                public void onResult(InApp inapp) {
                    WritableMap inAppMap = convertToInAppMap(inapp);
                    Callback callback;
                    synchronized (RNAccInAppModule.this) {
                        callback = mClickedCallback;
                        mClickedCallback = null;
                    }
                    try {
                        callback.invoke(inAppMap);
                    } catch (IllegalViewOperationException e) {
                        Log.e(TAG, "setClickedCallback exception: " + e);
                    }
                    // Clean setInAppClosedCallback, react native can't reuse callbacks
                    A4S.get(mReactContext).setInAppClickedCallback(null);
                }

                @Override
                public void onError(int error, String errorMessage) {
                    Log.e(TAG, "setClickedCallback error: " + error + ", message: " + errorMessage);
                    // Clean setInAppClosedCallback, react native can't reuse callbacks
                    A4S.get(mReactContext).setInAppClickedCallback(null);
                }
            });
        }
    }

    @ReactMethod
    public void setClosedCallback(Callback callback) {
        synchronized (this) {
            if (mClosedCallback != null) {
                Log.w(TAG, "Success Closed Callback for InApp messages is replaced by a new one");
            }
            mClosedCallback = callback;

            A4S.get(mReactContext).setInAppClosedCallback(new A4S.Callback<InApp>() {
                @Override
                public void onResult(InApp inapp) {
                    WritableMap inAppMap = convertToInAppMap(inapp);
                    Callback callback;
                    synchronized (RNAccInAppModule.this) {
                        callback = mClosedCallback;
                        mClosedCallback = null;
                    }
                    try {
                        callback.invoke(inAppMap);
                    } catch (IllegalViewOperationException e) {
                        Log.e(TAG, "setClosedCallback exception: " + e);
                    }
                    // Clean setInAppClosedCallback, react native can't reuse callbacks
                    A4S.get(mReactContext).setInAppClosedCallback(null);
                }

                @Override
                public void onError(int error, String errorMessage) {
                    Log.e(TAG, "setClosedCallback error: " + error + ", message: " + errorMessage);
                    // Clean setInAppClosedCallback, react native can't reuse callbacks
                    A4S.get(mReactContext).setInAppClosedCallback(null);
                }
            });
        }
    }

    @ReactMethod
    public void setLocked(Boolean isEnabled) {
        A4S.get(mReactContext).setInAppDisplayLocked(isEnabled);
    }

    @ReactMethod
    public void isLocked(Promise promise) {
        synchronized (this) {
            if (mIsInAppLockedPromise != null) {
                Log.w(TAG, "isLocked Promise is replaced by a new one");
            }
            mIsInAppLockedPromise = promise;
            A4S.get(mReactContext).isInAppDisplayLocked(new A4S.Callback<Boolean>() {
                @Override
                public void onResult(Boolean isLocked) {
                    Promise promise;
                    synchronized (RNAccInAppModule.this) {
                        promise = mIsInAppLockedPromise;
                        mIsInAppLockedPromise = null;
                    }
                    if (promise == null) {
                        Log.e(TAG, "Promise is null for isLocked");
                        return;
                    }
                    try {
                        promise.resolve(isLocked);
                    } catch (IllegalViewOperationException e) {
                        promise.reject("ERROR_IS_INAPP_DISPLAY_ENABLED", e);
                    }
                }

                @Override
                public void onError(int i, String error) {
                    Log.e(TAG, "An error is appeared for isLocked: " + error);
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
