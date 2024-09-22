import React from "react";
import { createBottomTabNavigator } from "@react-navigation/bottom-tabs";
import { PatientInfo } from ".././screens/Reservation/PatientInfo";

const ReservationStack = createBottomTabNavigator();

export default function BottomTabNavigator() {
  return (
    <ReservationStack.Navigator independent={true}>
      <ReservationStack.Screen name="PatientInfo" component={PatientInfo} />
    </ReservationStack.Navigator>
  );
}
