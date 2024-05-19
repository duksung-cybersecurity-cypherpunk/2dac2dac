package dac2dac.doctect.health_list.entity.constant.healthScreening.hepB;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum HepBSurfaceAntigen {
    STANDARD("일반"),
    PRECISION("정밀");

    private final String HepBSurfaceAntigenType;

    public static HepBSurfaceAntigen fromString(String text) {
        for (HepBSurfaceAntigen b : HepBSurfaceAntigen.values()) {
            if (b.HepBSurfaceAntigenType.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
