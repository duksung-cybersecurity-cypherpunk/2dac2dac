import React from "react";
import { createNativeStackNavigator } from '@react-navigation/native-stack';

// page
import PrescriptionDetails from '../screens/MedicalHistory/PrescriptionDetails';
import PrescriptionWriting from '../screens/MedicalHistory/PrescriptionWriting';
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
                          headerTitle: '처방전 상세 내역',
                      }}
                      initialParams={{ data }}
                  />
              ) : id === 2 ? (
                <HistoryStack.Screen 
                    name="PrescriptionWriting"
                    component={PrescriptionWriting}
                    options={{
                        headerTitle: '처방전 작성',
                    }}
                    initialParams={{ data }}
                />
            ) : <HistoryStack.Screen 
                    name="MedicalHistory" 
                    component={MedicalHistory} 
                    options={{
                        headerShown: false,
                    }}
                />
            }
        </HistoryStack.Navigator>
    );
  }
  