
package com.reactlibrary;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

public class RNAccModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;

  public RNAccModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNAcc";
  }

  @ReactMethod
  public String getMsg() {
    android.util.Log.d("AccModule", "getMsg!!!!");
    return "My RNAcc module";
  }

}