import React from 'react';
import { createNativeStackNavigator } from '@react-navigation/native-stack';

// page
import Examination from "../screens/PatientInfo/Examination";
import Details from "../screens/PatientInfo/Examination/ExaminationDetails";
import MeasurementDetails from "../screens/PatientInfo/Examination/MeasurementDetails";
import BloodDetails from "../screens/PatientInfo/Examination/BloodDetails";
import OtherDetails from "../screens/PatientInfo/Examination/OtherDetails";

const ExaminationInfoStack = createNativeStackNavigator();

export default function ExaminationNavigator( {route} ) {
    const { id, data } = route.params;

    return (
        <ExaminationInfoStack.Navigator>  
        {
            id === 1 ? (
                <ExaminationInfoStack.Screen 
                    name="Details"
                    component={Details}
                    options={{
                        headerTitle: '건강검진',
                    }} 
                    initialParams={{ data }}
                />
            ) : id === 2 ? (
                <ExaminationInfoStack.Screen 
                    name="MeasurementDetails" 
                    component={MeasurementDetails}
                    options={{
                        headerTitle: '건강검진',
                    }} 
                    initialParams={{ data }}
                />
            ) : id === 3 ? (
                <ExaminationInfoStack.Screen 
                    name="BloodDetails" 
                    component={BloodDetails}
                    options={{
                        headerTitle: '건강검진',
                    }} 
                    initialParams={{ data }}
                />
            ) : id === 4 ? (
                <ExaminationInfoStack.Screen 
                    name="OtherDetails" 
                    component={OtherDetails}
                    options={{
                        headerTitle: '건강검진',
                    }} 
                    initialParams={{ data }}
                />
            )   : <ExaminationInfoStack.Screen 
                    name="건강검진" 
                    component={Examination} 
                    options={{
                        headerShown: false,
                    }}
                />
        }
        </ExaminationInfoStack.Navigator>
    );
  }