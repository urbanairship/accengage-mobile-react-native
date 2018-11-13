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
}

var deviceInfo = new DeviceInfo();

exports.deviceInfo = deviceInfo;