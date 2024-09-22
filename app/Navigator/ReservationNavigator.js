import React from "react";
import { createBottomTabNavigator } from "@react-navigation/bottom-tabs";

const ReservationStack = createBottomTabNavigator();

export default function BottomTabNavigator() {
  return (
    <ReservationStack.Navigator independent={true}>
      {/*  <ReservationStack.Screen name="홈" component={Home} />
      <ReservationStack.Screen name="내역" component={MedicalHistory} />
      <ReservationStack.Screen name="예약" component={ReservationStack} />
  <ReservationStack.Screen name="설정" component={MyPage} />*/}
    </ReservationStack.Navigator>
  );
}
