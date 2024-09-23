import React from 'react';
import { createNativeStackNavigator } from '@react-navigation/native-stack';

// page
import Prescription from '../screens/PatientInfo/Prescription';
import PrescriptionDetails from '../screens/PatientInfo/Prescription/PrescriptionFaceDetails';

const PrescriptionInfoStack = createNativeStackNavigator();

export default function PrescriptionNavigator( {route} ) {
    const { id, data } = route.params;

    return (
        <PrescriptionInfoStack.Navigator> 
            {
                id === 1 ? (
                  <PrescriptionInfoStack.Screen 
                      name="PrescriptionDetails"
                      component={PrescriptionDetails}
                      options={{
                          headerTitle: '처방 내역',
                      }}
                      initialParams={{ data }}
                  />
              ) : <PrescriptionInfoStack.Screen 
                        name="처방" 
                        component={Prescription} 
                        options={{
                            headerShown: false,
                        }}
                    />
            }
        </PrescriptionInfoStack.Navigator>
    );
  }
  