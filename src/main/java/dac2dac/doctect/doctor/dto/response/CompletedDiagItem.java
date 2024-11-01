package dac2dac.doctect.doctor.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CompletedDiagItem {

    private Long noncontactDiagId;
    private LocalDateTime reservationDate;
    private String patientName;

    @Builder
    public CompletedDiagItem(Long noncontactDiagId, LocalDateTime reservationDate, String patientName) {
        this.noncontactDiagId = noncontactDiagId;
        this.reservationDate = reservationDate;
        this.patientName = patientName;
    }
}
