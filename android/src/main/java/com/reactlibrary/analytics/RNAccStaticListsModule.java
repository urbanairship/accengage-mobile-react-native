package com.reactlibrary.analytics;

import android.util.Log;

import com.ad4screen.sdk.A4S;
import com.ad4screen.sdk.A4S.Callback;
import com.ad4screen.sdk.StaticList;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableNativeMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.reactlibrary.common.Utils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Locale;

public class RNAccStaticListsModule extends ReactContextBaseJavaModule {

    private static final String TAG = "AccStaticLists";
    private static final String E_LAYOUT_ERROR = "E_LAYOUT_ERROR";

    private final ReactApplicationContext reactContext;

    public RNAccStaticListsModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "RNAccStaticLists";
    }

    //TODO : Remake, and implement callback with Promise
    @ReactMethod
    public void getStaticListsSubscribed(final Promise promise) {
        Log.i(TAG, "Getting Static Lists Subscribed...");
        //Creating the Callback
        com.ad4screen.sdk.A4S.Callback<List<com.ad4screen.sdk.StaticList>> subscriptionCallback = new Callback<List<StaticList>>() {

            @Override
            public void onResult(List<StaticList> result) {
                WritableArray array = Arguments.createArray();
                if (result != null) {
                    Log.i(TAG, "Lists subscribed");
                    int id = 0;
                    for (StaticList staticList : result) {
                        WritableMap map = Arguments.createMap();
                        id++;
                        Log.i(TAG, "ID: " + staticList.getListId());
                        Log.i(TAG, "Name: " + staticList.getName());
                        Log.i(TAG, "key : " + id);
                        map.putInt("key", id);
                        map.putString("listID", staticList.getListId());
                        map.putString("name", staticList.getName());
                        map.putInt("status", staticList.getStatus());
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
    public void subscribeToLists(String staticListID, String expirationDate) {
        try {
            if(staticListID != null) {
                if(expirationDate == null) {
                    Log.i(TAG, "No expiration date found");
                    StaticList staticList = new StaticList(staticListID);
                    Log.i(TAG, "Subscribing to list : " + staticList.getListId());
                    A4S.get(getReactApplicationContext()).subscribeToLists(staticList);
                    Log.i(TAG, "Successfully subscribed to list : " + staticList.getListId());
                } else {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                    Date date = sdf.parse(expirationDate);
                    Log.i(TAG, "Expiration date : " + date);
                    StaticList staticList = new StaticList(staticListID, date);
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
    public void unsubscribeFromLists(String staticListID) {
        StaticList staticList = new StaticList(staticListID);
        Log.i(TAG, "Unsubscribing from list : " + staticList.getListId());
        A4S.get(getReactApplicationContext()).unsubscribeFromLists(staticList);
        Log.i(TAG, "Successfully unsubscribed from list : " + staticList.getListId());
    }

    @ReactMethod
    public void getSubscriptionStatusForLists(String staticListID, final Promise promise) {
        //Putting the StaticList in a List for the function
        StaticList staticList = new StaticList(staticListID);
        final List<StaticList> staticLists = new ArrayList<>();
        staticLists.add(staticList);
        Log.i(TAG, "Getting status for list : " + staticList.getListId());
        //Creating the Callback
        com.ad4screen.sdk.A4S.Callback<List<com.ad4screen.sdk.StaticList>> subscriptionCallback = new Callback<List<StaticList>>() {

            @Override
            public void onResult(List<StaticList> result) {
                WritableMap map = Arguments.createMap();
                if (result != null) {
                    Log.i(TAG, "List detail");
                    for (StaticList staticList : result) {
                        Log.i(TAG, "ID: " + staticList.getListId());
                        Log.i(TAG, "Name: " + staticList.getName());
                        Log.i(TAG, "Status: " + staticList.getStatus());
                        map.putString("id", staticList.getListId());
                        map.putString("name", staticList.getName());
                        map.putInt("status", staticList.getStatus());
                    }
                    promise.resolve(map);
                }
            }

            @Override
            public void onError(int error, String errorMessage) {
            }
        };
        A4S.get(getReactApplicationContext()).getSubscriptionStatusForLists(staticLists, subscriptionCallback);
    }

}