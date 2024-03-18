package dac2dac.doctect.user.entity.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentType {
    CARD("card"),
    KAKAOPAY("kakaopay"),
    NAVERPAY("naverpay");

    private final String paymentTypeName;
}
