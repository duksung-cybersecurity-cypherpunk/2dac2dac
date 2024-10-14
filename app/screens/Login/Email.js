import React, { useState, useEffect } from "react";
import {
  View,
  Text,
  TextInput,
  Button,
  Alert,
  StyleSheet,
  TouchableOpacity,
} from "react-native";
import axios from "axios";
import AsyncStorage from "@react-native-async-storage/async-storage";

const Email = ({ route, navigation }) => {
  const { username, password } = route.params;
  const [enteredToken, setEnteredToken] = useState("");
  const [serverToken, setServerToken] = useState(null);

  useEffect(() => {
    sendCredentials();
  }, []);

  const sendCredentials = async () => {
    try {
      const response = await axios.post(
        "http://203.252.213.209:8080/api/v1/login/doctor/mailConfirm",
        {
          name: username,
          password: password,
        }
      );
      setServerToken(response.data);
    } catch (error) {
      Alert.alert("Error", "Failed to verify email. Please try again.");
    }
  };

  const handleVerifyToken = async () => {
    if (enteredToken === serverToken) {
      Alert.alert("Success", "Email verified successfully.");
      await handleLogin(); // Call handleLogin() after successful email verification
    } else {
      Alert.alert(
        "Invalid Token",
        "The token you entered is incorrect. Please try again."
      );
    }
  };

  const handleLogin = async () => {
    try {
      const response = await axios.post(
        "http://203.252.213.209:8080/api/v1/doctors/login/jwt",
        {
          username: username,
          password: password,
        }
      );

      // JWT 토큰을 헤더에서 추출해 AsyncStorage에 저장
      const token = response.headers["authorization"].split(" ")[1]; // "Bearer <token>"에서 토큰만 추출

      await AsyncStorage.setItem("jwtToken", token); // AsyncStorage에 토큰 저장

      Alert.alert("Success", "Login successful");

      // 로그인 후 페이지 이동
      navigation.reset({
        index: 0,
        routes: [{ name: "BottomTabNavigator" }],
      });

      // 저장된 토큰으로 GET 요청을 보내기
      getDataWithToken(token);
    } catch (error) {
      console.error(error);
      Alert.alert("Error", "Login failed");
    }
  };

  const getDataWithToken = async (token) => {
    try {
      const response = await axios.get(
        "http://203.252.213.209:8080/api/v1/doctors/login/jwt",
        {
          headers: {
            Authorization: `Bearer ${token}`, // Authorization 헤더에 토큰을 추가
          },
        }
      );

      console.log("GET 요청 성공:", response.data);
      const userData = response.data;
      await AsyncStorage.setItem("userInfo", JSON.stringify(userData));
      Alert.alert("Success", "Data fetched successfully");
    } catch (error) {
      console.error("GET 요청 실패:", error);
      Alert.alert("Error", "Failed to fetch data");
    }
  };

  return (
    <View style={styles.container}>
      <Text style={styles.title}>인증코드 입력</Text>
      <Text style={styles.subtitle}>
        등록하신 이메일로 인증 코드를 보내드렸어요.
      </Text>
      <Text style={styles.instruction}>
        안전한 로그인을 위해 인증 코드를 입력해 주세요.
      </Text>

      <View style={styles.inputContainer}>
        <TextInput
          style={styles.input}
          placeholder="Token"
          value={enteredToken}
          onChangeText={setEnteredToken}
          keyboardType="numeric"
          maxLength={20}
        />
      </View>

      <TouchableOpacity style={styles.button} onPress={handleVerifyToken}>
        <Text style={styles.buttonText}>등록하기</Text>
      </TouchableOpacity>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    padding: 20,
    backgroundColor: "#fff",
  },
  title: {
    fontSize: 24,
    fontWeight: "bold",
    marginBottom: 20,
  },
  subtitle: {
    fontSize: 18,
    marginBottom: 10,
  },
  instruction: {
    fontSize: 16,
    marginBottom: 20,
  },
  inputContainer: {
    width: "100%",
    marginBottom: 20,
  },
  input: {
    height: 40,
    borderColor: "#ccc",
    borderBottomWidth: 1,
    paddingHorizontal: 10,
    fontSize: 16,
  },
  footer: {
    marginTop: 20,
    color: "#007BFF",
  },
  button: {
    backgroundColor: "#5E9740",
    padding: 15,
    alignItems: "center",
    borderRadius: 5,
  },
  buttonText: {
    color: "#fff",
    fontSize: 16,
  },
});

export default Email;
