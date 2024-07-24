package dac2dac.doctect.bootpay.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BootpayPayDto {

    private String orderName;
    private String billingKey;
    private Integer price;
}
