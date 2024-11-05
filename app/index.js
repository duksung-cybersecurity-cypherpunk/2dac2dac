import React from "react";
import { NavigationContainer } from "@react-navigation/native";
import RootNavigator from "./Navigator/RootNavigator";

import { LogBox } from "react-native";

LogBox.ignoreLogs([
  "ViewPropTypes will be removed from React Native",
  "API 호출 실패",
  "Error fetching data",
  "Got a",
  "TypeError: Cannot reaa property",
  "AirGoogleMaps",
  "displayName",
  "API",
]);

export default function App() {
  return <RootNavigator />;
}
