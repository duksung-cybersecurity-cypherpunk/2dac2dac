package dac2dac.doctect.health_list.entity.constant.healthScreening.elderlyPhysicalFunctionTest;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ElderlyPhysicalFunctionTest {
    NORMAL("정상"),
    PHYSICAL_FUNCTION_DECLINE("신체기능저하");

    private final String ElderlyPhysicalFunctionTestType;

    public static ElderlyPhysicalFunctionTest fromString(String text) {
        for (ElderlyPhysicalFunctionTest b : ElderlyPhysicalFunctionTest.values()) {
            if (b.ElderlyPhysicalFunctionTestType.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
