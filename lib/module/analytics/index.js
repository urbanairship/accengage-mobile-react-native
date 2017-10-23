// index.js
'use strict';

var moduleStaticList = require('./staticlist');
var moduleTracking = require('./tracking');


module.exports = {
    staticlist: moduleStaticList.staticList,
    tracking: moduleTracking.tracking
}