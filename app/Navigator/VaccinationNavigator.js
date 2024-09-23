import React from "react";
import { createNativeStackNavigator } from "@react-navigation/native-stack";

{/* Screen list. . . */}
import Vaccination from "../screens/PatientInfo/Vaccination";
import VaccinationDetails from "../screens/PatientInfo/Vaccination/VaccinationDetails";

const VaccinationInfoStack = createNativeStackNavigator();

export default function VaccinationNavigator({ route }) {
    const { id, data } = route.params;

    return (
        <VaccinationInfoStack.Navigator>
        {
            id === 1 ? (
                <VaccinationInfoStack.Screen 
                    name="VaccinationDetails"
                    component={VaccinationDetails}
                    options={{
                        headerTitle: '예방 접종',
                    }} 
                    initialParams={{ data }}
                />
            ) : <VaccinationInfoStack.Screen 
                    name="예방 접종" 
                    component={Vaccination} 
                    options={{
                        headerShown: false,
                    }}
                />
        }     
        </VaccinationInfoStack.Navigator>
    );
}
