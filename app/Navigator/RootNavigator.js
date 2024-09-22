import React from "react";
import { createNativeStackNavigator } from "@react-navigation/native-stack";

// stack
import BottomTabNavigator from "./TabNavigator"; // Bottom Tab Navigator
import OnboardingStack from "./OnboardingNavigation";
import ReservationStack from "./ReservationNavigator";
const RootStack = createNativeStackNavigator();

export default function RootNavigator() {
  return (
    <RootStack.Navigator
      screenOptions={{ headerShown: false }}

      //initialRouteName="Onboarding" // Set the initial route here
    >
      {/*<RootStack.Screen
        screenOptions={{ headerShown: false }}
        name="Onboarding" // Name must match the initialRouteName
        component={OnboardingStack}
  />*/}

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
    </RootStack.Navigator>
  );
}
