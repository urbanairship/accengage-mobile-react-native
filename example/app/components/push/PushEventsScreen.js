import React, { Component } from "react";
import {
  View,
  Text,
  DeviceEventEmitter,
  NativeEventEmitter,
  NativeModules,
  Platform
} from 'react-native';
import CheckBox from 'react-native-checkbox';
import styles from '../../../Styles';

export default class PushEventsScreen extends Component {
  static navigationOptions = ({navigation}) => ({
    title: "Push Events",
  });

  constructor() {
    super();
    this.state = {
      receiveEventSubscription : undefined,
      clickEventSubscription : undefined,
      receiveEventEnabled: false,
      clickEventEnabled: false,
      textTypeEvent : '',
      textNotifId : '',
      textCustomParams : ''
    };
    this.pushManagerEmitter = new NativeEventEmitter(NativeModules.RNAcc);
    this._setReceive = this._setReceive.bind(this);
    this._setClick = this._setClick.bind(this);
  }

  _setReceive(checked) {
    this.setState({ receiveEventEnabled : !checked });
    
    
     if (Platform.OS == "ios") {
      if (!checked) {
        this.setState({
          receiveEventSubscription : this.pushManagerEmitter.addListener(
            'didReceiveNotification',
            (reminder) => this.setState({
              textTypeEvent : "Receive",
              textNotifId : reminder.notification.notificationId,
              textCustomParams : JSON.stringify(reminder.notification.customParams)
            })
            )
        });
      } else {
        this.state.receiveEventSubscription.remove();
        this.setState({ textCustomParams : ''});
      }

    } else if (Platform.OS == "android") {
      if (!checked) {
        this.receiveEventSubscription = DeviceEventEmitter.addListener('didReceiveNotification', (e: Event) => {
          console.log("A4S|didReceiveNotification|Params: " + JSON.stringify(e));
          this.setState({
            textTypeEvent : "Receive",
            textNotifId: e.pushID,
            textCustomParams: JSON.stringify(e)
          })
        });
      }
      else {
        this.receiveEventSubscription.remove();
        this.setState({
          textTypeEvent: '',
          textNotifId: '',
          textCustomParams: ''
        });
      }
    }
  }

  _setClick(checked) {
    this.setState({ clickEventEnabled : !checked });
    if (Platform.OS == "ios") {
      if (!checked) {
        this.setState({
          clickEventSubscription : this.pushManagerEmitter.addListener(
            'didClickNotification',
            (reminder) => this.setState({
              textTypeEvent : "Click",
              textNotifId : reminder.notification.notificationId,
              textCustomParams : JSON.stringify(reminder.notification.customParams)
            })
          )
        });
      } else {
        this.state.clickEventSubscription.remove();
        this.setState({ textCustomParams : ''});
      }
    }
    else if (Platform.OS == "android") {
      if (!checked) {
        console.log("Listener added");
        this.receiveEventSubscription = DeviceEventEmitter.addListener('didClickNotification', (e: Event) => {
          console.log("A4S|didClickNotification|Params: " + JSON.stringify(e));
          this.setState({
            textTypeEvent : "Clicked",
            textNotifId: e.pushID,
            textCustomParams: JSON.stringify(e)
          })
        });
      }
      else {
        this.receiveEventSubscription.remove();
        this.setState({
          textTypeEvent: '',
          textNotifId: '',
          textCustomParams: ''
        });
      }
    }
  }

  _clear() {
    this.setState({ textTypeEvent : ''});
    this.setState({ textNotifId : ''});
    this.setState({ textCustomParams : ''});
  }

  componentWillUnmount() {
    this._clear();
    if (this.state.receiveEventSubscription !== undefined) {
      this.state.receiveEventSubscription.remove();
    }
    if (this.state.clickEventSubscription !== undefined) {
      this.state.clickEventSubscription.remove();
    }
  }

  render() {
    return (
      <View style={styles.container}>
        <CheckBox
          label='Received'
          checked={this.state.receiveEventEnabled}
          onChange={this._setReceive}
        />
        <CheckBox
          label='Clicked'
          checked={this.state.clickEventEnabled}
          onChange={this._setClick}
        />
        <Text style={styles.welcome}>
          {this.state.textTypeEvent}
        </Text>
        <Text style={styles.welcome}>
          {this.state.textCustomParams}
        </Text>
        <Text style={styles.welcome}>
          {this.state.textNotifId}
        </Text>
      </View>
    );
  }
}
