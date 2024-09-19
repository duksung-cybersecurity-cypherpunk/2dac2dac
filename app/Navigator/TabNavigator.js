import React from "react";
import { createBottomTabNavigator } from "@react-navigation/bottom-tabs";
import Home from "../screens/Home";
import MedicalHistory from "../screens/MedicalHistory";
import MyPage from "../screens/MyPage";

const Tab = createBottomTabNavigator();

export default function BottomTabNavigator() {
  return (
    <Tab.Navigator independent={true}>
      <Tab.Screen name="홈" component={Home} />
      <Tab.Screen name="내역" component={MedicalHistory} />
      <Tab.Screen name="설정" component={MyPage} />
    </Tab.Navigator>
  );
}
