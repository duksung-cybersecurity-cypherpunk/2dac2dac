import React, { useState } from "react";
import {
  View,
  Text,
  TextInput,
  TouchableOpacity,
  StyleSheet,
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
      Alert.alert("Error", "이메일 인증을 완료해주세요.");
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
          "회원가입이 완료됐습니다.",
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
    <View style={styles.container}>
      <Text style={styles.title}>Sign Up</Text>
      {/* Email verification code send input and button */}
      <View style={styles.emailContainer}>
        <TextInput
          style={styles.emailInput}
          placeholder="Email"
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

      <View style={styles.emailContainer}>
        <TextInput
          style={styles.emailInput}
          placeholder="Email 인증 코드"
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
      <View style={styles.emailContainer}>
        <TextInput
          style={styles.emailInput}
          placeholder="Username"
          value={username}
          onChangeText={setUsername}
          editable={isCodeVerified}
        />
      </View>
      <View style={styles.emailContainer}>
        <TextInput
          style={styles.emailInput}
          placeholder="Password"
          value={password}
          onChangeText={setPassword}
          secureTextEntry
          editable={isCodeVerified}
        />
      </View>
      <View style={styles.emailContainer}>
        <TextInput
          style={styles.emailInput}
          placeholder="Confirm Password"
          value={confirmPassword}
          onChangeText={setConfirmPassword}
          secureTextEntry
          editable={isCodeVerified}
        />
      </View>
      <View style={styles.emailContainer}>
        <TextInput
          style={styles.emailInput}
          placeholder="PhoneNumber"
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
        <Text style={styles.buttonText}>Sign Up</Text>
      </TouchableOpacity>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 20,
    justifyContent: "center",
    backgroundColor: "#fff",
  },
  title: {
    fontSize: 24,
    fontWeight: "bold",
    marginBottom: 20,
    textAlign: "center",
  },
  emailContainer: {
    flexDirection: "row",
    alignItems: "center",
    justifyContent: "space-between",
    marginBottom: 15,
  },
  emailInput: {
    flex: 1,
    height: 40,
    borderColor: "#ccc",
    borderBottomWidth: 1,
    paddingHorizontal: 10,
    marginRight: 10,
  },
  codeButton: {
    backgroundColor: "#FFC107",
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
    backgroundColor: "#007BFF",
    paddingVertical: 15,
    borderRadius: 5,
  },
});
