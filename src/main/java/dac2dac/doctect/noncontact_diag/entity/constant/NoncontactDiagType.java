package dac2dac.doctect.noncontact_diag.entity.constant;

import dac2dac.doctect.bootpay.entity.constant.PaymentType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NoncontactDiagType {
    VOICE("음성진료"),
    VIDEO("화상진료");

    private final String NoncontactDiagTypeName;

    public static NoncontactDiagType fromString(String text) {
        for (NoncontactDiagType b : NoncontactDiagType.values()) {
            if (b.NoncontactDiagTypeName.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
