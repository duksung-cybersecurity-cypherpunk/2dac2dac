package dac2dac.doctect.health_list.entity.constant.healthScreening;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UrinaryProtein {
    NORMAL("정상"),
    BORDERLINE("경계"),
    PROTEINURIA_SUSPECTED("단백뇨 의심");

    private final String UrinaryProteinType;

    public static UrinaryProtein fromString(String text) {
        for (UrinaryProtein b : UrinaryProtein.values()) {
            if (b.UrinaryProteinType.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
