package com.accengage.react.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.accengage.react.RNAccModule;

import java.util.Set;

public class ActionsReceiver extends BroadcastReceiver {

    private static final String TAG = "ActionsReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Set<String> cats = intent.getCategories();
        for (String currentCat : cats) {
            String eventName = "";
            String type = "";
            String actionName = intent.getAction();
            String msg = "Received notification : " + actionName + "\nC:[" + currentCat + "]";
            Log.d(TAG, msg);
            Log.d(TAG, "Sending action name: " + actionName);
            WritableMap paramsMap = Arguments.createMap();
            if (currentCat.equals("com.ad4screen.sdk.intent.category.PUSH_NOTIFICATIONS")) {
                paramsMap.putString("type", "push");
                type = "push";
                //Push received
            } else if (currentCat.equals("com.ad4screen.sdk.intent.category.INAPP_NOTIFICATIONS")) {
                paramsMap.putString("type", "inapp");
                type = "inapp";
            }
            Log.d(TAG, "TYPE NOTIF : " + type);
            try {
                Bundle bundle = intent.getExtras();
                Log.d(TAG, "BUNDLE EXTRAS : " + bundle);
                switch (actionName) {
                    case "com.ad4screen.sdk.intent.action.DISPLAYED":
                        if (type.equals("push")) {
                            eventName = "didReceiveNotification";
                        } else if (type.equals("inapp")) {
                            eventName = "didInAppDisplay";
                        }
                        break;
                    case "com.ad4screen.sdk.intent.action.CLICKED":
                        if (type.equals("push")) {
                            eventName = "didClickNotification";
                        } else if (type.equals("inapp")) {
                            eventName = "didInAppClick";
                        }
                        break;
                    case "com.ad4screen.sdk.intent.action.CLOSED":
                        if (type.equals("inapp")) {
                            eventName = "didInAppClose";
                        }
                        break;
                }
                Log.d(TAG,"EVENT NAME : " + eventName);
                Set<String> extras = intent.getExtras().keySet();
                for (String extra : extras) {
                    if (extra.equals("a4sid")) {
                        paramsMap.putString("pushID", intent.getExtras().get(extra).toString());
                    } else {
                        paramsMap.putString(extra, intent.getExtras().get(extra).toString());
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