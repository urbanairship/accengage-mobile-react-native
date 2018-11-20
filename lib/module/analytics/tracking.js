// tracking.js
'use strict';
import { NativeModules } from 'react-native';

const AccTracking = NativeModules.RNAccTracking;

class Tracking {

    trackCustomEvent(id, parameters) {
        AccTracking.trackCustomEvent(id, parameters);
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