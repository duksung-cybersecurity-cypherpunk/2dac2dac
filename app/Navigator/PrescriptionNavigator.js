import React from 'react';
import { createNativeStackNavigator } from '@react-navigation/native-stack';

// page
import Prescription from '../screens/PatientInfo/Prescription';
import PrescriptionFaceDetails from '../screens/PatientInfo/Prescription/PrescriptionFaceDetails';

const PrescriptionInfoStack = createNativeStackNavigator();

export default function PrescriptionNavigator( {route} ) {
    const { id, data } = route.params;

    return (
        <PrescriptionInfoStack.Navigator> 
            {
                id === 1 ? (
                  <PrescriptionInfoStack.Screen 
                      name="PrescriptionFaceDetails"
                      component={PrescriptionFaceDetails}
                      options={{
                          headerTitle: '처방 내역',
                      }}
                      initialParams={{ data }}
                  />
              ) : <PrescriptionInfoStack.Screen 
                        name="Prescription" 
                        component={Prescription} 
                        options={{
                            headerShown: false,
                        }}
                    />
            }
        </PrescriptionInfoStack.Navigator>
    );
  }
  