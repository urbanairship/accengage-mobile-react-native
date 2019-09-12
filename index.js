'use strict';
import { NativeModules, Platform } from 'react-native';

module.exports = require('./lib/acc');
const { RNAcc } = NativeModules;

/** Accengage Module **/
exports.RNAcc = require('./acc.js');
