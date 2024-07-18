package dac2dac.doctect.bootpay.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BootpayBiilingKeyDto {

    private String cardNo;
    private String cardPw;
    private String cardIdentityNo;
    private String cardExpireYear;
    private String cardExpireMonth;
}
