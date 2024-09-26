import React from "react";
import { createNativeStackNavigator } from "@react-navigation/native-stack";

{/* Tab Screen list. . . */}
import HomeScreen from "../screens/Home";
import ReservationScreen from "../screens/Reservation";
import MedicalHistoryScreen from "../screens/MedicalHistory";
import MyPageScreen from "../screens/MyPage";

{/* Other Screen list. . . */}
import QRLoad from "../screens/PatientInfo/QRLoad";

const SharedStack = createNativeStackNavigator();

export default function SharedStackNavigator({ screenName }) {
  return (
    <SharedStack.Navigator screenOptions={{ headerShown: false }}>
      {screenName === '홈' && (
        <SharedStack.Screen
          name="홈"
          component={HomeScreen}
        />
      )}
      {screenName === '예약' && (
        <SharedStack.Screen
          name="예약"
          component={ReservationScreen}
        />
      )}
      {screenName === '내역' && (
        <SharedStack.Screen
          name="내역"
          component={MedicalHistoryScreen}
        />
      )}
      {screenName === '설정' && (
        <SharedStack.Screen
          name="설정"
          component={MyPageScreen}
        />
      )}

    {/* Other Screen list. . . */}
    <SharedStack.Screen name={'QRLoad'} component={QRLoad} />

    </SharedStack.Navigator>
  );
}
