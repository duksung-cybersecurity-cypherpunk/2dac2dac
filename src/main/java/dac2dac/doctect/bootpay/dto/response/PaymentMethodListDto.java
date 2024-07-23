package dac2dac.doctect.bootpay.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class PaymentMethodListDto {

    private Integer cnt;
    private List<PaymentMethodItem> PaymentMethodList = new ArrayList<>();

    @Builder
    public PaymentMethodListDto(Integer cnt, List<PaymentMethodItem> paymentMethodList) {
        this.cnt = cnt;
        PaymentMethodList = paymentMethodList;
    }
}
