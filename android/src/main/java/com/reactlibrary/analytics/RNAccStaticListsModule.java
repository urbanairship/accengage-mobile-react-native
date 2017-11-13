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
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableNativeMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.reactlibrary.common.Utils;

import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Locale;

import static java.lang.Double.NaN;

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
        com.ad4screen.sdk.A4S.Callback<List<com.ad4screen.sdk.StaticList>> subscriptionCallback = new Callback<List<StaticList>>() {

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
                    promise.resolve(map);
                }
            }

            @Override
            public void onError(int error, String errorMessage) {
            }
        };
        A4S.get(getReactApplicationContext()).getSubscriptionStatusForLists(listStaticLists, subscriptionCallback);
    }

}