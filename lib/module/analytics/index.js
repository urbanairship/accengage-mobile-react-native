// index.js
'use strict';

var moduleStaticList = require('./staticlist');
var moduleTracking = require('./tracking');
var moduleDeviceInfo = require('./deviceInfo');


module.exports = {
    staticlist: moduleStaticList.staticList,
    tracking: moduleTracking.tracking,
    deviceInfo: moduleDeviceInfo.deviceInfo
}