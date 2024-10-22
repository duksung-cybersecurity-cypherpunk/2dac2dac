import React, { useState, useEffect } from "react";
import { View, Text, StyleSheet, TouchableOpacity, Image } from "react-native";
import { useNavigation } from "@react-navigation/native";
import axios from "axios";

export default function QRLoad({ route }) {
  const navigation = useNavigation();
  const { doctorId, reservationId } = route.params;

  const [reservationData, setReservationData] = useState(null);
  const [loading, setLoading] = useState(true);

  const [item, setitem] = useState([]);

  const [userId, setUserId] = useState();
  const [name, setName] = useState("");
  const [age, setAge] = useState("");
  const [gender, setGender] = useState("");
  const [phoneNumber, setPhoneNumber] = useState("");

  const fetchData = async () => {
    try {
      const userInfo = await AsyncStorage.getItem("userInfo");
      const userData = JSON.parse(userInfo);
      setDoctorInfo(userData.id);

      const response = await fetch(
        `http://203.252.213.209:8080/api/v1/doctors/noncontactDiag/completed/${doctorInfo}`
      );
      const data = await response.json();
      setitem(data.data.completedReservationList);
      setCnt(data.data.totalCnt);
      console.log(item);
    } catch (error) {
      console.error("Error fetching data:", error);
    }
  };

  const patientData = async () => {
    try {
      const response = await fetch(
        `http://203.252.213.209:8080/api/v1/doctors/reservations/${doctorId}/${reservationId}/patientInfo`
      );
      const patient = await response.json();
      setUserId(patient.data.userId);
      setName(patient.data.userName);
      setAge(patient.data.age);
      setGender(patient.data.gender);
      setPhoneNumber(patient.data.phoneNumber);
      console.log(userId);
    } catch (error) {
      console.error("Error fetching data:", error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchData();
    patientData();
  }, []);
  useEffect(() => {
    setUserId(userId);
  }, [userId]);

  const blocks = [
    {
      id: 1,
      title: "진료 내역",
      imageUrl: require("../../../assets/images/PatientInfo/treatment.png"),
    },
    {
      id: 2,
      title: "투약 내역",
      imageUrl: require("../../../assets/images/PatientInfo/prescription.png"),
    },
    {
      id: 3,
      title: "건강 검진 내역",
      imageUrl: require("../../../assets/images/PatientInfo/examination.png"),
    },
    {
      id: 4,
      title: "예방 접종 내역",
      imageUrl: require("../../../assets/images/PatientInfo/vaccination.png"),
    },
  ];

  if (loading) {
    return (
      <View style={styles.container}>
        <Text>Loading...</Text>
      </View>
    );
  }

  const handleLoad = (id) => {
    if (id === 1) {
      navigation.navigate("TreatmentInfoStack", {
        screen: "Treatment",
        params: { userId: userId },
      });
      navigation.navigate("TreatmentInfoStack", {
        screen: "TreatmentFace",
        params: { userId: userId },
      });
      navigation.navigate("TreatmentInfoStack", {
        screen: "TreatmentNonFace",
        params: { userId: userId },
      });
    }
    if (id === 2) {
      navigation.navigate("PrescriptionInfoStac", {
        screen: "처방",
        params: { userId: userId },
      });
    }
    if (id === 3) {
      //ExaminationInfoStack
      navigation.navigate("ExaminationInfoStack", {
        screen: "건강검진",
        params: { userId: userId },
      });
    }
    //PatientInfoStack Vaccination
    if (id === 4) {
      navigation.navigate("PatientInfoStack", {
        screen: "예방 접종 내역",
        params: { userId: userId },
      });
    }
  };

  return (
    <View style={styles.container}>
      <Text style={[styles.titleText, { marginTop: 20 }]}>
        환자 정보를 확인해 주세요.
      </Text>
      <View style={styles.infoBlock}>
        <View style={styles.row}>
          <Text style={styles.infoSubText}>환자 명 {name}</Text>
          <Text style={styles.text}></Text>
        </View>
        <View style={styles.row}>
          <Text style={styles.infoSubText}>나이 {age}</Text>
          <Text style={styles.text}></Text>
        </View>
        <View style={styles.row}>
          <Text style={styles.infoSubText}>성별 {gender}</Text>
          <Text style={styles.text}></Text>
        </View>
      </View>
      <View style={styles.listBlock}>
        {blocks.slice(0, 4).map((blocks) => (
          <TouchableOpacity
            key={blocks.id}
            style={[styles.blocks]}
            onPress={() => handleLoad(blocks.id)}
            activeOpacity={0.7}
          >
            <Image source={blocks.imageUrl} />
            <Text style={styles.text}> {blocks.title} </Text>
          </TouchableOpacity>
        ))}

        <Text style={[styles.text, { marginTop: 50 }]}>
          주의사항{"\n"}- 열람 종료하기를 누른 이후 해당 기기로 불러온 환자 데이
          {"\n"}
          터는 소실되며, 새로 갱신된 환자 QR 촬영을 통해 다시 불{"\n"}
          러올 수 있습니다.
        </Text>

        <TouchableOpacity
          style={[styles.bottomBlocks]}
          onPress={() => navigation.navigate("PatientInfoStack", { id: null })}
          activeOpacity={0.7}
        >
          <Text sytle={styles.textButton}> 열람 종료하기 </Text>
        </TouchableOpacity>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "white",
  },
  row: {
    flexDirection: "row",
    justifyContent: "space-between",
  },
  infoBlock: {
    width: "90%",
    height: "12%",
    backgroundColor: "#F5F5F5",
    borderRadius: 8,
    margin: 20,
    paddingTop: 12,
    paddingLeft: 20,
  },
  infoSubText: {
    fontSize: 14,
    fontWeight: "bold",
    paddingTop: 3,
  },
  listBlock: {
    paddingRight: 20,
    paddingLeft: 20,
  },
  blocks: {
    flexDirection: "row",
    alignItems: "center",
    justifyContent: "center",
    width: "98%",
    height: "10%",
    backgroundColor: "#EBF2EA",
    borderRadius: 6,
    marginTop: 15,
  },
  bottomBlocks: {
    alignItems: "center",
    justifyContent: "center",
    width: "98%",
    height: "8%",
    backgroundColor: "#76B947",
    marginTop: 50,
    borderRadius: 6,
  },
  titleText: {
    fontSize: 24,
    fontWeight: "bold",
    marginLeft: 20,
  },
  textButton: {
    fontSize: 25,
    color: "white",
    fontWeight: "bold",
    marginTop: 10,
  },
  text: {
    fontSize: 14,
  },
});
