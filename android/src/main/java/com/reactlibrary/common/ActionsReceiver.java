package com.reactlibrary.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.reactlibrary.RNAccModule;

import java.util.Set;

public class ActionsReceiver extends BroadcastReceiver {

    private static final String TAG = "ActionsReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Set<String> cats = intent.getCategories();
        for (String currentCat : cats) {
            String msg = "Received notification : " + intent.getAction() + "\nC:[" + currentCat + "]";
            Log.d(TAG, msg);
            String eventName = currentCat;
            Log.d(TAG, "Sending event name: " + eventName);
            Utils.sendEvent(RNAccModule.getReactContext(), eventName, null);
        }

        try {
            Set<String> extras = intent.getExtras().keySet();
            for (String extra : extras) {
                String msg = "Key: " + extra + " Value: " + intent.getExtras().get(extra);
                Log.d(TAG, msg);
            }

        } catch (Exception e) {
            Log.d(TAG, "No extras");
        }
    }
}