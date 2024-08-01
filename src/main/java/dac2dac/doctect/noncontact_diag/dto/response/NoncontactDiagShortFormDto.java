package dac2dac.doctect.noncontact_diag.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class NoncontactDiagShortFormDto {

    private LocalDateTime signupDate;
    private LocalDateTime reservationDate;
    private String department;
    private String diagType;

    @Builder
    public NoncontactDiagShortFormDto(LocalDateTime signupDate, LocalDateTime reservationDate, String department, String diagType) {
        this.signupDate = signupDate;
        this.reservationDate = reservationDate;
        this.department = department;
        this.diagType = diagType;
    }
}
