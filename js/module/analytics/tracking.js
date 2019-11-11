// tracking.js
'use strict';
import { NativeModules, Platform } from 'react-native';

const AccTracking = NativeModules.RNAccTracking;

class Tracking {

    trackEvent(id, parameters) {
        AccTracking.trackEvent(id, parameters);
    }

    trackCustomEvent(id, parameters) {
        if (Platform.OS === 'ios') {
            AccTracking.trackCustomEvent(id, parameters);
        } else if (Platform.OS === 'android') {
            AccTracking.trackEvent(id, parameters);
        }
    }

    trackLead(key, value) {
        AccTracking.trackLead(key, value);
    }

    trackCart(cartId, currency, item) {
        AccTracking.trackCart(cartId, currency, item);
    }

    trackPurchase(purchaseId, currency, price, items) {
        AccTracking.trackPurchase(purchaseId, currency, price, items);
    }

}

var tracking = new Tracking();

exports.tracking = tracking;