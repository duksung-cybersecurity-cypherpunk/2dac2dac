import React from "react";
import { createNativeStackNavigator } from "@react-navigation/native-stack";

// stack
import BottomTabNavigator from "./TabNavigator"; // Bottom Tab Navigator
import OnboardingStack from "./OnboardingNavigation";
import ReservationStack from "./ReservationNavigator";
import HistoryStack from "./HistoryNavigator";

{/* Patient Infomation Navigator list. . . */}
import PatientInfoStack from "./PatientInfoNavigator";
import TreatmentInfoStack from "./TreatmentNavigator";
import VaccinationInfoStack from "./VaccinationNavigator";
import PrescriptionInfoStack from "./PrescriptionNavigator";
import ExaminationInfoStack from "./ExaminationNavigator";

const RootStack = createNativeStackNavigator();

export default function RootNavigator() {
  return (
    <RootStack.Navigator
      screenOptions={{ headerShown: false }}
      initialRouteName="Onboarding" // Set the initial route here
    >
      <RootStack.Screen
        screenOptions={{ headerShown: false }}
        name="Onboarding" // Name must match the initialRouteName
        component={OnboardingStack}
      />

      <RootStack.Screen
        name="BottomTabNavigator"
        component={BottomTabNavigator}
      />
      <RootStack.Screen
        name="ReservationStack"
        component={ReservationStack}
        options={{
          tabBarVisible: true, // Tab 바 표시
        }}
      />
      <RootStack.Screen
        name="HistoryStack"
        component={HistoryStack}
        options={{
          tabBarVisible: true, // Tab 바 표시
        }}
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
        name="PrescriptionInfoStack"
        component={PrescriptionInfoStack}
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
