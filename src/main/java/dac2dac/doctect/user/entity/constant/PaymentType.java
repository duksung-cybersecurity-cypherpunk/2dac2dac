package dac2dac.doctect.user.entity.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentType {
    CREDIT_CARD("신용카드"),
    KAKAOPAY("카카오페이"),
    NAVERPAY("네이버페이");

    private final String paymentTypeName;
}
