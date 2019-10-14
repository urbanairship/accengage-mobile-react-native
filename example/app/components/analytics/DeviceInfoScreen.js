import React, {Component} from 'react';
import {
    Picker,
    StyleSheet,
    Text,
    TextInput,
    ScrollView,
    View
} from 'react-native';
import Button from 'react-native-button';
import Acc from 'react-native-acc';
import styles from './../../../Styles';
import {DeviceInfo} from 'react-native-acc';

export default class DeviceInfoScreen extends Component {
  static navigationOptions = ({navigation}) => ({
    title: "Device Information",
  });

  constructor(props) {
        super(props);
        this.state = {
            method: "set",
            key: null,
            value: null,
            keyboardType : 'default'
        };
  }
  render() {
    const { navigate } = this.props.navigation;
        return (  
          <View style={styles.container}>
          
          	<ScrollView contentContainerStyle={styles.scrollcontainer}
  						scrollEnabled={true}>
  			
            <View style={styles.flowRight}>
                <Text style={styles.text}>
                        Method
                </Text>
            	<Picker style={styles.picker} itemStyle={styles.pickerItem}
            		selectedValue={this.state.method}
 				    onValueChange={(itemValue) => this.setState({method: itemValue})}>
 		   		<Picker.Item label="Set" value="set" />
  		   		<Picker.Item label="Delete" value="delete" />
  		   		<Picker.Item label="Increment" value="increment" />
 		   		<Picker.Item label="Decrement" value="decrement" />
		   		</Picker>
		   </View>
		   
            <View style={styles.flowRight}>
                <Text style={styles.text}>
                    Key
                </Text>
                <TextInput
                    style={styles.input}
                    value={this.state.key}
                    onChange={this._onKeyTextChanged}
                    ref={input => { this.textKeyInput = input }}
                    clearButtonMode="always"
                    placeholder='Field Name'/>
            </View>
            <View style={styles.flowRight}>
                <Text style={styles.text}>
                    Value
                </Text>
                <TextInput
                    style={styles.input}
                    value={this.state.value}
                    keyboardType={((this.state.method === 'increment') || (this.state.method === 'decrement')) ? 'numeric' : 'default'}
                    onChange={this._onValueTextChanged}
                    ref={input => { this.textValueInput = input }}
                    clearButtonMode="always"
                    placeholder='Field Value'/>
            </View>
            <Button
                style={styles.blueButton}
                styleDisabled={styles.disabledButton}
                disabled={((this.state.key === null) || (this.state.value === null) || (this.state.key === "") || (this.state.value === "")) ? true : false}
                onPress={this._sendAction}>
                Send
            </Button>
            </ScrollView> 
          </View>
        );
    }

    _onKeyTextChanged = (event) => {
        this.setState({key: event.nativeEvent.text});
    };

    _onValueTextChanged = (event) => {
        this.setState({value: event.nativeEvent.text});
    };

    _sendAction = () => {
      console.log("Method : " + this.state.method + ", action sent");
      Acc.analytics.deviceInfo.updateDeviceInformation(this.state.method, this.state.key, this.state.value);
      this._initializeAll();
    }
    
    _initializeAll = () => {
    	this.textKeyInput.clear();
    	this.textValueInput.clear();
    }
}
