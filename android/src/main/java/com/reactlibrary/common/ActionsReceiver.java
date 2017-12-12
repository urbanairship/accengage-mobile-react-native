package com.reactlibrary.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.reactlibrary.RNAccModule;

import java.util.Set;

public class ActionsReceiver extends BroadcastReceiver {

    private static final String TAG = "ActionsReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Set<String> cats = intent.getCategories();
        for (String currentCat : cats) {
            String eventName = intent.getAction();
            String msg = "Received notification : " + eventName + "\nC:[" + currentCat + "]";
            Log.d(TAG, msg);
            Log.d(TAG, "Sending event name: " + eventName);
            WritableMap paramsMap = Arguments.createMap();
            if (currentCat.equals("com.ad4screen.sdk.intent.category.PUSH_NOTIFICATIONS")) {
                paramsMap.putString("type", "push");
                //Push received
            } else if (currentCat.equals("com.ad4screen.sdk.intent.category.INAPP_NOTIFICATIONS")) {
                paramsMap.putString("type", "inapp");
            }
            try {
                Bundle bundle = intent.getExtras();
                Log.d(TAG, "BUNDLE EXTRAS : " + bundle);
                if (bundle == null && !eventName.equals("com.ad4screen.sdk.intent.action.DISPLAYED")) {
                    Utils.sendEvent(RNAccModule.getReactContext(), eventName, null);
                    return ;
                }
                Set<String> extras = intent.getExtras().keySet();
                for (String extra : extras) {
                    if (extra.equals("a4sid")) {
                        paramsMap.putString("pushID", intent.getExtras().get(extra).toString());
                    }
                    msg = "Key: " + extra + " Value: " + intent.getExtras().get(extra);
                    Log.d(TAG, msg);
                }
                Utils.sendEvent(RNAccModule.getReactContext(), eventName, paramsMap);
            } catch (Exception e) {
                Log.d(TAG, "No extras");


            }
        }
    }
}