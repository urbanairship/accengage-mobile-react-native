import { AppRegistry } from 'react-native';
import App from './App';
import { name as appName } from "./app.json";  
//require('RCTNativeAppEventEmitter');
AppRegistry.registerComponent(appName, () => App);
