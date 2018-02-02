// inapp.js
'use strict';
import { NativeEventEmitter, NativeModules, Platform} from 'react-native';

const AccInApp = NativeModules.RNAccInApp;

const InAppManagerEmitter = (Platform.OS === 'ios') ? new NativeEventEmitter(AccInApp) : undefined;

class InApp {

    setDisplayedCallback(callback) {
        if (Platform.OS === 'ios') {
            const subscription = InAppManagerEmitter.addListener(
                'didInAppDisplay',
                (reminder) => {
                    callback(reminder.inApp);
                    subscription.remove();
                });
        }
        if (Platform.OS === 'android')
          AccInApp.setDisplayedCallback(callback);
    }

    setClickedCallback(callback) {
        if (Platform.OS === 'ios') {
            const subscription = InAppManagerEmitter.addListener(
                'didInAppClick',
                (reminder) => {
                    callback(reminder.inApp);
                    subscription.remove();
                });
        }
        if (Platform.OS === 'android')
          AccInApp.setClickedCallback(callback);
    }

    setClosedCallback(callback) {
        if (Platform.OS === 'ios') {
            const subscription = InAppManagerEmitter.addListener(
                'didInAppClose',
                (reminder) => {
                    callback(reminder.inApp);
                    subscription.remove();
                });
        }
        if (Platform.OS === 'android')
          AccInApp.setClosedCallback(callback);
    }

    setLocked(isEnabled) {
      AccInApp.setLocked(isEnabled);
    }

    isLocked() {
        return AccInApp.isLocked();
    }
}

var inapp = new InApp();

exports.inapp = inapp;