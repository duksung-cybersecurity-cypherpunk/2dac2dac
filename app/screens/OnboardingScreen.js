import React, { useState } from "react";
import { View, Text, StyleSheet, Image, TouchableOpacity } from "react-native";

// 예시 이미지 경로
const images = [
  require("../../assets/images/onboarding/onBoarding1.png"),
  require("../../assets/images/onboarding/onBoarding2.png"),
  require("../../assets/images/onboarding/onBoarding3.png"),
];

const OnboardingScreen = ({ navigation }) => {
  const [pageIndex, setPageIndex] = useState(0);

  const handleNext = () => {
    if (pageIndex < images.length - 1) {
      setPageIndex(pageIndex + 1);
    } else if (pageIndex == images.length - 1) {
      navigation.replace("Login");
    } else {
      navigation.replace("BottomTabNavigator"); // 메인 화면으로 이동
    }
  };

  const renderProgressDots = () => {
    return images.map((_, index) => (
      <Image
        key={index}
        source={
          index <= pageIndex
            ? require("../../assets/images/icon/EllipseFilled.png")
            : require("../../assets/images/icon/EllipseEmpty.png")
        }
        style={styles.dot}
      />
    ));
  };

  return (
    <View style={styles.container}>
      <Image source={images[pageIndex]} style={styles.image} />
      <View style={styles.dotsContainer}>{renderProgressDots()}</View>
      <TouchableOpacity style={styles.button} onPress={handleNext}>
        <Text style={styles.buttonText}>다음으로 넘어가기</Text>
      </TouchableOpacity>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: "center",
    justifyContent: "center",
    padding: 20,
    backgroundColor: "white",
  },
  image: {
    height: "70%",
    marginBottom: 20,
    resizeMode: "contain", // 이미지 크기가 컨테이너에 맞게 조정됩니다.
  },
  dotsContainer: {
    flexDirection: "row",
    marginBottom: 20,
  },
  dot: {
    width: 10,
    height: 10,
    marginHorizontal: 5,
  },
  button: {
    backgroundColor: "#5E9740",
    padding: 16,
    borderRadius: 8,
    alignItems: "center",
    marginTop: 16,
    width: "80%",
  },
  buttonText: {
    color: "#fff",
    fontSize: 18,
    fontWeight: "bold",
  },
});

export default OnboardingScreen;
