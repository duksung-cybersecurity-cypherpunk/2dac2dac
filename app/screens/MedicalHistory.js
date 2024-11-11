import React, { useState, useEffect } from "react";
import { View, Text, StyleSheet, } from "react-native";
import { createMaterialTopTabNavigator } from "@react-navigation/material-top-tabs";
import AsyncStorage from "@react-native-async-storage/async-storage";

// page
import TobeLIst from "../screens/MedicalHistory/ToBeList";
import CompletedList from "../screens/MedicalHistory/CompletedList";

const Tab = createMaterialTopTabNavigator();

export default function MedicalHistory() {
  const [doctorInfo, setDoctorInfo] = useState(null);

  useEffect(() => {
    const fetchUserInfo = async () => {
      try {
        const userInfo = await AsyncStorage.getItem("userInfo");
        const userData = JSON.parse(userInfo);
        setDoctorInfo(userData.id);
      } catch (error) {
        console.error("Error fetching user info:", error);
      }
    };

    fetchUserInfo();
  }, []);
  
  return (
    <View style={styles.blocks}>

      <Tab.Navigator
        initialRouteName="MedicalHistoryDashboard"
        screenOptions={{
          tabBarActiveTintColor: "#050953",
          tabBarInactiveTintColor: "gray", // 선택되지 않은 탭의 텍스트 색상
          tabBarLabelStyle: {
            fontSize: 14,
            color: "#47743A",
            fontWeight: "bold",
            height: 50,
            width: "100%",
          },
          tabBarStyle: {
            backgroundColor: "white",
            height: 50,
            width: "100%",
            justifyContent: "center",
            position: "absolute",
          },
          tabBarIndicatorStyle: {
            backgroundColor: "#76B947",
            height: 4,
            width: "15%",
            marginLeft: 75,
            alignSelf: "center",
            justifyContent: "center",
            borderRadius: 10,
          },
        }}
      >
        <Tab.Screen
          name="TobeLIst"
          component={TobeLIst}
          options={{ tabBarLabel: "처방전 작성" }}
          initialParams={{ doctorInfo: doctorInfo }} // doctorInfo 전달
        />

        <Tab.Screen
          name="CompletedList"
          component={CompletedList}
          options={{ tabBarLabel: "진료 완료" }}
          initialParams={{ doctorInfo: doctorInfo }} // doctorInfo 전달
        />
      </Tab.Navigator>
    </View>
  );
}

const styles = StyleSheet.create({
  blocks: {
    height: "100%",
    width: "100%",
    borderColor: "gray",
    borderTopWidth: 0.5,
    borderBottomWidth: 0.5,
  },
  text: {
    fontSize: 18, // Increase font size
    fontFamily: "Arial", // Change font family
  },
});