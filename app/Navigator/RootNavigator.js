import React from "react";
import { createNativeStackNavigator } from "@react-navigation/native-stack";

// stack
import BottomTabNavigator from "./TabNavigator"; // Bottom Tab Navigator
import OnboardingStack from "./OnboardingNavigation";
import PatientInfoStack from "./PatientInfoNavigator";
import TreatmentInfoStack from "./TreatmentNavigator";
import VaccinationInfoStack from "./VaccinationNavigator";
import PrescriptionInfoStack from "./PrescriptionNavigator";
import ExaminationInfoStack from "./ExaminationNavigator";
import HistoryStack from "./HistoryNavigator";

// page
import ReservationDetails from "../screens/Reservation/ReservationDetails";

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
        name="ReservationDetails"
        component={ReservationDetails}
        options={{
          headerTitle: "진료 내역",
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
