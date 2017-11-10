// inapp.js
'use strict';
import { NativeEventEmitter, NativeModules, Platform} from 'react-native';

const AccInApp = NativeModules.RNAccInApp;

const InAppManagerEmitter = (Platform.OS === 'ios') ? new NativeEventEmitter(AccInApp) : undefined;

class InApp {
    constructor() {
        console.log("InApp module");
    }

    setInAppDisplayedCallback(callback) {
        if (Platform.OS === 'ios') {
            const subscription = InAppManagerEmitter.addListener(
                'onInAppNotifDidAppear',
                (reminder) => {
                    callback(reminder.inApp);
                    subscription.remove();
                });
        }
        if (Platform.OS === 'android')
        	AccInApp.setInAppDisplayedCallback(callback);
    }

    setInAppClickedCallback(callback) {
        if (Platform.OS === 'ios') {
            const subscription = InAppManagerEmitter.addListener(
                'onInAppNotifClicked',
                (reminder) => {
                    callback(reminder.inApp);
                    subscription.remove();
                });
        }
        if (Platform.OS === 'android')
        	AccInApp.setInAppClickedCallback(callback);
    }

    setInAppClosedCallback(callback) {
        if (Platform.OS === 'ios') {
            const subscription = InAppManagerEmitter.addListener(
                'onInAppNotifClosed',
                (reminder) => {
                    callback(reminder.inApp);
                    subscription.remove();
                });
        }
        if (Platform.OS === 'android')
        	AccInApp.setInAppClosedCallback(callback);
    }

    setInAppDisplayEnabled(isEnabled) {
      AccInApp.setInAppDisplayEnabled(isEnabled);
    }

    isInAppDisplayEnabled() {
        return AccInApp.isInAppDisplayEnabled();
    }
}

var inapp = new InApp();

exports.inapp = inapp;