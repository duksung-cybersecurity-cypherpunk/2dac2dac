package dac2dac.doctect.keypad.dto;

import dac2dac.doctect.keypad.entity.IntegrityId;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SecureKeypadAuthRequest {

    private IntegrityId integrityId;
    private String userInput;
}