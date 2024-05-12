package dac2dac.doctect.health_list.entity.constant.healthScreening.boneDensityTest;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BoneDensityTest {
    NORMAL("정상"),
    OSTEOPENIA("골감소증"),
    OSTEOPOROSIS("골다공증");

    private final String BoneDensityType;

    public static BoneDensityTest fromString(String text) {
        for (BoneDensityTest b : BoneDensityTest.values()) {
            if (b.BoneDensityType.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
