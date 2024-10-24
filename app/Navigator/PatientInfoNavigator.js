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
  const { id } = route.params;

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
          name="Treatment"
          component={Treatment}
          options={{
            headerTitle: "환자 정보",
          }}
        />
      ) : id === 3 ? (
        <PatientInfoStack.Screen
          name="Prescription"
          component={Prescription}
          options={{
            headerTitle: "환자 정보",
          }}
        />
      ) : id === 4 ? (
        <PatientInfoStack.Screen
          name="Examination"
          component={Examination}
          options={{
            headerTitle: "환자 정보",
          }}
        />
      ) : id === 5 ? (
        <PatientInfoStack.Screen
          name="Vaccination"
          component={Vaccination}
          options={{
            headerTitle: "환자 정보",
          }}
        />
      ) : (
        <PatientInfoStack.Screen name="MyPage" component={MyPage} />
      )}
    </PatientInfoStack.Navigator>
  );
}
