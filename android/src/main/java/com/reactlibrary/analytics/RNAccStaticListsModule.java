package com.reactlibrary.analytics;

import android.util.Log;

import com.ad4screen.sdk.A4S;
import com.ad4screen.sdk.StaticList;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableNativeMap;
import com.facebook.react.bridge.Callback;
import com.reactlibrary.common.Utils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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

    @ReactMethod
    //TODO : Remake, and implement callback with Promise
    public void getListsOfSubscriptions() {
        Log.i(TAG, "getListsOfSubscriptions started");
//        com.ad4screen.sdk.A4S.Callback<List<com.ad4screen.sdk.StaticList>> subscriptionCallback = new Callback<List<StaticList>>() {
//
//            @Override
//            public void onResult(List<StaticList> result) {
//                if (result != null) {
//                    Log.i(TAG, "List detail with subscription true");
//                    for (StaticList staticList : result) {
//                        Log.i(TAG, "ID: " + staticList.getListId());
//                        Log.i(TAG, "Name: " + staticList.getName());
//                    }
//                } else {
//                    Log.i(TAG, "No result.");
//                }
//            }
//
//            @Override
//            public void onError(int error, String errorMessage) {
//                Log.e("Error");
//                // Process error
//                // Error code 0: Network error
//                // Error code 1: The list does not exist
//            }
//        };
//
//        A4S.get(getReactApplicationContext()).getListOfSubscriptions(subscriptionCallback);
    }

    @ReactMethod
    public void subscribeToLists(String staticListID) {
        StaticList staticList = new StaticList(staticListID);
        Log.i(TAG, "Subscribing to list : " + staticList.getListId());
        A4S.get(getReactApplicationContext()).subscribeToLists(staticList);
        Log.i(TAG, "Successfully subscribed to list : " + staticList.getListId());

    }

    @ReactMethod
    public void unsubscribeFromLists(String staticListID) {
        StaticList staticList = new StaticList(staticListID);
        Log.i(TAG, "Unsubscribing from list : " + staticList.getListId());
        A4S.get(getReactApplicationContext()).unsubscribeFromLists(staticList);
        Log.i(TAG, "Successfully unsubscribed from list : " + staticList.getListId());
    }

    //TODO : Implement Callback
    @ReactMethod
    public void getSubscriptionStatusForLists(String staticListID) {
//        StaticList staticList = new StaticList(staticListID);
//        Log.i(TAG, "Getting status for list : " + staticList.getListId());
//        A4S.get(getReactApplicationContext()).getSubscriptionStatusForList(staticList);
//        Log.i(TAG, "List ID : " + staticList.getListId());
//        Log.i(TAG, "List Name : " + staticList.getName());
//        Log.i(TAG, "List Status : " + staticList.getStatus());
    }

}