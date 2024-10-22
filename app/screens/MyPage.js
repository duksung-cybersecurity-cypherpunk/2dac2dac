import React, { useEffect, useState } from "react";
import {
  View,
  Text,
  StyleSheet,
  TouchableOpacity,
  ScrollView,
} from "react-native";
import AsyncStorage from "@react-native-async-storage/async-storage";
import { useNavigation } from "@react-navigation/native";

export default function MyPage() {
  const [userInfo, setUserInfo] = useState(null);
  const navigation = useNavigation();

  useEffect(() => {
    const loadUserData = async () => {
      const storedUserInfo = await AsyncStorage.getItem("userInfo");

      if (storedUserInfo) {
        setUserInfo(JSON.parse(storedUserInfo));
      }
    };
    loadUserData();
  }, []);

  return (
    <ScrollView contentContainerStyle={styles.container}>
      {userInfo ? (
        <>
          <View style={styles.section}>
            <Text style={styles.sectionTitle}>기본 정보</Text>
            <View style={styles.infoRow}>
              <Text style={styles.label}>Id</Text>
              <Text style={styles.value}>{userInfo.username}</Text>
            </View>

            <View style={styles.infoRow}>
              <Text style={styles.label}>한줄소개</Text>
              <Text style={styles.value}>{userInfo.oneLiner}</Text>
            </View>
          </View>

          <View style={styles.section}>
            <View style={styles.infoRow}>
              <Text style={styles.label}>이메일</Text>
              <Text style={styles.value}>{userInfo.email}</Text>
            </View>
          </View>
        </>
      ) : (
        <Text>Loading user data...</Text>
      )}
    </ScrollView>
  );
}

const styles = StyleSheet.create({
  container: {
    flexGrow: 1,
    backgroundColor: "#fff",
    padding: 20,
  },
  section: {
    marginBottom: 30,
  },
  sectionTitle: {
    fontSize: 18,
    fontWeight: "bold",
    marginBottom: 10,
  },
  infoRow: {
    flexDirection: "row",
    justifyContent: "space-between",
    alignItems: "center",
    paddingVertical: 10,
    borderBottomWidth: 1,
    borderBottomColor: "#eee",
  },
  label: {
    fontSize: 16,
    color: "#333",
  },
  value: {
    fontSize: 16,
    color: "#333",
  },
  arrow: {
    fontSize: 18,
    color: "#333",
  },
  blocks: {
    width: "46%",
    aspectRatio: 1 / 1,
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: "#EBF2EA",
    borderRadius: 6,
  },
});
