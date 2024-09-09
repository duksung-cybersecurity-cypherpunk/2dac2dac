package dac2dac.doctect.keypad.dto;

import dac2dac.doctect.keypad.entity.IntegrityId;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SecureKeypadAuthRequest {

    @NotNull(message = "IntegrityId는 필수입니다.")
    private IntegrityId integrityId;

    @NotBlank(message = "사용자 입력은 필수입니다.")
    private String userInput;
}