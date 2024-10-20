import React from "react";
import { View, Text, StyleSheet } from "react-native";
import { createNativeStackNavigator } from '@react-navigation/native-stack';

// page
import PrescriptionDetails from '../screens/MedicalHistory/PrescriptionDetails';
import MedicalHistory from '../screens/MedicalHistory';

const HistoryStack = createNativeStackNavigator();

export default function HistoryNavigator( {route} ) {
    const { id, data } = route.params;

    return (
        <HistoryStack.Navigator>
            {
                id === 1 ? (
                  <HistoryStack.Screen 
                      name="PrescriptionDetails"
                      component={PrescriptionDetails}
                      options={{
                          headerTitle: '처방전',
                      }}
                      initialParams={{ data }}
                  />
              ) : <HistoryStack.Screen 
                        name="처방" 
                        component={MedicalHistory} 
                        options={{
                            headerShown: false,
                        }}
                    />
            }
        </HistoryStack.Navigator>
    );
  }
  