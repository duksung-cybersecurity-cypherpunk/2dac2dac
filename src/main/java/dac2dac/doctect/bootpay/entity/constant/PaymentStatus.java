package dac2dac.doctect.bootpay.entity.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentStatus {

    COMPLETE("결제완료"),
    CANCLE("결제취소");

    private final String paymentStatus;
}
