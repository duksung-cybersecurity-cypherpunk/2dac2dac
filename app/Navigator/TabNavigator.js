import React from "react";
import { Image } from "react-native";
import { createBottomTabNavigator } from "@react-navigation/bottom-tabs";

{
  /* Screen list. . . */
}
import Home from "../screens/Home";
import Reservation from "../screens/Reservation";
import MedicalHistory from "../screens/MedicalHistory";
import MyPage from "../screens/MyPage";
const Tab = createBottomTabNavigator();

export default function BottomTabNavigator() {
  return (
    <Tab.Navigator
      screenOptions={({ route }) => ({
        tabBarIcon: ({ focused, color, size }) => {
          if (route.name === "홈") {
            return (
              <Image
                source={
                  focused
                    ? require("../../assets/images/tabNavigation/g_home.png")
                    : require("../../assets/images/tabNavigation/w_home.png")
                }
                style={{ width: size, height: size }}
              />
            );
          } else if (route.name === "검색") {
            return (
              <Image
                source={
                  focused
                    ? require("../../assets/images/tabNavigation/g_search.png")
                    : require("../../assets/images/tabNavigation/w_search.png")
                }
                style={{ width: size, height: size }}
              />
            );
          } else if (route.name === "예약") {
            return (
              <Image
                source={
                  focused
                    ? require("../../assets/images/tabNavigation/g_nonface.png")
                    : require("../../assets/images/tabNavigation/w_nonface.png")
                }
                style={{ width: size, height: size }}
              />
            );
          } else if (route.name === "내역") {
            return (
              <Image
                source={
                  focused
                    ? require("../../assets/images/tabNavigation/g_medicalhistory.png")
                    : require("../../assets/images/tabNavigation/w_medicalhistory.png")
                }
                style={{ width: size, height: size }}
              />
            );
          } else if (route.name === "설정") {
            return (
              <Image
                source={
                  focused
                    ? require("../../assets/images/tabNavigation/g_mypage.png")
                    : require("../../assets/images/tabNavigation/w_mypage.png")
                }
                style={{ width: size, height: size }}
              />
            );
          }
        },
        tabBarActiveTintColor: "black",
        tabBarInactiveTintColor: "#737373",
        tabBarActiveBackgroundColor: "rgba(118, 185, 71, 0.25)",
        tabBarStyle: {
          borderTopWidth: 0,
          elevation: 0,
          shadowOpacity: 0,
          backgroundColor: "transparent",
        },
        tabBarItemStyle: {
          width: 20,
          height: 50,
          borderRadius: 25,
          alignItems: "center",
          justifyContent: "center",
          paddingVertical: 3,
        },
      })}
    >
      <Tab.Screen name="홈" component={Home} />
      <Tab.Screen name="예약" component={Reservation} />
      <Tab.Screen name="내역" component={MedicalHistory} />
      <Tab.Screen name="설정" component={MyPage} />
    </Tab.Navigator>
  );
}
