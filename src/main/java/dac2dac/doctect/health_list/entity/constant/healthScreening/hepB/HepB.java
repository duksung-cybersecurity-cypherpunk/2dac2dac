package dac2dac.doctect.health_list.entity.constant.healthScreening.hepB;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum HepB {
    ANTIBODY_POSITIVE("항체있음"),
    ANTIBODY_NEGATIVE("항체없음"),
    HEPATITIS_B_SUSPECTED("B형 간염 보유자 의심"),
    JUDGEMENT_PENDING("판정보류");

    private final String DiagTypeName;
}
