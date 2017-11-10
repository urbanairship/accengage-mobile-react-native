// inapp.js
'use strict';
import { NativeEventEmitter, NativeModules, Platform} from 'react-native';

const AccInApp = NativeModules.RNAccInApp;
const InAppManagerEmitter = new NativeEventEmitter(AccInApp);

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
    }

    setInAppDisplayEnabled(value) {
        AccInApp.setInAppDisplayEnabled(value);
    }

    isInAppDisplayEnabled() {
        return AccInApp.isInAppDisplayEnabled();
    }
}

var inapp = new InApp();

exports.inapp = inapp;