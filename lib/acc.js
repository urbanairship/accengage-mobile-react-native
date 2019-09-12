// acc.js
'use strict';
import { NativeModules, Platform } from 'react-native';

const RNAcc = NativeModules.RNAccModule;

//In-app variables
const InAppManagerEmitter = (Platform.OS === 'ios') ? new NativeEventEmitter(RNAcc) : undefined;

///// Main class of the React Bridge
class Accengage {

  ///////////////////////
	//// Push Methods /////
  ///////////////////////
	setPushEnabled(isEnabled) {
    	RNAcc.setEnabled(isEnabled);
  }

	setCustomCategories(customCategories) {
  	RNAcc.setCustomCategories(customCategories);
	}

	isPushEnabled() {
  	return RNAcc.isEnabled();
	}

	setProvisionalEnabled(isEnabled) {
  	RNAcc.setProvisionalEnabled(isEnabled);
	}

	setPushLocked(isEnabled) {
  	RNAcc.setLocked(isEnabled);
	}

	isPushLocked() {
  	return RNAcc.isLocked();
	}

	setToken(token) {
  	if (Platform.OS === 'android') {
    		RNAcc.setToken(token);
  	}
	}

	getToken() {
  	return RNAcc.getToken();
	}

  ///////////////////////
  //// In-App Methods ///
  ///////////////////////

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
          RNAcc.setDisplayedCallback(callback);
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
          RNAcc.setClickedCallback(callback);
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
          RNAcc.setClosedCallback(callback);
    }

    setInAppLocked(isEnabled) {
      RNAcc.setLocked(isEnabled);
    }

    isInAppLocked() {
        return RNAcc.isLocked();
    }

  ///////////////////////
  //// Control Methods //
  ///////////////////////

  setOptinDataEnabled(isEnabled) {
    RNAcc.setOptinDataEnabled(isEnabled);
  }

  setOptinGeolocEnabled(isEnabled) {
    RNAcc.setOptinGeolocEnabled(isEnabled);
  }
  
  isOptinDataEnabled() {
    return RNAcc.isOptinDataEnabled();
  }

  isOptinGeolocEnabled() {
    return RNAcc.isOptinGeolocEnabled();
  }
 
  setAllServicesEnabled(isEnabled) {
    if (Platform.OS === 'ios') {
        RNAcc.setAllServicesEnabled(isEnabled);
    }
  }

  areAllServicesEnabled() {
    if (Platform.OS === 'ios') {
      return RNAcc.areAllServicesEnabled();
    } else {
      return new Promise(function (resolve) {
        resolve(true);
      });
    }
  }

  setNetworkCallsEnabled(isEnabled) {
    if (Platform.OS === 'ios') {
      RNAcc.setNetworkCallsEnabled(isEnabled);
    }
  }

  areNetworkCallsEnabled() {
    if (Platform.OS === 'ios') {
      return RNAcc.areNetworkCallsEnabled();
    } else {
      return new Promise(function (resolve) {
        resolve(true);
      });
    }
  }

  setGeofenceServiceEnabled(isEnabled) {
    if (Platform.OS === 'ios') {
      RNAcc.setGeofenceServiceEnabled(isEnabled);
    }
  }

  isGeofenceServiceEnabled() {
    if (Platform.OS === 'ios') {
      return RNAcc.isGeofenceServiceEnabled();
    }
    if (Platform.OS === 'android') {
      return new Promise(function (resolve) {
        resolve(true);
      });
    }
  }

  setBeaconServiceEnabled(isEnabled) {
    if (Platform.OS === 'ios') {
      RNAcc.setBeaconServiceEnabled(isEnabled);
    }
  }

  isBeaconServiceEnabled() {
    if (Platform.OS === 'ios') {
      return RNAcc.isBeaconServiceEnabled();
    }
    if (Platform.OS === 'android') {
      return new Promise(function (resolve) {
        resolve(true);
      });
    }
  }

  ///////////////////////
  // Analytics Methods //
  ///////////////////////

  updateDeviceInfo(deviceInfo) {
        RNAcc.updateDeviceInfo(deviceInfo);
  }
    
  updateDeviceInformation(action, key, value) {
        RNAcc.updateDeviceInformation(action, key, value);
  }

  setDeviceTag(category, identifier, items) {
        RNAcc.setDeviceTag(category, identifier, items);
  }
    
  deleteDeviceTag(category, identifier) {
        RNAcc.deleteDeviceTag(category, identifier);
  }

  setState(name, value) {
      if (Platform.OS === 'ios') {
          RNAcc.setState(value, name);
      } else {
        RNAcc.setState(name, value);
      }
  }

  subscribeToLists(lists) {
        RNAcc.subscribeToLists(lists);
  }

  unsubscribeFromLists(lists) {
      RNAcc.unsubscribeFromLists(lists);
  }

  getSubscriptionStatusForLists(lists) {
      return RNAcc.getSubscriptionStatusForLists(lists);
  }

  getStaticListsSubscribed() {
      return RNAcc.getStaticListsSubscribed();
  }

  trackEvent(id, parameters) {
      RNAcc.trackEvent(id, parameters);
  }

  trackCustomEvent(id, parameters) {
      if (Platform.OS === 'ios') {
          RNAcc.trackCustomEvent(id, parameters);
      } else if (Platform.OS === 'android') {
          RNAcc.trackEvent(id, parameters);
      }
  }

  trackLead(key, value) {
      RNAcc.trackLead(key, value);
  }

  trackCart(cartId, currency, item) {
      RNAcc.trackCart(cartId, currency, item);
  }

  trackPurchase(purchaseId, currency, price, items) {
      RNAcc.trackPurchase(purchaseId, currency, price, items);
  }

  setView(viewName) {
      RNAcc.setView(viewName);
  }

  dismissView(viewName) {
      RNAcc.dismissView(viewName);
  }

}

module.exports = RNAcc;