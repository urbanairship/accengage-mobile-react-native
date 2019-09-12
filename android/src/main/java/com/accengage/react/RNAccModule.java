package com.accengage.react;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.accengage.react.common.Utils;
import com.ad4screen.sdk.A4S;
import com.ad4screen.sdk.DeviceTag;
import com.ad4screen.sdk.InApp;
import com.ad4screen.sdk.OptinType;
import com.ad4screen.sdk.StaticList;
import com.ad4screen.sdk.analytics.Cart;
import com.ad4screen.sdk.analytics.Item;
import com.ad4screen.sdk.analytics.Lead;
import com.ad4screen.sdk.analytics.Purchase;
import com.ad4screen.sdk.service.modules.profile.DeviceInformation;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableNativeArray;
import com.facebook.react.bridge.ReadableNativeMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.IllegalViewOperationException;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Double.NaN;

public class RNAccModule extends ReactContextBaseJavaModule {

  private static ReactContext mReactContext;
  private static final String TAG = "RNAcc";

  //Push variables
  private Promise mIsEnabledPromise;
  private Promise mGetTokenPromise;

  //In-app variables
  private Callback mReadyCallback;
  private Callback mDisplayedCallback;
  private Callback mClickedCallback;
  private Callback mClosedCallback;
  private Promise mIsInAppLockedPromise;

  //Optin variables
  private Promise mIsOptinDataEnabledPromise;
  private Promise mIsOptinGeolocEnabledPromise;

  //DeviceInfo variables
  private DeviceInformation mDeviceInformation;

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

  /*********************************
   *
   * Push Methods
   *
   ********************************/
  @ReactMethod
  public void setPushEnabled(Boolean enabled) {
    Log.i(TAG, "setEnabled " + enabled);
    A4S.get(mReactContext).setPushEnabled(enabled);
  }

  @ReactMethod
  public void isPushEnabled(Promise promise) {
    synchronized (this) {
      if (mIsEnabledPromise != null) {
        Log.w(TAG, "isEnabled Promise is replaced by a new one");
      }
      mIsEnabledPromise = promise;
      A4S.get(mReactContext).isPushEnabled(new A4S.Callback<Boolean>() {
        @Override
        public void onResult(Boolean isEnabled) {
          Promise promise;
          synchronized (RNAccModule.this) {
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
  public void setPushLocked(Boolean locked) {
    A4S.get(mReactContext).setPushNotificationLocked(locked);
  }

  @ReactMethod
  public void isPushLocked(Promise promise) {
    try {
      promise.resolve(A4S.get(mReactContext).isPushNotificationLocked());
    } catch (IllegalViewOperationException e) {
      promise.reject(TAG + " ERROR_IS_LOCKED", e);
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
          synchronized (RNAccModule.this) {
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

  /******************************
   *
   * In-app methods
   *
   ********************************/

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
        synchronized (RNAccModule.this) {
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
          synchronized (RNAccModule.this) {
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
          synchronized (RNAccModule.this) {
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
          synchronized (RNAccModule.this) {
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
  public void setInAppLocked(Boolean isEnabled) {
    A4S.get(mReactContext).setInAppDisplayLocked(isEnabled);
  }

  @ReactMethod
  public void isInAppLocked(Promise promise) {
    synchronized (this) {
      if (mIsInAppLockedPromise != null) {
        Log.w(TAG, "isLocked Promise is replaced by a new one");
      }
      mIsInAppLockedPromise = promise;
      A4S.get(mReactContext).isInAppDisplayLocked(new A4S.Callback<Boolean>() {
        @Override
        public void onResult(Boolean isLocked) {
          Promise promise;
          synchronized (RNAccModule.this) {
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

  /**************************************
   *
   * Control methods (Optin)
   *
   **************************************/

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

  /****************************************
   *
   * DeviceInfo methods
   *
   *****************************************/

  @ReactMethod
  public void updateDeviceInformation(String method, String key, String value) {
    mDeviceInformation = new DeviceInformation();
    switch (method) {
      case "set":
        mDeviceInformation.set(key, value);
        A4S.get(mReactContext).updateDeviceInformation(mDeviceInformation);
        break;
      case "delete":
        mDeviceInformation.delete(key);
        A4S.get(mReactContext).updateDeviceInformation(mDeviceInformation);
        break;
      case "increment":
      case "decrement":
        double input;
        try {
          input = Double.parseDouble(value);
        } catch (NumberFormatException e) {
          Log.e("UpdateDeviceInfo | ", "Increment without a numeric value !");
          break;
        }
        if (method.equals("increment")) {
          mDeviceInformation.increment(key, input);
        } else {
          mDeviceInformation.decrement(key, input);
        }
        A4S.get(mReactContext).updateDeviceInformation(mDeviceInformation);
        break;
    }
  }

  @ReactMethod
  public void updateDeviceInfo(ReadableMap deviceInfoMap) {
    if (!(deviceInfoMap instanceof ReadableNativeMap)) {
      Log.e(TAG, "updateDeviceInfo: deviceInfoMap is not a js object");
      return;
    }

    Bundle bundle = new Bundle();
    Map<String, Object> info = Utils.recursivelyDeconstructReadableMap(deviceInfoMap);
    for (Map.Entry<String, Object> entry : info.entrySet()) {
      if (!(entry.getValue() instanceof String)) {
        Log.w(TAG, "updateDeviceInfo: key '" + entry.getKey() + "' doesn't contain a String object");
        continue;
      }
      bundle.putString(entry.getKey(), (String) entry.getValue());
    }
    A4S.get(mReactContext).updateDeviceInfo(bundle);
  }

  /****************************************
   *
   * DeviceTag methods
   *
   ****************************************/

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

  /****************************************
   *
   * States methods
   *
   ****************************************/

  @ReactMethod
  public void setState(String name, String value) {
    A4S.get(mReactContext).putState(name, value);
  }

  /****************************************
   *
   * Static lists methods
   *
   ****************************************/

  @ReactMethod
  public void getStaticListsSubscribed(final Promise promise) {
    Log.i(TAG, "Getting Static Lists Subscribed...");
    //Creating the Callback
    com.ad4screen.sdk.A4S.Callback<List<StaticList>> subscriptionCallback = new A4S.Callback<List<StaticList>>() {

      @Override
      public void onResult(List<StaticList> result) {
        WritableArray array = Arguments.createArray();
        if (result != null) {
          Log.i(TAG, "Lists subscribed");
          int id = 0;
          for (StaticList staticList : result) {
            WritableMap map = Arguments.createMap();
            id++;
            Log.i(TAG, "ExternalId: " + staticList.getListId());
            Log.i(TAG, "Name: " + staticList.getName());
            Log.i(TAG, "Expiration date: " + staticList.getExpireAt());
            Log.i(TAG, "key : " + id);
            map.putInt("key", id);
            map.putString("listID", staticList.getListId());
            map.putString("name", staticList.getName());
            map.putInt("status", staticList.getStatus());
            Date expirationDate = staticList.getExpireAt();
            double time = expirationDate.getTime()/1000L;
            Log.i(TAG, "Timestamp : " + time);
            map.putDouble("expirationDate", time);
            array.pushMap(map);
          }
          promise.resolve(array);
        }
      }

      @Override
      public void onError(int error, String errorMessage) {
      }
    };
    A4S.get(getReactApplicationContext()).getListOfSubscriptions(subscriptionCallback);
  }

  @ReactMethod
  //The staticListID is the field "external id" in the Accengage User Interface.
  public void subscribeToLists(ReadableArray staticLists) {
    try {
      for (int i=0; i < staticLists.size(); i++ ) {
        ReadableMap map = staticLists.getMap(i);
        if(map.getInt("expirationDate") == NaN) {
          Log.i(TAG, "No expiration date found");
          StaticList staticList = new StaticList(map.getString("listId"));
          Log.i(TAG, "Subscribing to list : " + staticList.getListId());
          A4S.get(getReactApplicationContext()).subscribeToLists(staticList);
          Log.i(TAG, "Successfully subscribed to list : " + staticList.getListId());
        } else {
          int expirationDate = map.getInt("expirationDate");
          Date date = new Date((long)expirationDate*1000);
          Log.i(TAG, "Expiration date : " + date);
          StaticList staticList = new StaticList(map.getString("listId"), date);
          Log.i(TAG, "Subscribing to list : " + staticList.getListId());
          A4S.get(getReactApplicationContext()).subscribeToLists(staticList);
          Log.i(TAG, "Successfully subscribed to list : " + staticList.getListId());
        }
      }
    } catch (Exception e) {
      Log.e(TAG, "Error : " + e);
    }
  }

  @ReactMethod
  public void unsubscribeFromLists(ReadableArray staticLists) {
    for (int i=0; i < staticLists.size(); i++ ) {
      ReadableMap map = staticLists.getMap(i);
      StaticList staticList = new StaticList(map.getString("listId"));
      Log.i(TAG, "Unsubscribing from list : " + staticList.getListId());
      A4S.get(getReactApplicationContext()).unsubscribeFromLists(staticList);
      Log.i(TAG, "Successfully unsubscribed from list : " + staticList.getListId());
    }
  }

  @ReactMethod
  public void getSubscriptionStatusForLists(ReadableArray staticLists, final Promise promise) {
    final List<StaticList> listStaticLists = new ArrayList<>();
    for (int i=0; i < staticLists.size(); i++ ) {
      ReadableMap map = staticLists.getMap(i);
      //Putting the StaticList in a List for the function
      StaticList staticList = new StaticList(map.getString("listId"));
      listStaticLists.add(staticList);
      Log.i(TAG, "Getting status for list : " + staticList.getListId());
    }
    //Creating the Callback
    com.ad4screen.sdk.A4S.Callback<List<com.ad4screen.sdk.StaticList>> subscriptionCallback = new A4S.Callback<List<StaticList>>() {

      @Override
      public void onResult(List<StaticList> result) {
        WritableMap map = Arguments.createMap();
        if (result != null) {
          Log.i(TAG, "List detail");
          for (StaticList staticList : result) {
            Log.i(TAG, "ID: " + staticList.getListId());
            Log.i(TAG, "Name: " + staticList.getName());
            int statusCode = staticList.getStatus();
            if (statusCode == 2){
              String status = "Subscribed";
              map.putString("status", status);
            } else if (statusCode == 4) {
              String status = "Unsubscribed";
              map.putString("status", status);
            } else {
              String status = "Unknown";
              map.putString("status", status);
            }
            Log.i(TAG, "Status: " + staticList.getStatus());
            map.putString("id", staticList.getListId());
            map.putString("name", staticList.getName());
            Date expirationDate = staticList.getExpireAt();
            double time = expirationDate.getTime()/1000L;
            Log.i(TAG, "Timestamp : " + time);
            map.putDouble("expirationDate", time);
          }
          WritableArray arrayList = Arguments.createArray();
          arrayList.pushMap(map);
          promise.resolve(arrayList);
        }
      }

      @Override
      public void onError(int error, String errorMessage) {
      }
    };
    A4S.get(getReactApplicationContext()).getSubscriptionStatusForLists(listStaticLists, subscriptionCallback);
  }

  /******************************************
   *
   * Tracking (events) methods
   *
   *******************************************/

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

  @ReactMethod
  public void trackLead(String leadLabel, String leadValue) {
    if (leadLabel == null || leadLabel.isEmpty()) {
      Log.w(TAG, "trackLead: there is no label");
      return;
    }
    if (leadValue == null || leadValue.isEmpty()) {
      Log.w(TAG, "trackLead: there is no value");
      return;
    }

    Lead lead = new Lead(leadLabel, leadValue);
    A4S.get(getReactApplicationContext()).trackLead(lead);
  }

  @ReactMethod
  public void trackCart(String name, String currency, ReadableMap itemMap) {
    if (name == null || name.isEmpty()) {
      Log.w(TAG, "trackAddToCart: there is no cart name");
      return;
    }
    if (currency == null || currency.isEmpty()) {
      Log.w(TAG, "trackAddToCart: there is no currency");
      return;
    }
    if (!(itemMap instanceof ReadableNativeMap)) {
      Log.w(TAG, "trackAddToCart: item is not a js object");
      return;
    }

    if (!(itemMap instanceof ReadableNativeMap)) {
      Log.w(TAG, "trackAddToCart: item is not js Object (ReadableMap)");
      return;
    }
    Map<String, Object> hashMap = Utils.recursivelyDeconstructReadableMap(itemMap);
    Item item = convertFromMapToItem(currency, hashMap);
    if (item == null) {
      Log.w(TAG, "trackAddToCart: can't convert js Object to Item");
      return;
    }
    Cart cart = new Cart(name, item);
    A4S.get(mReactContext).trackAddToCart(cart);
  }

  @ReactMethod
  public void trackPurchase(String name, String currency, Double price, ReadableArray itemsArray) {
    if (name == null || name.isEmpty()) {
      Log.w(TAG, "trackPurchase: there is no purchase name");
      return;
    }
    if (currency == null || currency.isEmpty()) {
      Log.w(TAG, "trackPurchase: there is no currency");
      return;
    }
    if (!(itemsArray instanceof ReadableNativeArray)) {
      Log.w(TAG, "trackPurchase: item is not js Array (ReadableArray)");
      return;
    }
    List<Object> itemMapList = Utils.recursivelyDeconstructReadableArray(itemsArray);
    List<Item> purchaseItems = new ArrayList<>(itemMapList.size());
    for (Object itemMap : itemMapList) {
      Item item = convertFromMapToItem(currency, (Map<String, Object>) itemMap);
      if (item == null) {
        Log.w(TAG, "trackPurchase: can't convert js Object to Item");
        return;
      }
      purchaseItems.add(item);
    }
    Purchase purchase = new Purchase(name, currency, price, purchaseItems.toArray(new Item[purchaseItems.size()]));
    A4S.get(mReactContext).trackPurchase(purchase);
  }

  private Item convertFromMapToItem(String currency, Map<String, Object> hashMap) {
    // Check id of item
    if (!(hashMap.get("id") instanceof String)) {
      Log.w(TAG, "convertFromMapToItem: id of item is not a String");
      return null;
    }
    String id = (String) hashMap.get("id");
    if (id == null || id.isEmpty()) {
      Log.w(TAG, "convertFromMapToItem: there is no id of item");
      return null;
    }
    // Check label of item
    if (!(hashMap.get("label") instanceof String)) {
      Log.w(TAG, "convertFromMapToItem: label of item is not a String");
      return null;
    }
    String label = (String) hashMap.get("label");
    if (label == null || label.isEmpty()) {
      Log.w(TAG, "convertFromMapToItem: there is no label of item");
      return null;
    }
    // Check category of item
    if (!(hashMap.get("category") instanceof String)) {
      Log.w(TAG, "convertFromMapToItem: category of item is not a String");
      return null;
    }
    String category = (String) hashMap.get("category");
    if (category == null || category.isEmpty()) {
      Log.w(TAG, "convertFromMapToItem: there is no category of item");
      return null;
    }
    // Check price of item
    if (!(hashMap.get("price") instanceof Double)) {
      Log.w(TAG, "convertFromMapToItem: price of item is not a Number");
      return null;
    }
    Double price = (Double) hashMap.get("price");
    if (price == null) {
      Log.w(TAG, "convertFromMapToItem: there is no price of item");
      return null;
    }
    // Check price of item
    if (!(hashMap.get("price") instanceof Double)) {
      Log.w(TAG, "convertFromMapToItem: price of item is not a Number");
      return null;
    }
    Double quantity = (Double) hashMap.get("quantity");
    if (quantity == null) {
      Log.w(TAG, "convertFromMapToItem: there is no quantity of item");
      return null;
    }
    return new Item(id, label, category, currency, price, quantity.intValue());
  }

  /****************************************
   *
   * Views methods
   *
   *****************************************/

  @ReactMethod
  public void setView(String viewName) {
    Log.i(TAG, "Received view " + viewName);
    A4S.get(mReactContext).setView(viewName);
  }

  @ReactMethod
  public void dismissView(String viewName) {
    Log.i(TAG, "Received view " + viewName);
    A4S.get(mReactContext).dismissView(viewName);
  }

}