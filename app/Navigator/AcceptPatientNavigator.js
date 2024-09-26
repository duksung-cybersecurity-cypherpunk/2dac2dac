import React from "react";
import { createBottomTabNavigator } from "@react-navigation/bottom-tabs";
import { PatientInformation } from ".././screens/Reservation/PatientInfo";
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

      <AcceptPatientStack.Screen
        name="Accept"
        component={Accept}
        options={{
          headerTitle: "예약 수락",
        }}
      />
    </AcceptPatientStack.Navigator>
  );
}
