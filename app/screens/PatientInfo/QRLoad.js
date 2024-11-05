import React, { useState, useEffect } from "react";
import { View, Text, StyleSheet, TouchableOpacity, Image } from "react-native";
import { useNavigation } from "@react-navigation/native";

export default function QRLoad({ route }) {
  const navigation = useNavigation();
  const { doctorId, reservationId } = route.params;
  const [loading, setLoading] = useState(true);

  const [userId, setUserId] = useState();
  const [name, setName] = useState("");
  const [age, setAge] = useState("");
  const [gender, setGender] = useState("");

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
    } catch (error) {
      console.error("Error fetching data:", error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    patientData();
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

  const handleLoad = (id, userId) => {
    if (id === 1) {
      navigation.navigate("PatientInfoStack", {
        screen: "Treatment",
        id: 2,
        params: { userId: userId },
      });
    }
    if (id === 2) {
      navigation.navigate("PatientInfoStack", {
        screen: "Prescription",
        id: 3,
        params: { userId: userId },
      });
    }
    if (id === 3) {
      navigation.navigate("PatientInfoStack", {
        screen: "Examination",
        id: 4,
        params: { userId: userId },
      });
    }
    if (id === 4) {
      navigation.navigate("PatientInfoStack", {
        screen: "Vaccination",
        id: 5,
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
            onPress={() => handleLoad(blocks.id, userId)}
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
          onPress={() => navigation.goBack()}
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
    backgroundColor: "#F5F5F5",
    borderRadius: 8,
    margin: 20,
    padding: 10,
    paddingLeft: 15,
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
