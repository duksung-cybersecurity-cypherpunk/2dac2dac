package dac2dac.doctect.health_list.dto.request;

import dac2dac.doctect.keypad.dto.SecureKeypadAuthRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserAuthenticationDto {

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;

    @NotBlank(message = "주민등록번호는 필수 입력 값입니다.")
    @Pattern(regexp = "\\d{6}", message = "주민등록번호 형식이 유효하지 않습니다. 예시: 123456")
    private String pinFront;

    private SecureKeypadAuthRequest pinBack;
}
