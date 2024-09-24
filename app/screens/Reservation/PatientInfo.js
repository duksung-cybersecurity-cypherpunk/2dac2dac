import React, { useState } from "react";
import {
  View,
  Text,
  TextInput,
  Button,
  StyleSheet,
  ScrollView,
  TouchableOpacity,
} from "react-native";
import { useNavigation, useRoute } from "@react-navigation/native";

export function PatientInfo() {
  const [medicationChecked, setMedicationChecked] = useState(false);
  const [allergyChecked, setAllergyChecked] = useState(false);
  const [congenitalChecked, setCongenitalChecked] = useState(false);
  const [prescribedDrug, setPrescribedDrug] = useState(false);
  const [allergyInfo, setAllergyInfo] = useState(
    "사진 첨부 혹은 최소 5자 이상의 글을 작성해 주세요."
  );
  const [congenitalInfo, setCongenitalInfo] = useState(
    "사진 첨부 혹은 최소 5자 이상의 글을 작성해 주세요."
  );
  const [otherInfo, setOtherInfo] = useState(
    "사진 첨부 혹은 최소 5자 이상의 글을 작성해 주세요."
  );
  const navigation = useNavigation();

  const route = useRoute();
  const { doctorId, doctor } = route.params;

  const handleSubmit = () => {
    const data = {
      prescribedDrug: prescribedDrug,
      isPrescribedDrug: medicationChecked,
      allergicSymptom: allergyInfo,
      isAllergicSymptom: allergyChecked,
      inbornDisease: congenitalInfo,
      isInbornDisease: congenitalChecked,
      additionalInformation: otherInfo,
      doctorId: doctorId,
    };

    navigation.navigate("ReservationForm2", { formData: data, doctor: doctor });
  };

  return (
    <ScrollView contentContainerStyle={styles.screenContainer}>
      <View style={styles.headerContainer}>
        <Text style={styles.header}>증상을 알려주세요.</Text>
        <Text style={styles.subtitle}>내과 평균 진료비: 6,000원</Text>
        <Text style={styles.subtitle}>
          상세 진료 내역에 따라 변동될 수 있습니다.
        </Text>
      </View>
      <View style={styles.screenContainer}></View>
      <Text style={styles.label}>현재 복용 중인 약이 있다면 알려주세요.</Text>
      <View style={styles.checkboxContainer}>
        <TouchableOpacity
          style={[
            styles.checkbox,
            medicationChecked && styles.selectedCheckbox,
          ]}
          onPress={() => setMedicationChecked(!medicationChecked)}
        >
          {medicationChecked && <Text>✔️</Text>}
        </TouchableOpacity>
        <Text style={styles.checkboxLabel}>현재 복용 중인 약이 있습니다.</Text>
      </View>
      <TextInput
        style={styles.input}
        placeholder="사진 첨부 혹은 최소 5자 이상의 글을 작성해 주세요."
        placeholderTextColor="#888"
        onChangeText={(text) => setPrescribedDrug(text)}
        value={prescribedDrug}
      />

      <Text style={styles.label}>
        알레르기 증상을 보인 적이 있다면 알려주세요.
      </Text>
      <View style={styles.checkboxContainer}>
        <TouchableOpacity
          style={[styles.checkbox, allergyChecked && styles.selectedCheckbox]}
          onPress={() => setAllergyChecked(!allergyChecked)}
        >
          {allergyChecked && <Text>✔️</Text>}
        </TouchableOpacity>
        <Text style={styles.checkboxLabel}>
          약이나 음식물로 인한 알레르기 혹은 그와 유사한 증상을 보인 적이
          있습니다.
        </Text>
      </View>
      <TextInput
        style={styles.input}
        placeholder="사진 첨부 혹은 최소 5자 이상의 글을 작성해 주세요."
        placeholderTextColor="#888"
        onChangeText={(text) => setAllergyInfo(text)}
        value={allergyInfo}
      />

      <Text style={styles.label}>선천적 질환을 앓고 있다면 알려주세요.</Text>
      <View style={styles.checkboxContainer}>
        <TouchableOpacity
          style={[
            styles.checkbox,
            congenitalChecked && styles.selectedCheckbox,
          ]}
          onPress={() => setCongenitalChecked(!congenitalChecked)}
        >
          {congenitalChecked && <Text>✔️</Text>}
        </TouchableOpacity>
        <Text style={styles.checkboxLabel}>
          앓고 있는 선천적 질환이 있습니다.
        </Text>
      </View>
      <TextInput
        style={styles.input}
        placeholder="사진 첨부 혹은 최소 5자 이상의 글을 작성해 주세요."
        placeholderTextColor="#888"
        onChangeText={(text) => setCongenitalInfo(text)}
        value={congenitalInfo}
      />

      <Text style={styles.label}>
        기타 전달하고 싶은 정보가 있다면 알려주세요.
      </Text>
      <TextInput
        style={styles.input}
        placeholder="최대 200자까지 작성할 수 있습니다."
        placeholderTextColor="#888"
        onChangeText={(text) => setOtherInfo(text)}
        value={otherInfo}
      />

      <TouchableOpacity onPress={handleSubmit} style={styles.submitButton}>
        <Text style={styles.submitButtonText}>진료시간 정하러 가기</Text>
      </TouchableOpacity>
    </ScrollView>
  );
}

const styles = StyleSheet.create({
  screenContainer: {
    justifyContent: "flex-start",
    backgroundColor: "white",
    padding: 16,
  },
  headerContainer: {
    backgroundColor: "white",
    width: "100%",
    borderBottomWidth: 10,
    borderBottomColor: "#EBF2EA",
    paddingBottom: 20,
  },
  header: {
    fontSize: 19,
    fontWeight: "bold",
    marginBottom: 10,
  },
  subtitle: {
    fontSize: 14,
    color: "gray",
    marginBottom: 4,
  },
  label: {
    fontSize: 16,
    marginBottom: 20,
    alignSelf: "flex-start",
  },
  checkboxContainer: {
    flexDirection: "row",
    alignItems: "center",
    marginBottom: 20,
  },
  checkbox: {
    height: 24,
    width: 24,
    borderWidth: 2,
    marginRight: 10,
    justifyContent: "center",
    alignItems: "center",
    borderRadius: 4,
    borderColor: "#ccc",
  },
  selectedCheckbox: {
    backgroundColor: "#76B947",
  },
  checkboxLabel: {
    fontSize: 16,
    flex: 1,
  },
  input: {
    width: "100%",
    height: 100,
    borderColor: "gray",
    backgroundColor: "rgba(163, 163, 163, 0.1)",
    borderWidth: 1,
    marginBottom: 16,
    paddingHorizontal: 8,
    paddingTop: 10,
    fontSize: 14,
    borderRadius: 8,
    textAlignVertical: "top",
  },
  submitButton: {
    backgroundColor: "#5E9740",
    padding: 16,
    borderRadius: 8,
    alignItems: "center",
  },
  submitButtonText: {
    color: "#fff",
    fontSize: 16,
    fontWeight: "bold",
  },
});
