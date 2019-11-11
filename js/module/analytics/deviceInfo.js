// deviceinfo.js
'use strict';
import { NativeModules } from 'react-native';

const AccDeviceInfo = NativeModules.RNAccDeviceInfo;

class DeviceInfo {

    updateDeviceInfo(deviceInfo) {
        AccDeviceInfo.updateDeviceInfo(deviceInfo);
    }
    
    updateDeviceInformation(action, key, value) {
        AccDeviceInfo.updateDeviceInformation(action, key, value);
    }
}

var deviceInfo = new DeviceInfo();

exports.deviceInfo = deviceInfo;
