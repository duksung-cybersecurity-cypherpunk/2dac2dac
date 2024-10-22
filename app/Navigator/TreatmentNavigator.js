import React from "react";
import { createNativeStackNavigator } from "@react-navigation/native-stack";

{
  /* Screen list. . . */
}
import Treatment from "../screens/PatientInfo/Treatment";
import NonfaceDetails from "../screens/PatientInfo/Treatment/NonfaceDetails"; // 비대면 진료 상세페이지 (진료 완료)
import FaceDetails from "../screens/PatientInfo/Treatment/FaceDetails"; // 대면 진료 상세페이지
import TreatmentNonface from "../screens/PatientInfo/Treatment/TreatmentNonface";
import TreatmentFace from "../screens/PatientInfo/Treatment/TreatmentFace";

const TreatmentInfoStack = createNativeStackNavigator();

export default function TreatmentNavigator({ route }) {
  const { id, data } = route.params;

  return (
    <TreatmentInfoStack.Navigator>
      {id === 1 ? (
        <TreatmentInfoStack.Screen
          name="NonfaceDetails"
          component={NonfaceDetails}
          options={{
            headerTitle: "비대면 진료",
          }}
          initialParams={{ data }}
        />
      ) : id === 2 ? (
        <TreatmentInfoStack.Screen
          name="FaceDetails"
          component={FaceDetails}
          options={{
            headerTitle: "대면 진료",
          }}
          initialParams={{ data }}
        />
      ) : id === 3 ? (
        <TreatmentInfoStack.Screen
          name="TreatmentNonface"
          component={TreatmentNonface}
          options={{
            headerTitle: "비대면 진료",
          }}
        />
      ) : id === 4 ? (
        <TreatmentInfoStack.Screen
          name="TreatmentFace"
          component={TreatmentFace}
          options={{
            headerTitle: "대면 진료",
          }}
        />
      ) : (
        <TreatmentInfoStack.Screen
          name="Treatment"
          component={Treatment}
          options={{
            headerTitle: "진료",
          }}
        />
      )}
    </TreatmentInfoStack.Navigator>
  );
}
