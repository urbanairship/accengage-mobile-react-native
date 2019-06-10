// deviceTag.js
'use strict';
import { NativeModules } from 'react-native';

const AccDeviceTag = NativeModules.RNAccDeviceTag;

class DeviceTag {

    setDeviceTag(category, identifier, items) {
        AccDeviceTag.setDeviceTag(category, identifier, items);
    }
    
    deleteDeviceTag(category, identifier) {
        AccDeviceTag.deleteDeviceTag(category, identifier);
    }
}

var deviceTag = new DeviceTag();

exports.deviceTag = deviceTag;