package dac2dac.doctect.common.utils;

import dac2dac.doctect.user.entity.constant.Gender;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DeidentificationUtils {

    private DeidentificationUtils() {
        // 유틸리티 클래스 인스턴스화 방지
    }


    public static String getGenderCode(Gender gender) {
        return gender.toString().substring(0, 1);  // 성별 첫 글자만 추출 (예: "M" or "F")
    }

    public static String maskName(String name) {
        if (name == null || name.length() < 2) {
            return name;  // 이름이 너무 짧거나 null인 경우 그대로 반환
        }

        // 첫 글자만 남기고 나머지를 "OO"으로 마스킹
        String maskedName = name.substring(0, 1) + "OO";
        return maskedName;
    }

    // 생년월일을 나이대(10대, 20대 등)로 변환하는 메서드
    public static String convertBirthDateToAgeGroup(String birthDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
        LocalDate birthDateLocalDate = LocalDate.parse(birthDate, formatter);
        int birthYear = birthDateLocalDate.getYear();
        int currentYear = LocalDate.now().getYear();

        if ((birthYear % 100) > (currentYear % 100)) {
            birthYear -= 100;  // 1900년대 출생자
        }

        int age = currentYear - birthYear + 1;
        return calculateAgeGroup(age);
    }

    // 나이대(10대, 20대 등) 계산 메서드
    private static String calculateAgeGroup(int age) {
        if (age < 10) {
            return "연령대 미상";
        }
        int ageGroup = age / 10 * 10;  // 10 단위로 나이대 계산
        return ageGroup + "대";
    }

}
