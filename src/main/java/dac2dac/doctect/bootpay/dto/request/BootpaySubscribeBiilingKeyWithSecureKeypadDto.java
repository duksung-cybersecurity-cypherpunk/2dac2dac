package dac2dac.doctect.bootpay.dto.request;

import dac2dac.doctect.keypad.dto.SecureKeypadAuthRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BootpaySubscribeBiilingKeyWithSecureKeypadDto {

    @NotBlank(message = "카드 번호는 필수입니다.")
    private String cardNoPart1; // 첫 번째 4자리

    @NotBlank(message = "카드 번호는 필수입니다.")
    private String cardNoPart2; // 두 번째 4자리

    private SecureKeypadAuthRequest cardNoPart3; // 세 번째 4자리 (보안 키패드 적용)

    private SecureKeypadAuthRequest cardNoPart4; // 네 번째 4자리 (보안 키패드 적용)

    private SecureKeypadAuthRequest cardExpireDate;

    private SecureKeypadAuthRequest cardPw;

    @NotBlank(message = "생년 월일은 필수입니다.")
    private String cardIdentityNo;
}