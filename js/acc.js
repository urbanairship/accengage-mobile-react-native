// acc.js
'use strict';
import { NativeModules, Platform } from 'react-native';

const RNAccModule = NativeModules.RNAccModule;

//In-app variables
const InAppManagerEmitter = (Platform.OS === 'ios') ? new NativeEventEmitter(RNAccModule) : undefined;

///// Main class of the React Bridge
 class Acc {

  ///////////////////////
	//// Push Methods /////
  ///////////////////////
	setPushEnabled(isEnabled) {
    	RNAccModule.setEnabled(isEnabled);
  }

	setCustomCategories(customCategories) {
  	RNAccModule.setCustomCategories(customCategories);
	}

	isPushEnabled() {
  	return RNAccModule.isEnabled();
	}

	setProvisionalEnabled(isEnabled) {
  	RNAccModule.setProvisionalEnabled(isEnabled);
	}

	setPushLocked(isEnabled) {
  	RNAccModule.setLocked(isEnabled);
	}

	isPushLocked() {
  	return RNAccModule.isLocked();
	}

	setToken(token) {
  	if (Platform.OS === 'android') {
    		RNAccModule.setToken(token);
  	}
	}

	getToken() {
  	return RNAccModule.getToken();
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
          RNAccModule.setDisplayedCallback(callback);
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
          RNAccModule.setClickedCallback(callback);
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
          RNAccModule.setClosedCallback(callback);
    }

    setInAppLocked(isEnabled) {
      RNAccModule.setInAppLocked(isEnabled);
    }

    isInAppLocked() {
        return RNAccModule.isInAppLocked();
    }

  ///////////////////////
  //// Control Methods //
  ///////////////////////

  setOptinDataEnabled(isEnabled) {
    RNAccModule.setOptinDataEnabled(isEnabled);
  }

  setOptinGeolocEnabled(isEnabled) {
    RNAccModule.setOptinGeolocEnabled(isEnabled);
  }
  
  isOptinDataEnabled() {
    return RNAccModule.isOptinDataEnabled();
  }

  isOptinGeolocEnabled() {
    return RNAccModule.isOptinGeolocEnabled();
  }
 
  setAllServicesEnabled(isEnabled) {
    if (Platform.OS === 'ios') {
        RNAccModule.setAllServicesEnabled(isEnabled);
    }
  }

  areAllServicesEnabled() {
    if (Platform.OS === 'ios') {
      return RNAccModule.areAllServicesEnabled();
    } else {
      return new Promise(function (resolve) {
        resolve(true);
      });
    }
  }

  setNetworkCallsEnabled(isEnabled) {
    if (Platform.OS === 'ios') {
      RNAccModule.setNetworkCallsEnabled(isEnabled);
    }
  }

  areNetworkCallsEnabled() {
    if (Platform.OS === 'ios') {
      return RNAccModule.areNetworkCallsEnabled();
    } else {
      return new Promise(function (resolve) {
        resolve(true);
      });
    }
  }

  setGeofenceServiceEnabled(isEnabled) {
    if (Platform.OS === 'ios') {
      RNAccModule.setGeofenceServiceEnabled(isEnabled);
    }
  }

  isGeofenceServiceEnabled() {
    if (Platform.OS === 'ios') {
      return RNAccModule.isGeofenceServiceEnabled();
    }
    if (Platform.OS === 'android') {
      return new Promise(function (resolve) {
        resolve(true);
      });
    }
  }

  setBeaconServiceEnabled(isEnabled) {
    if (Platform.OS === 'ios') {
      RNAccModule.setBeaconServiceEnabled(isEnabled);
    }
  }

  isBeaconServiceEnabled() {
    if (Platform.OS === 'ios') {
      return RNAccModule.isBeaconServiceEnabled();
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
        RNAccModule.updateDeviceInfo(deviceInfo);
  }
    
  updateDeviceInformation(action, key, value) {
        RNAccModule.updateDeviceInformation(action, key, value);
  }

  setDeviceTag(category, identifier, items) {
        RNAccModule.setDeviceTag(category, identifier, items);
  }
    
  deleteDeviceTag(category, identifier) {
        RNAccModule.deleteDeviceTag(category, identifier);
  }

  setState(name, value) {
      if (Platform.OS === 'ios') {
          RNAccModule.setState(value, name);
      } else {
        RNAccModule.setState(name, value);
      }
  }

  subscribeToLists(lists) {
        RNAccModule.subscribeToLists(lists);
  }

  unsubscribeFromLists(lists) {
      RNAccModule.unsubscribeFromLists(lists);
  }

  getSubscriptionStatusForLists(lists) {
      return RNAccModule.getSubscriptionStatusForLists(lists);
  }

  getStaticListsSubscribed() {
      return RNAccModule.getStaticListsSubscribed();
  }

  trackEvent(id, parameters) {
      RNAccModule.trackEvent(id, parameters);
  }

  trackCustomEvent(id, parameters) {
      if (Platform.OS === 'ios') {
          RNAccModule.trackCustomEvent(id, parameters);
      } else if (Platform.OS === 'android') {
          RNAccModule.trackEvent(id, parameters);
      }
  }

  trackLead(key, value) {
      RNAccModule.trackLead(key, value);
  }

  trackCart(cartId, currency, item) {
      RNAccModule.trackCart(cartId, currency, item);
  }

  trackPurchase(purchaseId, currency, price, items) {
      RNAccModule.trackPurchase(purchaseId, currency, price, items);
  }

  setView(viewName) {
      RNAccModule.setView(viewName);
  }

  dismissView(viewName) {
      RNAccModule.dismissView(viewName);
  }

}

module.exports = Acc;