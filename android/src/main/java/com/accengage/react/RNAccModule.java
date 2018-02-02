package com.accengage.react;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;

public class RNAccModule extends ReactContextBaseJavaModule {

  private static ReactContext mReactContext;

  public RNAccModule(ReactApplicationContext reactContext) {
    super(reactContext);
    mReactContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNAcc";
  }

  public static ReactContext getReactContext() {
    return mReactContext;
  }

}