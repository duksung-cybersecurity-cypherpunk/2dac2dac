import React from 'react';
import { createNativeStackNavigator } from '@react-navigation/native-stack';

// page
import Prescription from '../screens/PatientInfo/Prescription';
import PrescriptionNonface from "../screens/PatientInfo/Prescription/PrescriptionNonface";
import PrescriptionFace from "../screens/PatientInfo/Prescription/PrescriptionFace";
import PrescriptionFaceDetails from '../screens/PatientInfo/Prescription/PrescriptionFaceDetails';
import PrescriptionNonfaceDetails from '../screens/PatientInfo/Prescription/PrescriptionNonfaceDetails';

const PrescriptionInfoStack = createNativeStackNavigator();

export default function PrescriptionNavigator( {route} ) {
    const { id, data } = route.params;

    return (
        <PrescriptionInfoStack.Navigator> 
            {
                id === 1 ? (
                  <PrescriptionInfoStack.Screen 
                      name="PrescriptionNonface"
                      component={PrescriptionNonface}
                      options={{
                          headerTitle: '처방 내역',
                      }}
                      initialParams={{ data }}
                  />
              ) : id === 2 ? (
                <PrescriptionInfoStack.Screen 
                    name="PrescriptionFace"
                    component={PrescriptionFace}
                    options={{
                        headerTitle: '처방 내역',
                    }}
                    initialParams={{ data }}
                />
            ) : id === 3 ? (
                <PrescriptionInfoStack.Screen 
                    name="PrescriptionNonfaceDetails"
                    component={PrescriptionNonfaceDetails}
                    options={{
                        headerTitle: '처방 내역',
                    }}
                    initialParams={{ data }}
                />
            ) : id === 4 ? (
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
  