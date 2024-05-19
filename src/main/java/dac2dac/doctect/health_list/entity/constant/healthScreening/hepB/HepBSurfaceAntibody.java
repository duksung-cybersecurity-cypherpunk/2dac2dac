package dac2dac.doctect.health_list.entity.constant.healthScreening.hepB;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum HepBSurfaceAntibody {
    STANDARD("일반"),
    PRECISION("정밀");

    private final String HepBSurfaceAntibodyType;

    public static HepBSurfaceAntibody fromString(String text) {
        for (HepBSurfaceAntibody b : HepBSurfaceAntibody.values()) {
            if (b.HepBSurfaceAntibodyType.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
