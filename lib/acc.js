// acc.js
'use strict';

var modulePush = require('./module/push');
var moduleInapp = require('./module/inapp');
var moduleControl = require('./module/control');
var moduleAnalytics = require('./module/analytics/index');

module.exports = {
    push: modulePush.push,
    inapp: moduleInapp.inapp,
    control: moduleControl,
    analytics: moduleAnalytics,
}