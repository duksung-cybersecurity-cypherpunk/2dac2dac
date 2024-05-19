package dac2dac.doctect.health_list.entity.constant.healthScreening.depression;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Depression {
    NONE("우울증상이 없음"),
    MILD("가벼운 우울증상"),
    MODERATE_SUSPECTED("중간 정도 우울증 의심"),
    SEVERE_SUSPECTED("심한 우울증 의심");

    private final String DepressionType;

    public static Depression fromString(String text) {
        for (Depression b : Depression.values()) {
            if (b.DepressionType.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
