// deviceinfo.js
'use strict';
import { NativeModules } from 'react-native';

const AccDeviceInfo = NativeModules.RNAccDeviceInfo;

class DeviceInfo {

    updateDeviceInfo(deviceInfo) {
        AccDeviceInfo.updateDeviceInfo(deviceInfo);
    }
}

var deviceInfo = new DeviceInfo();

exports.deviceInfo = deviceInfo;