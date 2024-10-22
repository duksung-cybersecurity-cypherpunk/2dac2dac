package dac2dac.doctect.user.dto.request;


import dac2dac.doctect.user.entity.constant.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserDTO {

    private String username;
    private String email;
    private String password;
    private String phoneNumber;
    private Gender gender;
    private String birthDate;
}

