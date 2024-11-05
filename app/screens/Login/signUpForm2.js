import React, { useState } from "react";
import {
  View,
  Text,
  TextInput,
  TouchableOpacity,
  StyleSheet,
  ScrollView,
  Alert,
} from "react-native";
import axios from "axios";

export default function SignUpForm2({ navigation, route }) {
  const { formData } = route.params; // Extract formData from route params

  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [phonenumber, setPhoneNumber] = useState("");
  const [verificationCode, setVerificationCode] = useState("");
  const [isCodeSent, setIsCodeSent] = useState(false);
  const [isCodeVerified, setIsCodeVerified] = useState(false);
  const [serverCode, setServerCode] = useState("");

  // Function to send verification code to email
  const sendVerificationCode = async () => {
    try {
      const response = await axios.post(
        "http://203.252.213.209:8080/api/v1/login/mailConfirm",
        { email: email }
      );

      if (response.data) {
        setServerCode(response.data);
        Alert.alert("인증 코드가 이메일로 전송되었습니다.");
        setIsCodeSent(true);
      } else {
        throw new Error("인증 코드가 없습니다.");
      }
    } catch (error) {
      console.error(error);
      Alert.alert("Error", "인증 코드 전송 실패");
    }
  };

  // Function to verify the entered code
  const verifyCode = () => {
    if (verificationCode === serverCode) {
      Alert.alert("인증 완료", "인증 코드가 확인되었습니다.");
      setIsCodeVerified(true);
    } else {
      Alert.alert("Error", "입력한 인증 코드가 일치하지 않습니다.");
    }
  };

  // Function to handle sign-up
  const handleSignUp = async () => {
    if (password !== confirmPassword) {
      Alert.alert("Error", "Passwords do not match");
      return;
    }

    if (!isCodeVerified) {
      Alert.alert("Error", "이메일 인증을 완료해 주세요.");
      return;
    }

    try {
      const response = await axios.post(
        "http://203.252.213.209:8080/api/v1/doctors/signup",
        {
          name: username,
          email: email,
          password: password,
          isLicenseCertificated: true,
          profileImagePath: "/imagres/doctors/johndoe.jpg",
          oneLiner: formData.oneLiner, // Include oneLiner from formData
          departmentId: formData.departmentId, // Include departmentId
          hospitalId: formData.hospitalId, // Include hospitalId
          experience: formData.experience[0].text, // Include experience
        }
      );

      if (response.status === 200) {
        Alert.alert(
          "회원가입이 완료되었습니다.",
          response.data.message || "회원가입 성공"
        );
        navigation.navigate("Login");
      } else {
        Alert.alert(
          "Error",
          "회원가입 실패: " + (response.data.message || "알 수 없는 오류")
        );
      }
    } catch (error) {
      console.error(
        "Sign-up error:",
        error.response ? error.response.data : error.message
      );
      Alert.alert("Error", "Sign up failed");
    }
  };

  return (
    <ScrollView contentContainerStyle={styles.container}>
      <Text style={styles.title}>기본 정보를 입력해주세요.</Text>

      <Text style={styles.label}>이메일</Text>
      <View style={styles.emailContainer}>
        <TextInput
          style={styles.emailInput}
          placeholder="email@example.com"
          value={email}
          onChangeText={setEmail}
          keyboardType="email-address"
          autoCapitalize="none"
        />
        <TouchableOpacity
          style={styles.codeButton}
          onPress={sendVerificationCode}
          disabled={isCodeSent}
        >
          <View style={styles.buttonContent}>
            <Text style={styles.buttonText}>
              {isCodeSent ? "코드 전송 완료" : "코드 전송"}
            </Text>
          </View>
        </TouchableOpacity>
      </View>

      <Text style={styles.label}>이메일 인증 코드</Text>
      <View style={styles.emailContainer}>
        <TextInput
          style={styles.emailInput}
          placeholder="이메일 인증 코드"
          value={verificationCode}
          onChangeText={setVerificationCode}
        />
        <TouchableOpacity
          style={styles.codeButton}
          onPress={verifyCode}
          disabled={!isCodeSent}
        >
          <Text style={styles.buttonText}>코드 확인</Text>
        </TouchableOpacity>
      </View>

      <Text style={styles.label}>이름</Text>
      <View style={styles.emailContainer}>
        <TextInput
          style={styles.Input}
          placeholder="이름"
          value={username}
          onChangeText={setUsername}
          editable={isCodeVerified}
        />
      </View>

      <Text style={styles.label}>비밀번호</Text>
      <View style={styles.emailContainer}>
        <TextInput
          style={styles.Input}
          placeholder="비밀번호"
          value={password}
          onChangeText={setPassword}
          secureTextEntry
          editable={isCodeVerified}
        />
      </View>

      <Text style={styles.label}>비밀번호 확인</Text>
      <View style={styles.emailContainer}>
        <TextInput
          style={styles.Input}
          placeholder="비밀번호 확인"
          value={confirmPassword}
          onChangeText={setConfirmPassword}
          secureTextEntry
          editable={isCodeVerified}
        />
      </View>

      <Text style={styles.label}>휴대폰 번호</Text>
      <View style={styles.emailContainer}>
        <TextInput
          style={styles.Input}
          placeholder="휴대폰 번호를 - 없이 입력해주세요."
          value={phonenumber}
          onChangeText={setPhoneNumber}
          editable={isCodeVerified}
        />
      </View>

      <TouchableOpacity
        style={styles.button}
        onPress={handleSignUp}
        disabled={!isCodeVerified}
      >
        <Text style={styles.buttonText}>가입 완료</Text>
      </TouchableOpacity>
    </ScrollView>
  );
}

const styles = StyleSheet.create({
  container: {
    padding: 16,
    justifyContent: "center",
    backgroundColor: "#fff",
  },
    title: {
      fontSize: 24,
      fontWeight: "bold",
      marginBottom: 20,
      marginTop: 20,
    },
  label: {
    marginBottom: 8,
    fontSize: 14,
    fontWeight: "bold",
  },
  emailContainer: {
    flexDirection: "row",
    alignItems: "center",
    justifyContent: "space-between",
    marginBottom: 16,
  },
  emailInput: {
    flex: 1,
    height: 40,
    borderWidth: 1,
    borderColor: "#ccc",
    borderBottomWidth: 1,
    paddingHorizontal: 10,
      borderRadius: 8,
      padding: 8,
    marginRight: 10,
  },
   Input: {
      flex: 1,
      height: 40,
      borderWidth: 1,
      borderColor: "#ccc",
      borderBottomWidth: 1,
      paddingHorizontal: 10,
        borderRadius: 8,
        padding: 8,
    },
  codeButton: {
    backgroundColor: "#4CAF50",
    paddingVertical: 10,
    paddingHorizontal: 15,
    borderRadius: 5,
  },
  buttonText: {
    color: "#fff",
    fontWeight: "bold",
    alignItems: "center",
    textAlign: "center",
  },
  button: {
    backgroundColor: "#4CAF50",
      padding: 10,
      borderRadius: 8,
      alignItems: "center",
    borderRadius: 5,
  },
});