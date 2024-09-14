package dac2dac.doctect.user.dto;


import dac2dac.doctect.user.entity.constant.SocialType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String password;
    private String phoneNumber;
    private String code;
    private SocialType socialType;
}

