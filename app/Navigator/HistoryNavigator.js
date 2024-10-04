import React from 'react';
import { createNativeStackNavigator } from '@react-navigation/native-stack';

// page
import PrescriptionWrite from '../screens/MedicalHistory/PrescriptionWrite';
import MedicalHistory from '../screens/MedicalHistory';

const HistoryStack = createNativeStackNavigator();

export default function PrescriptionNavigator( {route} ) {
    const { id, data } = route.params;

    return (
        <HistoryStack.Navigator> 
            {
                id === 1 ? (
                  <HistoryStack.Screen 
                      name="PrescriptionWrite"
                      component={PrescriptionWrite}
                      options={{
                          headerTitle: '처방전 작성',
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
  