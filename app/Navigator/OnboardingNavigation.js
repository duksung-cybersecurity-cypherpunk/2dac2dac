import React from "react";
import { View, Text, StyleSheet } from "react-native";
import { createNativeStackNavigator } from "@react-navigation/native-stack";

// page
import OnboardingScreen from "../screens/OnboardingScreen";
import Login from "../screens/Login/Login";
import signUpForm1 from "../screens/Login/signUpFrom1";
import signUpForm2 from "../screens/Login/signUpForm2";
import Email from "../screens/Login/Email";

const OnboardingStack = createNativeStackNavigator();

export default function SearchNavigation() {
  return (
    <OnboardingStack.Navigator>
      <OnboardingStack.Screen
        name="OnboardingScreen"
        component={OnboardingScreen}
        options={{ headerShown: false }}
      />
      <OnboardingStack.Screen name="Login" component={Login} />
      <OnboardingStack.Screen name="signUpForm1" component={signUpForm1} />
      <OnboardingStack.Screen name="signUpForm2" component={signUpForm2} />
      <OnboardingStack.Screen name="Email" component={Email} />
    </OnboardingStack.Navigator>
  );
}
