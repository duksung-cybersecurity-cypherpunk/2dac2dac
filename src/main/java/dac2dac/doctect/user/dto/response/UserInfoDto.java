package dac2dac.doctect.user.dto.response;

import dac2dac.doctect.user.entity.constant.Gender;
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
    private Gender gender;
    private String birthDate;

    @Builder
    public UserInfoDto(String username, String email, String phoneNumber, Integer paymentMethodCnt,Gender gender,String birthDate) {
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.paymentMethodCnt = paymentMethodCnt;
        this.gender = gender;
        this.birthDate = birthDate;
    }
}
