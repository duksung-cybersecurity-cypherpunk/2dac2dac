package dac2dac.doctect.health_list.entity.constant.healthScreening.cognitiveDysfunction;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CognitiveDysfunction {
    NO_SPECIAL_FINDINGS("특이소견 없음"),
    COGNITIVE_IMPAIRMENT_SUSPECTED("인지기능 저하 의심");

    private final String DiagTypeName;
}
