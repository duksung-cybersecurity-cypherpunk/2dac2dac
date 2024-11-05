import React, { useState } from "react";
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  TouchableOpacity,
  TextInput,
  Modal,
  Image
} from "react-native";
import axios from "axios";
import { useNavigation } from "@react-navigation/native";
import Icon from 'react-native-vector-icons/Ionicons'; // 아이콘 라이브러리

export default function PrescriptionWriting({ route }) {
  const navigation = useNavigation();
  const { doctorId, reservationId } = route.params;

  const [price, setPrice] = useState(0);
  const [opinion, setOpinion] = useState("");
  const [medications, setMedications] = useState([]); // 최종 의약품 목록
  const [isMedicationsVisible, setMedicationsVisible] = useState(false);
  const [searchKeyword, setSearchKeyword] = useState("");
  const [searchResults, setSearchResults] = useState([]);
  const [selectedMedications, setSelectedMedications] = useState({});

  const handleSubmit = async () => {
    const medicineList = medications.map(medication => ({
      medicineId: medication.medicineId,
      prescriptionCnt: medication.times, // 횟수
      medicationDays: medication.days, // 일 수
    }));

    try {
      const response = await axios.post(
        `${doctorId}/${reservationId}`,
        {
          price: price,
          medicineList: medicineList,
          doctorOpinion: opinion,
        }
      );
    } catch (error) {
      console.error("Error:", error);
    }
  };

  const addMedication = () => {
    const newMedications = Object.values(selectedMedications).map(item => ({
      medicineId: item.medicineId,
      medicineName: item.medicineName,
      medicineClassName: item.medicineClassName,
      medicineChart: item.medicineChart,
      medicineImageUrl: item.medicineImageUrl,
      days: "", // 초기값 설정
      times: "", // 초기값 설정
    }));
    setMedications([...medications, ...newMedications]);
    setSelectedMedications({});
    setMedicationsVisible(false);
  };

  const handleSearch = async () => {
    try {
      const response = await axios.post(
        `http://203.252.213.209:8080/api/v1/doctors/reservations/complete/${doctorId}/${reservationId}`, 
        {
            keyword: searchKeyword,
        });
      setSearchResults(response.data);
      console.log(searchResults);
    } catch (error) {
      console.error("Search Error:", error);
    }
  };

  const toggleSelection = (item) => {
    setSelectedMedications((prev) => {
      const newSelection = { ...prev };
      if (newSelection[item.id]) {
        delete newSelection[item.id];
      } else {
        newSelection[item.id] = {
          medicineId: item.id,
          medicineName: item.name,
          medicineClassName: item.className,
          medicineChart: item.chart,
          medicineImageUrl: item.imageUrl,
        };
      }
      return newSelection;
    });
  };

  const handleInputChange = (index, field, value) => {
    const newMedications = [...medications];
    newMedications[index][field] = value;
    setMedications(newMedications);
  };

  return (
    <View style={styles.screenContainer}>
      <ScrollView style={styles.scrollView}>
        <View style={styles.paymentContent}>
          <Text style={styles.titleText}>진료비 금액 입력</Text>
          <TextInput
            style={styles.input}
            placeholder="금액을 입력하세요"
            keyboardType="numeric"
            value={price}
            onChangeText={setPrice}
          />
        </View>
        <View style={styles.paymentContent}>
          <Text style={styles.titleText}>의사 소견 작성</Text>
          <TextInput
            style={styles.input}
            placeholder="의사 소견을 입력하세요"
            value={opinion}
            onChangeText={setOpinion}
          />
        </View>

        <TouchableOpacity
          style={[styles.button, { backgroundColor: "#F5F5F5", borderRadius: 8, height: 40, margin: 20 }]}
          onPress={() => setMedicationsVisible(true)}
        >
          <Icon name="add" size={24} color="#000" />
          <Text style={styles.buttonText}>처방 의약품을 추가해 주세요.</Text>
        </TouchableOpacity>

        {/* 의약품 검색 및 추가를 위한 모달창 */}
        <Modal
          visible={isMedicationsVisible}
          animationType="slide"
          onRequestClose={() => setMedicationsVisible(false)}
        >
          <View style={styles.modalContainer}>
            <Text style={styles.modalTitle}>의약품을 검색 및 추가하세요.</Text>
            <TextInput
              style={styles.searchInput}
              placeholder="의약품 검색"
              value={searchKeyword}
              onChangeText={setSearchKeyword}
            />
            <TouchableOpacity onPress={handleSearch}>
              <Text style={styles.searchButton}>검색</Text>
            </TouchableOpacity>

            <ScrollView>
              {searchResults.map((item) => (
                <TouchableOpacity
                  key={item.id}
                  style={[
                    styles.searchResultContainer,
                    selectedMedications[item.id] && { backgroundColor: "#F5F5F5" }
                  ]}
                  onPress={() => toggleSelection(item)}
                >
                  <Image
                    style={styles.medicationImage}
                    source={{ uri: item.imageUrl }}
                    defaultSource={require("../../../assets/images/PatientInfo/pills.png")}
                  />
                  <View style={styles.medicationDetails}>
                    <Text style={styles.medicineName}>{item.name}</Text>
                    <Text>{item.className}</Text>
                    <Text numberOfLines={2}>{item.chart}</Text>
                  </View>
                  <Icon
                    name={selectedMedications[item.id] ? "checkmark-circle" : "checkmark-circle-outline"}
                    size={24}
                    color={selectedMedications[item.id] ? "#76B947" : "#D6D6D6"}
                  />
                </TouchableOpacity>
              ))}
            </ScrollView>

            <TouchableOpacity
              style={[styles.button, { backgroundColor: "#9BD394", borderRadius: 8, height: 40, margin: 20 }]}
              onPress={addMedication}
            >
              <Text style={styles.buttonText}>선택된 의약품 추가하기</Text>
            </TouchableOpacity>

            <TouchableOpacity onPress={() => setMedicationsVisible(false)}>
              <Text style={styles.closeButton}>닫기</Text>
            </TouchableOpacity>
          </View>
        </Modal>

        {/* 선택된 의약품 목록 표시 */}
        {medications.map((medication, index) => (
          <View key={index} style={styles.medicationContainer}>
            <View style={[{ borderBlockColor: "#A3A3A3" }, { borderBottomWidth: 0.5 }]}>
              <View style={[{ flexDirection: "row" }]}>
                <Image
                  style={[{ width: 80 }, { height: 80 }]}
                  source={{ uri: medication.medicineImageUrl }}
                  defaultSource={require("../../../assets/images/PatientInfo/pills.png")}
                />
                <View style={[{ flex: 1, alignItems: "flex-start" , paddingLeft: 10 }]}>
                  <Text style={styles.hospitalName} numberOfLines={2}>{medication.medicineName}</Text>
                  <Text>{medication.medicineClassName}</Text>
                  <Text numberOfLines={2}>{medication.medicineChart}</Text>
                </View>
              </View>

              <Text style={[{alignItems: "flex-start", fontWeight: "bold"}]}>투약일 수 | 일일 투약 횟수</Text>
              <View style={styles.inputRow}>
                <TextInput
                  style={styles.inputSmall}
                  placeholder="일 수"
                  keyboardType="numeric"
                  value={medication.days}
                  onChangeText={(value) => handleInputChange(index, 'days', value)}
                /><Text>일 | </Text>
                <TextInput
                  style={styles.inputSmall}
                  placeholder="횟수"
                  keyboardType="numeric"
                  value={medication.times}
                  onChangeText={(value) => handleInputChange(index, 'times', value)}
                /><Text>회</Text>
              </View>
            </View>
          </View>
        ))}

        <View style={styles.buttonContainer}>
          <TouchableOpacity
            style={[styles.button, styles.submitButton]}
            onPress={handleSubmit}
          >
            <Text style={styles.buttonText}>처방전 발급</Text>
          </TouchableOpacity>
          <TouchableOpacity
            style={[styles.button, styles.cancelButton]}
            onPress={() => navigation.goBack()}
          >
            <Text style={styles.buttonText}>취소</Text>
          </TouchableOpacity>
        </View>
      </ScrollView>
    </View>
  );
}

const styles = StyleSheet.create({
  screenContainer: {
    flex: 1,
    alignItems: "center",
    justifyContent: "center",
    backgroundColor: "white",
  },
  scrollView: {
    flex: 1,
    width: "100%",
  },
  paymentContent: {
    padding: 30,
    backgroundColor: "white",
    borderRadius: 10,
  },
  input: {
    width: "100%",
    height: 50,
    borderColor: "gray",
    borderWidth: 1,
    marginBottom: 15,
    paddingLeft: 10,
  },
  modalContainer: {
    flex: 1,
    padding: 20,
    backgroundColor: "white",
  },
  modalTitle: {
    fontSize: 24,
    fontWeight: "bold",
    marginBottom: 20,
  },
  searchInput: {
    height: 40,
    borderColor: "gray",
    borderWidth: 1,
    marginBottom: 10,
    paddingHorizontal: 10,
  },
  searchButton: {
    padding: 10,
    backgroundColor: "#76B947",
    color: "white",
    borderRadius: 5,
    textAlign: "center",
  },
  searchResultContainer: {
    flexDirection: "row",
    alignItems: "center",
    padding: 10,
    borderBottomWidth: 1,
    borderColor: "#E0E0E0",
  },
  medicationImage: {
    width: 80,
    height: 80,
  },
  medicationDetails: {
    flex: 1,
    paddingLeft: 10,
  },
  medicineName: {
    fontWeight: "bold",
  },
  buttonContainer: {
    flexDirection: "row",
    justifyContent: "space-between",
  },
  button: {
    flex: 1,
    marginHorizontal: 3,
    borderRadius: 6,
  },
  submitButton: {
    backgroundColor: "#9BD394",
    alignItems: "center",
    padding: 10,
  },
  cancelButton: {
    borderWidth: 1,
    borderColor: "#9BD394",
    alignItems: "center",
    padding: 10,
  },
  buttonText: {
    color: "black",
  },
  closeButton: {
    marginTop: 20,
    textAlign: "center",
    color: "#007AFF",
  },
  medicationToggle: {
    fontSize: 18,
    marginBottom: 10,
  },
});
