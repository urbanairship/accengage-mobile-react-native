import React, { Component } from "react";
import {
  View,
  Text
} from 'react-native';
import Button from 'react-native-button';
import {Acc} from 'react-native-acc';
import styles from './../../Styles';

export default class PushScreen extends Component {
  static navigationOptions = ({navigation}) => ({
    title: "Push",
  });

  constructor() {
    super();
    this.state = {
      buttonEnabledName : 'Enable Push Notifications',
      textLockedName : '',
      textToken : '',
      arePushLocked : false,
    };
    Acc.isPushLocked().then(locked => {
      this.isPushLocked = locked;
      this._updateLockedNames();
    });
    this._setEnabled = this._setEnabled.bind(this);
    this._setLocked = this._setLocked.bind(this);
    this._getToken = this._getToken.bind(this);
  }

  _updateLockedNames() {
    if (this.isPushLocked) {
      this.setState({textLockedName : 'Push notifications are locked'});
      this.setState({arePushLocked : true});
    } else {
      this.setState({textLockedName : 'Push notifications are unlocked'});
      this.setState({arePushLocked : false});
    }
  }

  _setEnabled() {
    this.isPushEnabled = !this.isPushEnabled;
    Acc.setPushEnabled(this.isPushEnabled);
  }

  _setLocked() {
    this.isPushLocked = !this.isPushLocked;
    Acc.setPushLocked(this.isPushLocked);
    this._updateLockedNames();
  }

  _getToken() {
    Acc.getToken().then(token => {
      this.setState({textToken : token})
    });
  }

  render() {
    const { navigate } = this.props.navigation;
    return (
      <View style={styles.container}>
        <Button
          onPress={() => navigate('PushEvents')}
          containerStyle={styles.accbuttoncontainer}
          style={styles.accbutton}>
          Push Events
        </Button>
        <Button
          onPress={this._setEnabled}
          containerStyle={styles.accbuttoncontainer}
          style={styles.accbutton}>
          {this.state.buttonEnabledName}
        </Button>
        <Button
          onPress={this._setLocked}
          style = { this.state.arePushLocked === true ? styles.disabledButton : styles.enabledButton}>
          {this.state.textLockedName}
        </Button>
        <Button
          onPress={this._getToken}
          containerStyle={styles.accbuttoncontainer}
          style={styles.accbutton}>
          Get token
        </Button>
        <Text style={styles.welcome}>
          {this.state.textToken}
        </Text>
      </View>
    );
  }
}
