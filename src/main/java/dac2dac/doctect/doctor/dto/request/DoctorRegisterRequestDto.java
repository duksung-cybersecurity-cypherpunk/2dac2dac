package dac2dac.doctect.doctor.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DoctorRegisterRequestDto {

    private Long hospitalId;
    private Long departmentId;
    private String name;
    private String email;
    private String password;
    private String oneLiner;
}
