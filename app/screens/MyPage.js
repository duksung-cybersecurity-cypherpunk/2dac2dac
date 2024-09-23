import React from "react";
import { View, Text, StyleSheet, TouchableOpacity, Image } from "react-native";
import { useNavigation } from "@react-navigation/native";

export default function MyPage() {
  const navigation = useNavigation();
  const handleBlockPress = (id) => {
      navigation.navigate("PatientInfoStack", { id });
  };

  return (
    <View style={styles.container}>
      <TouchableOpacity
        style={styles.blocks}
        onPress={() => handleBlockPress(1)}
        activeOpacity={0.7}
      >
        <Text>QR 스캔</Text>
      </TouchableOpacity>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
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
