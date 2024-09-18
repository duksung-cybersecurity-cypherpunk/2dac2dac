package dac2dac.doctect.doctor.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PatientInfoDto {

    private Long userId;
    private String userName;
    private String age;
    private String gender;
    private String phoneNumber;

    @Builder
    public PatientInfoDto(Long userId, String userName, String age, String gender, String phoneNumber) {
        this.userId = userId;
        this.userName = userName;
        this.age = age;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
    }
}
