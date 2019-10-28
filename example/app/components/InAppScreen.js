import React, { Component } from "react";
import {
  View
} from 'react-native';
import Button from 'react-native-button';
import {Acc} from 'react-native-acc';
import styles from './../../Styles';

function setInAppDisplayedCallback() {
  Acc.setInAppDisplayedCallback(
    (inapp) => {
      console.log("setDisplayedCallback inapp: " + JSON.stringify(inapp));
    });
}

function setInAppClickedCallback() {
  Acc.setInAppClickedCallback(
    (inapp) => {
      console.log("setClickedCallback inapp: " + JSON.stringify(inapp));
    });
}

function setInAppClosedCallback() {
  Acc.setInAppClosedCallback(
    (inapp) => {
      console.log("setClosedCallback inapp: " + JSON.stringify(inapp));
    });
}



export default class InAppScreen extends Component {
  static navigationOptions = ({navigation}) => ({
    title: "InApp",
  });

  constructor() {
    super();
    this.isInAppLocked = false;
    this.state = {
      buttonIsInAppLockedName : 'Lock/Unlock InApps',
    };

    this._setInAppLocked = this._setInAppLocked.bind(this);
    this._checkInAppLocked();
  }

  async _checkInAppLocked() {
    console.log("_checkInAppLocked");
    try {
      this.isInAppLocked = await Acc.isInAppLocked();
      console.log("_checkInAppLocked OK:" + this.isInAppLocked);
      this._updateButtonIsInAppLockedName();
    } catch (e) {
      console.error(e);
    }
  }

  _setInAppLocked() {
    if (this.isInAppLocked) {
      Acc.setInAppLocked(false);
      this.isInAppLocked = false;
    } else {
      Acc.setInAppLocked(true);
      this.isInAppLocked = true;
    }
    this._updateButtonIsInAppLockedName();
  }

  _updateButtonIsInAppLockedName() {
    if (this.isInAppLocked) {
      this.setState({buttonIsInAppLockedName : 'Unlock InApps'});
    } else {
      this.setState({buttonIsInAppLockedName : 'Lock InApps'});
    }
  }

  render() {
    const { navigate } = this.props.navigation;
    return (
      <View style={styles.container}>
        <Button
          onPress={() => navigate('InAppEvents')}
          containerStyle={styles.accbuttoncontainer}
          style={styles.accbutton}>
          InApp Events
        </Button>
        <Button
          onPress={setInAppDisplayedCallback}
          containerStyle={styles.accbuttoncontainer}
          style={styles.accbutton}>
          Set Displayed Callback
        </Button>
        <Button
          onPress={setInAppClickedCallback}
          containerStyle={styles.accbuttoncontainer}
          style={styles.accbutton}>
          Set Clicked Callback
        </Button>
        <Button
          onPress={setInAppClosedCallback}
          containerStyle={styles.accbuttoncontainer}
          style={styles.accbutton}>
          Set Closed Callback
        </Button>
        <Button
          onPress={this._setInAppLocked}
          containerStyle={styles.accbuttoncontainer}
          style={styles.accbutton}>
          {this.state.buttonIsInAppLockedName}
        </Button>
      </View>
    );
  }
}
