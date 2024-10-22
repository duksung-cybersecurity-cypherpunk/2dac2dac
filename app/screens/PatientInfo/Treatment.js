import React from "react";
import { View, Text, StyleSheet } from "react-native";
import { createMaterialTopTabNavigator } from "@react-navigation/material-top-tabs";

// page
import NonfaceComponent from "../PatientInfo/Treatment/TreatmentNonface";
import FaceComponent from "../PatientInfo/Treatment/TreatmentFace";

const Tab = createMaterialTopTabNavigator();

export default function Treatment({ route }) {
  const { userId } = route.params;
  console.log("userId22", route.params);
  return (
    <View style={styles.blocks}>
      <Tab.Navigator
        initialRouteName="TreatmentDashboard"
        screenOptions={{
          tabBarActiveTintColor: "#050953",
          tabBarInactiveTintColor: "gray",
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
            top: 0,
            left: 0,
            right: 0,
          },
          tabBarIndicatorStyle: {
            backgroundColor: "#76B947",
            height: 4,
            width: "13%",
            alignSelf: "center",
            borderRadius: 10,
          },
        }}
      >
        <Tab.Screen
          name="NonfaceComponent"
          component={NonfaceComponent}
          options={{ tabBarLabel: "비대면" }}
          initialParams={{ userId: userId }} // userId 전달
        />

        <Tab.Screen
          name="FaceComponent"
          component={FaceComponent}
          options={{ tabBarLabel: "대면" }}
          initialParams={{ userId: userId }} // userId 전달
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
    fontSize: 18,
    fontFamily: "Arial",
  },
});
