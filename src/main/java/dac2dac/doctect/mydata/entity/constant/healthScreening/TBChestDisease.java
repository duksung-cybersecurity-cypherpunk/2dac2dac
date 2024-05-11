package dac2dac.doctect.mydata.entity.constant.healthScreening;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TBChestDisease {
    NORMAL("정상"),
    LATENT_TB("비활동성 폐결핵"),
    DISEASE_SUSPECTED("질환의심");

    private final String DiagTypeName;
}
