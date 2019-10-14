'use strict';

import React, {Component} from 'react';
import {
	Switch,
    StyleSheet,
    Text,
    TextInput,
    ScrollView,
    View
} from 'react-native';
import Button from 'react-native-button';
import Acc from 'react-native-acc';
import styles from './../../../Styles';
import {States} from 'react-native-acc';

export default class StatesScreen extends Component {
  static navigationOptions = ({navigation}) => ({
    title: "States",
  });

  constructor(props) {
        super(props);
        this.state = {
        	name : null,
        	value : null
        };
  }
  render() {
    const { navigate } = this.props.navigation;
        return (  
        <View style={styles.container}>
          	
          	<ScrollView  contentContainerStyle={styles.scrollcontainer}
  						 scrollEnabled={true}>
          
          	<View style={styles.flowRight}>
                    <Text style={styles.text}>
                        Name
                    </Text>
                    <TextInput
                        style={styles.input}
                        value={this.state.name}
                        onChange={this._onNameChanged}
                        ref={input => { this.textNameInput = input }}
                        clearButtonMode="always"
                        placeholder='Name'/>
            </View>
            
            <View style={styles.flowRight}>
                	<Text style={styles.text}>
                    	Value
                	</Text>
                	<TextInput
                    	style={styles.input}
                    	value={this.state.value}
                    	onChange={this._onValueChanged}
                    	ref={input => { this.textValueInput = input }}
                    	clearButtonMode="always"
                    	placeholder='Leave empty to delete state'/>
            </View>
             
        
           	<Button
            		style={styles.blueButton}
                	styleDisabled={styles.disabledButton}
                	disabled={((this.state.name === null) || (this.state.name === "")) ? true : false}
                	onPress={this._sendSetStatesAction}>
                			Set               
           	</Button>
       	</ScrollView>
    </View>
                 
        );
    }

	 _onNameChanged = (event) => {
        this.setState({name: event.nativeEvent.text});
    };

    _onValueChanged = (event) => {
        this.setState({value: event.nativeEvent.text});
    };
    
    _sendSetStatesAction = () => {
    	Acc.analytics.states.setState(this.state.name,  this.state.value);
    	this._initializeAll();
    }
    
    _initializeAll = () => {
    	this.textNameInput.clear();
    	this.textValueInput.clear();
    }
    
}
