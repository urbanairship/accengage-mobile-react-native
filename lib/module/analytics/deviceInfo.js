// deviceinfo.js
'use strict';
import { NativeModules } from 'react-native';

const AccDeviceInfo = NativeModules.RNAccDeviceInfo;

class DeviceInfo {

    constructor() {
        console.log("DeviceInfo module");
    }

    updateDeviceInfo(deviceInfo) {
        AccDeviceInfo.updateDeviceInfo(deviceInfo);
    }
}

var deviceInfo = new DeviceInfo();

exports.deviceInfo = deviceInfo;