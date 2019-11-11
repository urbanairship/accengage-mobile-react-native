import React, { Component } from "react";
import {
  View,
  Text
} from 'react-native';
import Button from 'react-native-button';
import { Acc } from 'react-native-acc';
import styles from './../../Styles';

export default class ControlScreen extends Component {
  static navigationOptions = ({navigation}) => ({
    title: "Control",
  });

  constructor() {
    super();
    this.state = {
      buttonAllServicesEnabledName : '',
      textAllServicesEnabledName : '',
      buttonNetworkCallsEnabledName : '',
      textNetworkCallsEnabledName : '',
      buttonGeofenceServiceEnabledName : '',
      textGeofenceServiceEnabledName : '',
      buttonBeaconServiceEnabledName : '',
      textBeaconServiceEnabledName : '',
      textOptinDataEnabledName : '',
      buttonOptinDataEnabledName : '',
      textOptinGeolocEnabledName : '',
      buttonOptinGeolocEnabledName : '',
    };
    Acc.control.areAllServicesEnabled().then(enabled => {
      this.allServicesEnabled = enabled;
      this._updateAllServicesEnabledNames();
    });
    Acc.control.areNetworkCallsEnabled().then(enabled => {
      this.networkCallsEnabled = enabled;
      this._updateNetworkCallsEnabledNames();
    });
    Acc.control.isGeofenceServiceEnabled().then(enabled => {
      this.geofenceServiceEnabled = enabled;
      this._updateGeofenceServiceEnabledNames();
    });
    Acc.control.isBeaconServiceEnabled().then(enabled => {
      this.beaconServiceEnabled = enabled;
      this._updateBeaconServiceEnabledNames();
    });
    Acc.control.isOptinDataEnabled().then(enabled => {
      this.optinDataEnabled = enabled;
      this._updateOptinDataEnabledNames();
    });
    Acc.control.isOptinGeolocEnabled().then(enabled => {
      this.optinGeolocEnabled = enabled;
      this._updateOptinGeolocEnabledNames();
    });
    this._setAllServicesEnabled = this._setAllServicesEnabled.bind(this);
    this._setNetworkCallsEnabled = this._setNetworkCallsEnabled.bind(this);
    this._setGeofenceServiceEnabled = this._setGeofenceServiceEnabled.bind(this);
    this._setBeaconServiceEnabled = this._setBeaconServiceEnabled.bind(this);
    this._setOptinDataEnabled = this._setOptinDataEnabled.bind(this);
    this._setOptinGeolocEnabled = this._setOptinGeolocEnabled.bind(this);
  }

  _updateAllServicesEnabledNames() {
    if (this.allServicesEnabled) {
      this.setState({textAllServicesEnabledName : 'All services are enabled'});
      this.setState({buttonAllServicesEnabledName : 'Disable'});
    } else {
      this.setState({textAllServicesEnabledName : 'All services are disabled'});
      this.setState({buttonAllServicesEnabledName : 'Enable'});
    }
  }

  _updateNetworkCallsEnabledNames() {
    if (this.networkCallsEnabled) {
      this.setState({textNetworkCallsEnabledName : 'Network calls are enabled'});
      this.setState({buttonNetworkCallsEnabledName : 'Disable'});
    } else {
      this.setState({textNetworkCallsEnabledName : 'Network calls are disabled'});
      this.setState({buttonNetworkCallsEnabledName : 'Enable'});
    }
  }

  _updateGeofenceServiceEnabledNames() {
    if (this.geofenceServiceEnabled) {
      this.setState({textGeofenceServiceEnabledName : 'Geofence service is enabled'});
      this.setState({buttonGeofenceServiceEnabledName : 'Disable'});
    } else {
      this.setState({textGeofenceServiceEnabledName : 'Geofence service is disabled'});
      this.setState({buttonGeofenceServiceEnabledName : 'Enable'});
    }
  }

    _updateBeaconServiceEnabledNames() {
    if (this.beaconServiceEnabled) {
      this.setState({textBeaconServiceEnabledName : 'Beacon service is enabled'});
      this.setState({buttonBeaconServiceEnabledName : 'Disable'});
    } else {
      this.setState({textBeaconServiceEnabledName : 'Beacon service is disabled'});
      this.setState({buttonBeaconServiceEnabledName : 'Enable'});
    }
  }

    _updateOptinDataEnabledNames() {
    if (this.optinDataEnabled) {
      this.setState({textOptinDataEnabledName : 'Optin data is enabled'});
      this.setState({buttonOptinDataEnabledName : 'Disable'});
    } else {
      this.setState({textOptinDataEnabledName : 'Optin data is disabled'});
      this.setState({buttonOptinDataEnabledName : 'Enable'});
    }
  }

    _updateOptinGeolocEnabledNames() {
    if (this.optinGeolocEnabled) {
      this.setState({textOptinGeolocEnabledName : 'Optin geoloc is enabled'});
      this.setState({buttonOptinGeolocEnabledName : 'Disable'});
    } else {
      this.setState({textOptinGeolocEnabledName : 'Optin geoloc is disabled'});
      this.setState({buttonOptinGeolocEnabledName : 'Enable'});
    }
  }

  _setAllServicesEnabled() {
    this.allServicesEnabled = !this.allServicesEnabled;
    Acc.control.setAllServicesEnabled(this.allServicesEnabled);
    this._updateAllServicesEnabledNames();
  }

  _setNetworkCallsEnabled() {
    this.networkCallsEnabled = !this.networkCallsEnabled;
    Acc.control.setNetworkCallsEnabled(this.networkCallsEnabled);
    this._updateNetworkCallsEnabledNames();
  }

  _setGeofenceServiceEnabled() {
    this.geofenceServiceEnabled = !this.geofenceServiceEnabled;
    Acc.control.setGeofenceServiceEnabled(this.geofenceServiceEnabled);
    this._updateGeofenceServiceEnabledNames();
  }

  _setBeaconServiceEnabled() {
    this.beaconServiceEnabled = !this.beaconServiceEnabled;
    Acc.control.setBeaconServiceEnabled(this.beaconServiceEnabled);
    this._updateBeaconServiceEnabledNames();
  }

  _setOptinDataEnabled() {
    this.optinDataEnabled = !this.optinDataEnabled;
    Acc.control.setOptinDataEnabled(this.optinDataEnabled);
    this._updateOptinDataEnabledNames();
  }

  _setOptinGeolocEnabled() {
    this.optinGeolocEnabled = !this.optinGeolocEnabled;
    Acc.control.setOptinGeolocEnabled(this.optinGeolocEnabled);
    this._updateOptinGeolocEnabledNames();

  }

  render() {
    return (
      <View style={styles.container}>
        <Button
          onPress={this._setAllServicesEnabled}
          containerStyle={styles.accbuttoncontainer}
          style={styles.accbutton}>
          {this.state.textAllServicesEnabledName}
        </Button>
        <Button
          onPress={this._setNetworkCallsEnabled}
          containerStyle={styles.accbuttoncontainer}
          style={styles.accbutton}>
          {this.state.textNetworkCallsEnabledName}
        </Button>
        <Button
          onPress={this._setGeofenceServiceEnabled}
          containerStyle={styles.accbuttoncontainer}
          style={styles.accbutton}>
          {this.state.textGeofenceServiceEnabledName}
        </Button>
        <Button
          onPress={this._setBeaconServiceEnabled}
          containerStyle={styles.accbuttoncontainer}
          style={styles.accbutton}>
          {this.state.textBeaconServiceEnabledName}
        </Button>
        <Button
          onPress={this._setOptinDataEnabled}
          containerStyle={styles.accbuttoncontainer}
          style={styles.accbutton}>
          {this.state.textOptinDataEnabledName}
        </Button>
        <Button
          onPress={this._setOptinGeolocEnabled}
          containerStyle={styles.accbuttoncontainer}
          style={styles.accbutton}>
          {this.state.textOptinGeolocEnabledName}
        </Button>
      </View>
    );
  }
}
