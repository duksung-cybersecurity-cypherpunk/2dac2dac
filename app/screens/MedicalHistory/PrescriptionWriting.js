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
      medicineId: Number(medication.medicineId),
      prescriptionCnt: Number(medication.times) || 0, // 변환 실패 시 0으로 설정
      medicationDays: Number(medication.days) || 0, // 변환 실패 시 0으로 설정
    }));
  
    console.log(price);
    console.log(medicineList);
    console.log(opinion);
    try {
      const response = await axios.post(
        `http://203.252.213.209:8080/api/v1/doctors/reservations/complete/${doctorId}/${reservationId}`,
        {
          price: price,
          medicineList: medicineList,
          doctorOpinion: opinion,
        }
      );
      console.log(response.data);
    } catch (error) {
      console.error("Error:", error);
    }
  };
  

  const addMedication = () => {
    const newMedications = Object.values(selectedMedications).map(item => ({
      medicineId: Number(item.medicineId),
      medicineName: item.medicineName,
      medicineClassName: item.medicineClassName,
      medicineChart: item.medicineChart,
      medicineImageUrl: item.medicineImageUrl,
      days: 0, // 초기값 설정
      times: 0, // 초기값 설정
    }));
  
    setMedications([...medications, ...newMedications]);
    console.log(newMedications);
    setSelectedMedications({});
    setMedicationsVisible(false);
  };


  const handleSearch = async () => {
    try {
      const response = await axios.post(
        `http://203.252.213.209:8080/api/v1/doctors/noncontactDiag/medicines`, 
        {
            keyword: searchKeyword,
        });
        setSearchResults(response.data.data);
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
          medicineId: Number(item.id),
          medicineName: item.name,
          medicineClassName: item.className,
          medicineChart: item.chart,
          medicineImageUrl: item.imageUrl,
        };
      }
      console.log(newSelection);
      return newSelection;
    });
  };

  const handleInputChange = (index, field, value) => {
    const newMedications = [...medications];
    // 입력값을 숫자로 변환, 빈 값이면 0으로 설정
    newMedications[index][field] = value ? parseFloat(value) : 0; 
    setMedications(newMedications);
  };

  return (
    <View style={styles.screenContainer}>
      <ScrollView style={styles.scrollView}>
        <View style={styles.paymentContent}>
          <Text style={styles.titleText}>진료비 금액을 입력해 주세요.</Text>
          <TextInput
            style={styles.input}
            placeholder="금액을 입력하세요"
            keyboardType="numeric"
            value={price}
            onChangeText={setPrice}
          />
        </View>
        <View style={styles.paymentContent}>
          <Text style={styles.titleText}>의사 소견을 작성해 주세요.</Text>
          <TextInput
            style={styles.input}
            placeholder="의사 소견을 입력하세요"
            value={opinion}
            onChangeText={setOpinion}
          />
        </View>
        
        <View style={styles.paymentContent}>
          <Text style={styles.titleText}>처방 의약품을 기입해 주세요.</Text>
          <TouchableOpacity
            style={[styles.buttonSt, { backgroundColor: "#F5F5F5", borderRadius: 8, height: 80, marginLeft: 10, }]}
            onPress={() => setMedicationsVisible(true)}
          >
            <Icon name="add" size={24} color="#000" />
            <Text>처방 의약품을 추가해 주세요.</Text>
          </TouchableOpacity>
        </View>

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
              placeholder="의약품명을 입력하세요."
              value={searchKeyword}
              onChangeText={setSearchKeyword}
            />
            <TouchableOpacity onPress={handleSearch}>
              <Text style={styles.searchButton}>검색</Text>
            </TouchableOpacity>

            <ScrollView>
              {searchResults.map((item) => {
                return (
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
                );
              })}
            </ScrollView>
            <View style={[{ flexDirection: "row",}]}>
            <TouchableOpacity
              style={styles.submitButton}
              onPress={addMedication}
            >
              <Text style={styles.buttonText}>추가하기</Text>
            </TouchableOpacity>

            <TouchableOpacity onPress={() => setMedicationsVisible(false)}>
              <Text style={styles.cancelButton}>뒤로가기</Text>
            </TouchableOpacity>
            </View>
          </View>
        </Modal>

        {/* 선택된 의약품 목록 표시 */}
        {medications.map((medication, index) => (
          <View key={medication.medicineId||index} style={styles.medicationContainer}>
            <View style={[{ borderBlockColor: "#A3A3A3" }, { borderBottomWidth: 0.5 }]}>
              <View style={[{ flexDirection: "row", marginLeft: 20, }]}>
                <Image
                  style={[{ width: 80 }, { height: 80 }, {borderRadius: 6}, { marginBottom: 20}]}
                  source={{ uri: medication.medicineImageUrl }}
                  defaultSource={require("../../../assets/images/PatientInfo/pills.png")}
                />
                <View style={[{ flex: 1, alignItems: "flex-start" , paddingLeft: 10, }]}>
                  <Text style={styles.hospitalName} numberOfLines={2}>{medication.medicineName}</Text>
                  <Text style={styles.subName}>{medication.medicineClassName}</Text>
                  <Text style={styles.explainText} numberOfLines={2}>{medication.medicineChart}</Text>
                </View>
              </View>

              <Text style={[{alignItems: "flex-start", fontWeight: "bold", marginLeft: 20,}]}>투약일 수 | 일일 투약 횟수</Text>
              <View style={[{ flexDirection: "row", marginLeft: 20, }]}>
                <TextInput
                  style={styles.inputSmall}
                  placeholder="일 수"
                  keyboardType="numeric"
                  value={medication.days}
                  onChangeText={(value) => handleInputChange(index, 'days', value)}
                /><Text style={[{marginTop: 16,}]}>일   |   </Text>
                <TextInput
                  style={styles.inputSmall}
                  placeholder="횟수"
                  keyboardType="numeric"
                  value={medication.times}
                  onChangeText={(value) => handleInputChange(index, 'times', value)}
                /><Text style={[{marginTop: 16, marginBottom: 30,}]}>회</Text>
              </View>
            </View>
          </View>
        ))}

        <View style={styles.buttonContainer}>
          <TouchableOpacity
            style={[styles.buttonSt, styles.EndButton]}
            onPress={handleSubmit}
          >
            <Text style={styles.buttonText}>처방전 작성 완료하기</Text>
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
  inputSmall: {
    width: "40%",
    height: 40,
    borderColor: "gray",
    borderWidth: 0.5,
    borderRadius: 6,
    paddingLeft: 10,
    marginTop: 10,
    marginRight: 5,
  },
  paymentContent: {
    padding: 20,
    backgroundColor: "white",
    borderRadius: 10,
  },
  input: {
    width: "100%",
    height: 40,
    borderColor: "gray",
    borderWidth: 0.5,
    paddingLeft: 10,
  },
  modalContainer: {
    flex: 1,
    padding: 20,
    backgroundColor: "white",
  },
  modalTitle: {
    fontSize: 23,
    fontWeight: "bold",
    marginTop: 20,
    marginBottom: 20,
  },
  hospitalName: {
    fontSize: 18,
    fontWeight: "bold",
    marginBottom: 3,
  },
  subName: {
    fontSize: 14,
    color: "#A3A3A3",
    marginBottom: 3,
  },
  explainText: {
    fontSize: 16,
    marginBottom: 10,
  },
  searchInput: {
    height: 40,
    borderColor: "gray",
    borderWidth: 0.5,
    marginBottom: 10,
    paddingHorizontal: 10,
  },
  searchButton: {
    padding: 10,
    backgroundColor: "#76B947",
    color: "white",
    borderRadius: 5,
    textAlign: "center",
    marginBottom: 20,
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
    alignItems: "center",
    justifyContent: "center",
    width: "90%",
    marginLeft: 20,
  },
  button: {
    width: "30%",
    height: "5%",
    alignItems: "center",
    justifyContent: "center",
    borderRadius: 6,
  },
  submitButton: {
    backgroundColor: "#76B947",
    alignItems: "center",
    borderRadius: 8,
    padding: 10,
    margin: 5,
    marginLeft: 100,
  },
  buttonSt: {
    width: "95%",
    alignItems: "center",
    justifyContent: "center",
    borderRadius: 6,
  },
  EndButton: {
    backgroundColor: "#76B947",
    borderRadius: 8,
    padding: 10,
    marginTop: 30,
  },
  cancelButton: {
    borderWidth: 1,
    borderColor: "#76B947",
    alignItems: "center",
    borderRadius: 8,
    padding: 10,
    margin: 5,
    fontSize: 16,
  },
  buttonText: {
    fontSize: 16,
    color: "white",
  },
  titleText: {
    fontSize: 17,
    marginBottom: 15,
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
