
package com.reactlibrary.analytics;

import android.util.Log;

import com.ad4screen.sdk.A4S;
import com.ad4screen.sdk.analytics.Cart;
import com.ad4screen.sdk.analytics.Item;
import com.ad4screen.sdk.analytics.Lead;
import com.ad4screen.sdk.analytics.Purchase;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableNativeArray;
import com.facebook.react.bridge.ReadableNativeMap;
import com.reactlibrary.common.Utils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RNAccTrackingModule extends ReactContextBaseJavaModule {

    private static final String TAG = "AccTracking";

    private final ReactApplicationContext mReactContext;

    public RNAccTrackingModule(ReactApplicationContext reactContext) {
        super(reactContext);
        mReactContext = reactContext;
    }

    @Override
    public String getName() {
        return "RNAccTracking";
    }

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
        Map<String, Object> hashMap = Utils.recursivelyDeconstructReadableMap(String currency, itemMap);
        Item item = convertFromMapToItem(hashMap);
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
            Item item = convertFromMapToItem((Map<String, Object>) itemMap);
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
}