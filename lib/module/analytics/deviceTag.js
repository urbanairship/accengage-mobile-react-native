// deviceTag.js
'use strict';
import { NativeModules } from 'react-native';

const AccDeviceTag = NativeModules.RNAccDeviceTag;

class DeviceTag {

    setDeviceTag(categorie, identifier, items) {
        AccDeviceTag.setDeviceTag(categorie, identifier, items);
    }
    
    deleteDeviceTag(categorie, identifier) {
        AccDeviceTag.deleteDeviceTag(categorie, identifier);
    }
}

var deviceTag = new DeviceTag();

exports.deviceTag = deviceTag;