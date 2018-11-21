// deviceinfo.js
'use strict';
import { NativeModules } from 'react-native';

const AccDeviceInfo = NativeModules.RNAccDeviceInfo;

class DeviceInfo {

	updateDeviceInformation(method, key, value) {
		AccDeviceInfo.updateDeviceInformation(method, key, value);
	}

    updateDeviceInfo(deviceInfo) {
        AccDeviceInfo.updateDeviceInfo(deviceInfo);
    }
    
    updateDeviceInformation(action, key, value) {
        AccDeviceInfo.updateDeviceInformation(action, key, value);
    }
}

var deviceInfo = new DeviceInfo();

exports.deviceInfo = deviceInfo;