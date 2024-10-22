import React from "react";
import { createBottomTabNavigator } from "@react-navigation/bottom-tabs";
import { Accept } from "../screens/Reservation/Accept";

const AcceptPatientStack = createBottomTabNavigator();

export default function AcceptPatientNavigator() {
  return (
    <AcceptPatientStack.Navigator>
      <AcceptPatientStack.Screen
        name="PatientInformation"
        component={PatientInformation}
        options={{
          headerTitle: "환자 예약 내역",
        }}
      />
    </AcceptPatientStack.Navigator>
  );
}
