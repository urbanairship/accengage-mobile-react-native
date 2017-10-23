'use strict';

import { NativeModules } from 'react-native';

const { RNAcc } = NativeModules;

//module.exports = require('./lib/acc');

// var Acc = require('./lib/acc');
// console.log(Acc.inapp.getMsg());
// console.log(Acc.push.getMsg());

export default RNAcc;
