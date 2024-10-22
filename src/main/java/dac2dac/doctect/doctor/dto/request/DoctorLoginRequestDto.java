package dac2dac.doctect.doctor.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DoctorLoginRequestDto {

    private String username;
    private String password;
}
