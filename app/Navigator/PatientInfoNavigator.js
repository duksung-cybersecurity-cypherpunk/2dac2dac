import React from "react";
import { createNativeStackNavigator } from "@react-navigation/native-stack";

{
  /* Screen list. . . */
}
import MyPage from "../screens/MyPage";
import QRLoad from "../screens/PatientInfo/QRLoad";
import Treatment from "../screens/PatientInfo/Treatment";
import Prescription from "../screens/PatientInfo/Prescription";
import Examination from "../screens/PatientInfo/Examination";
import Vaccination from "../screens/PatientInfo/Vaccination";

const PatientInfoStack = createNativeStackNavigator();

export default function PatientInfoNavigator({ route }) {
  const { id, data } = route.params;

  return (
    <PatientInfoStack.Navigator>
      {id === 1 ? (
        <PatientInfoStack.Screen
          name="QRLoad"
          component={QRLoad}
          options={{
            headerTitle: "환자 정보",
          }}
        />
      ) : id === 2 ? (
        <PatientInfoStack.Screen
          name="진료 내역"
          component={Treatment}
          options={{
            headerTitle: "환자 정보",
          }}
        />
      ) : id === 3 ? (
        <PatientInfoStack.Screen
          name="투약 내역"
          component={Prescription}
          options={{
            headerTitle: "환자 정보",
          }}
        />
      ) : id === 4 ? (
        <PatientInfoStack.Screen
          name="건강 검진 내역"
          component={Examination}
          options={{
            headerTitle: "환자 정보",
          }}
        />
      ) : id === 5 ? (
        <PatientInfoStack.Screen
          name="예방 접종 내역"
          component={Vaccination}
          options={{
            headerTitle: "환자 정보",
          }}
        />
      ) : (
        <PatientInfoStack.Screen name="설정" component={MyPage} />
      )}
    </PatientInfoStack.Navigator>
  );
}
