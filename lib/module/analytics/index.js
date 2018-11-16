// index.js
'use strict';

var moduleStaticList = require('./staticlist');
var moduleTracking = require('./tracking');
var moduleDeviceInfo = require('./deviceInfo');
var moduleDeviceTag = require('./deviceTag');
var moduleStates = require('./states');
var moduleViews = require('./views');


module.exports = {
    staticlist: moduleStaticList.staticList,
    tracking: moduleTracking.tracking,
    deviceInfo: moduleDeviceInfo.deviceInfo,
    deviceTag: moduleDeviceTag.deviceTag,
    states: moduleStates.states,
    views: moduleViews.views
}