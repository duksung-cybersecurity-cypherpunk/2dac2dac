package dac2dac.doctect.mydata.entity.constant.healthScreening.elderlyPhysicalFunctionTest;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ElderlyPhysicalFunctionTest {
    NORMAL("정상"),
    PHYSICAL_FUNCTION_DECLINE("신체기능저하");

    private final String DiagTypeName;
}
