package dac2dac.doctect.bootpay.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaymentMethodItem {

    private Long id;
    private String cardCompany;
    private String cardLast4Digits;
    private String paymentType;
    private String billingKey;

    @Builder
    public PaymentMethodItem(Long id, String cardCompany, String cardLast4Digits, String paymentType, String billingKey) {
        this.id = id;
        this.cardCompany = cardCompany;
        this.cardLast4Digits = cardLast4Digits;
        this.paymentType = paymentType;
        this.billingKey = billingKey;
    }
}
