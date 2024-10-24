import React, { useState } from "react";
import {
  View,
  Text,
  TextInput,
  TouchableOpacity,
  StyleSheet,
  Alert,
  SafeAreaView,
  Image,
} from "react-native";

export default function Login({ navigation }) {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  // Email verification navigation
  const emailVerify = () => {
    navigation.navigate("Email", { username, password }); // Navigate to the Email screen
  };

  // Handle user signup
  const goToNewLogin = () => {
    navigation.navigate("signUpForm1");
  };

  return (
    <View style={styles.container}>
      <Image
        source={require("../../../assets/images/icon/Mainicon.png")}
        style={styles.image}
      />

      <TextInput
        style={styles.input}
        placeholder="Username"
        value={username}
        onChangeText={setUsername}
      />
      <TextInput
        style={styles.input}
        placeholder="Password"
        value={password}
        onChangeText={setPassword}
        secureTextEntry
      />

      <TouchableOpacity style={styles.button} onPress={emailVerify}>
        <Text style={styles.buttonText}>로그인</Text>
      </TouchableOpacity>

      <SafeAreaView>
        <TouchableOpacity style={styles.signUpButton} onPress={goToNewLogin}>
          <Text style={styles.signUpButtonText}>회원가입</Text>
        </TouchableOpacity>
      </SafeAreaView>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
    backgroundColor: "#fff",
    padding: 20,
  },
  image: {
    width: 100,
    height: 150,
    alignSelf: "center",
    marginBottom: 40,
  },
  input: {
    height: 40,
    borderColor: "#ccc",
    borderBottomWidth: 1,
    marginBottom: 15,
    paddingHorizontal: 10,
  },
  button: {
    backgroundColor: "#5E9740",
    paddingVertical: 15,
    borderRadius: 5,
  },
  buttonText: {
    color: "#fff",
    textAlign: "center",
    fontWeight: "bold",
  },
  signUpButton: {
    backgroundColor: "#fff",
    paddingVertical: 15,
    borderRadius: 5,
    marginTop: 10,
    borderWidth: 2,
    borderColor: "#5E9740",
  },
  signUpButtonText: {
    color: "#5E9740",
    textAlign: "center",
    fontWeight: "bold",
  },
});
