package dac2dac.doctect.user.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserInfoDto {

    private String username;
    private String email;
    private String phoneNumber;
    private Integer paymentMethodCnt;

    @Builder
    public UserInfoDto(String username, String email, String phoneNumber, Integer paymentMethodCnt) {
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.paymentMethodCnt = paymentMethodCnt;
    }
}
