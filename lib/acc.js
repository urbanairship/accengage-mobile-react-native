// acc.js
'use strict';
import { NativeModules, Platform } from 'react-native';

const { RNAcc } = NativeModules;

var modulePush = require('./module/push');
var moduleInapp = require('./module/inapp');
var moduleControl = require('./module/control');
var moduleAnalytics = require('./module/analytics/index');

module.exports = {
    push: modulePush.push,
    inapp: moduleInapp.inapp,
    control: moduleControl.control,
    analytics: moduleAnalytics,
}