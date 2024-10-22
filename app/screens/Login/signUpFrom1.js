import React, { useState, useEffect } from "react";
import {
  View,
  Text,
  TextInput,
  TouchableOpacity,
  ScrollView,
  StyleSheet,
  FlatList,
  Modal,
} from "react-native";
import { useNavigation } from "@react-navigation/native";

const SignUpForm1 = () => {
  const navigation = useNavigation();
  const [oneLiner, setOneLiner] = useState("");
  const [departmentId, setDepartmentId] = useState("");
  const [hospitalId, setHospitalId] = useState("");
  const [experience, setExperience] = useState([{ current: true, text: "" }]);
  const [departments, setDepartments] = useState([]);
  const [hospitals, setHospitals] = useState([]);
  const [filteredHospitals, setFilteredHospitals] = useState([]);
  const [filteredDepartments, setFilteredDepartments] = useState([]);
  const [modalVisible, setModalVisible] = useState(false);
  const [modalType, setModalType] = useState(""); // "hospital" or "department"
  const [selectedDepartment, setSelectedDepartment] = useState(null);
  const [selectedHospital, setSelectedHospital] = useState(null);
  const [searchQuery, setSearchQuery] = useState("");

  useEffect(() => {
    const fetchDepartments = async () => {
      try {
        const response = await fetch(
          "http://203.252.213.209:8080/api/v1/noncontactDiag/departments"
        );
        const data = await response.json();
        if (data.status === 200) {
          setDepartments(data.data.departmentInfoList);
          setFilteredDepartments(data.data.departmentInfoList); // Set initial filtered departments
        } else {
          console.error("Failed to fetch departments:", data.message);
        }
      } catch (error) {
        console.error("Error fetching departments:", error);
      }
    };

    const fetchHospitals = async () => {
      try {
        const response = await fetch(
          "http://203.252.213.209:8080/api/v1/agency/hospitals"
        );
        const data = await response.json();
        if (data.status === 200) {
          setHospitals(data.data);
          setFilteredHospitals(data.data); // Set initial filtered hospitals
        } else {
          console.error("Failed to fetch hospitals:", data.message);
        }
      } catch (error) {
        console.error("Error fetching hospitals:", error);
      }
    };

    fetchDepartments();
    fetchHospitals();
  }, []);

  useEffect(() => {
    if (searchQuery) {
      const filteredHosp = hospitals.filter((hospital) =>
        hospital.name.toLowerCase().includes(searchQuery.toLowerCase())
      );
      setFilteredHospitals(filteredHosp);

      const filteredDept = departments.filter((department) =>
        department.departmentName
          .toLowerCase()
          .includes(searchQuery.toLowerCase())
      );
      setFilteredDepartments(filteredDept);
    } else {
      setFilteredHospitals(hospitals);
      setFilteredDepartments(departments);
    }
  }, [searchQuery, hospitals, departments]);

  const handleExperienceChange = (index, text) => {
    const newExperience = [...experience];
    newExperience[index].text = text;
    setExperience(newExperience);
  };

  const handleSubmit = () => {
    const formData = {
      oneLiner,
      departmentId,
      hospitalId,
      experience,
    };

    navigation.navigate("signUpForm2", { formData });
  };

  const handleSelectDepartment = (department) => {
    setDepartmentId(department.departmentId.toString());
    setSelectedDepartment(department);
    setModalVisible(false);
  };

  const handleSelectHospital = (hospital) => {
    setHospitalId(hospital.id.toString());
    setSelectedHospital(hospital);
    setModalVisible(false);
  };

  const openModal = (type) => {
    setModalType(type);
    setSearchQuery(""); // Clear search query when opening modal
    setModalVisible(true);
  };

  return (
    <ScrollView style={styles.container}>
      <Text style={styles.heading}>의사 소개 정보를 입력해 주세요.</Text>

      <Text style={styles.label}>대표 제목을 작성해 주세요.</Text>
      <TextInput
        style={styles.input}
        placeholder="예시) 안녕하세요. OOO 병원 OOO 의사입니다."
        value={oneLiner}
        onChangeText={setOneLiner}
      />

      <Text style={styles.label}>소개 상세 내용을 작성해 주세요.</Text>
      <TextInput
        style={styles.input}
        placeholder="최대 300자까지 작성할 수 있습니다."
        value={experience[0].text}
        onChangeText={(text) => handleExperienceChange(0, text)}
        multiline
      />

      <Text style={styles.label}>주 진료 항목을 선택해 주세요.</Text>
      <TouchableOpacity
        style={styles.input}
        onPress={() => openModal("department")}
      >
        <Text>
          {selectedDepartment
            ? selectedDepartment.departmentName
            : "진료 항목 선택"}
        </Text>
      </TouchableOpacity>

      <Text style={styles.label}>병원을 선택해 주세요.</Text>
      <TouchableOpacity
        style={styles.input}
        onPress={() => openModal("hospital")}
      >
        <Text>{selectedHospital ? selectedHospital.name : "병원 선택"}</Text>
      </TouchableOpacity>

      <TouchableOpacity style={styles.submitButton} onPress={handleSubmit}>
        <Text style={styles.submitButtonText}>입력 완료</Text>
      </TouchableOpacity>

      {/* Selection Modal */}
      <Modal visible={modalVisible} animationType="slide">
        <View style={styles.modalContainer}>
          <Text style={styles.modalTitle}>
            {modalType === "hospital" ? "병원 선택" : "진료 항목 선택"}
          </Text>

          <TextInput
            style={styles.input}
            placeholder={
              modalType === "hospital"
                ? "병원 이름으로 검색"
                : "진료 항목으로 검색"
            }
            value={searchQuery}
            onChangeText={setSearchQuery}
          />

          <FlatList
            data={
              modalType === "hospital" ? filteredHospitals : filteredDepartments
            }
            keyExtractor={(item) =>
              (modalType === "hospital"
                ? item.id
                : item.departmentId
              ).toString()
            }
            renderItem={({ item }) => (
              <TouchableOpacity
                onPress={
                  modalType === "hospital"
                    ? () => handleSelectHospital(item)
                    : () => handleSelectDepartment(item)
                }
              >
                <Text style={styles.departmentItem}>
                  {modalType === "hospital" ? item.name : item.departmentName}
                </Text>
              </TouchableOpacity>
            )}
          />
          <TouchableOpacity
            onPress={() => setModalVisible(false)}
            style={styles.closeButton}
          >
            <Text style={styles.closeButtonText}>닫기</Text>
          </TouchableOpacity>
        </View>
      </Modal>
    </ScrollView>
  );
};

const styles = StyleSheet.create({
  container: {
    padding: 16,
    backgroundColor: "#fff",
  },
  heading: {
    fontSize: 18,
    fontWeight: "bold",
    marginBottom: 16,
  },
  label: {
    marginBottom: 8,
    fontSize: 14,
    fontWeight: "bold",
  },
  input: {
    borderWidth: 1,
    borderColor: "#ccc",
    borderRadius: 8,
    padding: 8,
    marginBottom: 16,
  },
  experienceRow: {
    flexDirection: "row",
    alignItems: "center",
    marginBottom: 8,
  },
  submitButton: {
    backgroundColor: "#4CAF50",
    padding: 16,
    borderRadius: 8,
    alignItems: "center",
  },
  submitButtonText: {
    color: "#fff",
    fontSize: 16,
  },
  modalContainer: {
    flex: 1,
    justifyContent: "center",
    padding: 20,
    backgroundColor: "#fff",
  },
  modalTitle: {
    fontSize: 18,
    fontWeight: "bold",
    marginBottom: 20,
  },
  departmentItem: {
    padding: 10,
    borderBottomWidth: 1,
    borderBottomColor: "#ccc",
  },
  closeButton: {
    marginTop: 20,
    backgroundColor: "#4CAF50",
    padding: 10,
    borderRadius: 8,
    alignItems: "center",
  },
  closeButtonText: {
    color: "#fff",
    fontSize: 16,
  },
});

export default SignUpForm1;
