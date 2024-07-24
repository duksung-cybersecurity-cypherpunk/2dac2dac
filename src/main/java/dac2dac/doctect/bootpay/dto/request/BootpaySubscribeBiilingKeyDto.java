package dac2dac.doctect.bootpay.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BootpaySubscribeBiilingKeyDto {

    @NotBlank(message = "카드 번호는 필수입니다.")
    @Pattern(regexp = "^[0-9]{16}$", message = "카드 번호는 16자리 숫자여야 합니다.")
    private String cardNo;

    @NotBlank(message = "카드 비밀번호는 필수입니다.")
    @Pattern(regexp = "^[0-9]{2}$", message = "카드 비밀번호 앞자리는 2자리여야 합니다.")
    private String cardPw;

    @NotBlank(message = "신분 번호는 필수입니다.")
    @Pattern(regexp = "^[0-9]{6}$", message = "생년월일은 6자리 숫자여야 합니다.")
    private String cardIdentityNo;

    @NotBlank(message = "만료 연도는 필수입니다.")
    @Pattern(regexp = "^[0-9]{2}$", message = "만료 연도는 2자리 숫자여야 합니다.")
    private String cardExpireYear;

    @NotBlank(message = "만료 월은 필수입니다.")
    @Pattern(regexp = "^(0[1-9]|1[0-2])$", message = "만료 월은 01에서 12 사이의 숫자여야 합니다.")
    private String cardExpireMonth;
}
