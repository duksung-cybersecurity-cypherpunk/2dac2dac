package dac2dac.doctect.bootpay.entity.constant;

import dac2dac.doctect.health_list.entity.constant.DiagType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentType {
    CREDIT_CARD("신용카드"),
    KAKAOPAY("카카오페이"),
    NAVERPAY("네이버페이");

    private final String paymentTypeName;

    public static PaymentType fromString(String text) {
        for (PaymentType b : PaymentType.values()) {
            if (b.paymentTypeName.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
