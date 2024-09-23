import React from "react";
import { createNativeStackNavigator } from "@react-navigation/native-stack";

{/* Navigator list. . . */}
import BottomTabNavigator from "./TabNavigator"; // Bottom Tab Navigator
import PatientInfoStack from "./PatientInfoNavigator";
import TreatmentInfoStack from "./TreatmentNavigator";
import VaccinationInfoStack from "./VaccinationNavigator";
import PresciptionInfoStack from "./PrescriptionNavigator";
import ExaminationInfoStack from "./ExaminationNavigator";

const RootStack = createNativeStackNavigator();

export default function RootNavigator() {
  return (
    <RootStack.Navigator screenOptions={{ headerShown: false }}>
      <RootStack.Screen
        name="BottomTabNavigator"
        component={BottomTabNavigator}
      />
      <RootStack.Screen
        name="PatientInfoStack"
        component={PatientInfoStack}
        options={{
          tabBarVisible: true, // Tab 바 표시
        }}
      />
      <RootStack.Screen
        name="TreatmentInfoStack"
        component={TreatmentInfoStack}
        options={{
          tabBarVisible: true, // Tab 바 표시
        }}
      />
      <RootStack.Screen
        name="VaccinationInfoStack"
        component={VaccinationInfoStack}
        options={{
          tabBarVisible: true, // Tab 바 표시
        }}
      />
      <RootStack.Screen
        name="PresciptionInfoStack"
        component={PresciptionInfoStack}
        options={{
          tabBarVisible: true, // Tab 바 표시
        }}
      />
      <RootStack.Screen
        name="ExaminationInfoStack"
        component={ExaminationInfoStack}
        options={{
          tabBarVisible: true, // Tab 바 표시
        }}
      />                  
    </RootStack.Navigator>
  );
}
