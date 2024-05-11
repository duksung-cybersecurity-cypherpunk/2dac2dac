package dac2dac.doctect.health_list.entity.constant.healthScreening.hepB;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum HepBSurfaceAntibody {
    STANDARD("일반"),
    PRECISION("정밀");

    private final String DiagTypeName;
}
