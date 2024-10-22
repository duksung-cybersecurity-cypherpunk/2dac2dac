package dac2dac.doctect.user.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserLoginRequestDto {

    @NotNull(message = "username는 필수입니다.")
    private String username;

    @NotNull(message = "password는 필수입니다.")
    private String password;
}
