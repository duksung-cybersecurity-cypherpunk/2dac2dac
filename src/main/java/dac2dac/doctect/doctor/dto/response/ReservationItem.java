package dac2dac.doctect.doctor.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReservationItem {

    private Long reservationId;
    private LocalDateTime signupDate;
    private String patientName;
    private LocalDateTime reservationDate;

    @Builder
    public ReservationItem(Long reservationId, LocalDateTime signupDate, String patientName, LocalDateTime reservationDate) {
        this.reservationId = reservationId;
        this.signupDate = signupDate;
        this.patientName = patientName;
        this.reservationDate = reservationDate;
    }
}
