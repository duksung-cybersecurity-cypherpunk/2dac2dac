package dac2dac.doctect.doctor.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReservationItem {

    private Long userId;
    private Long reservationId;
    private LocalDateTime signupDate;
    private String patientName;
    private LocalDateTime reservationDate;

    @Builder
    public ReservationItem(Long userId, Long reservationId, LocalDateTime signupDate, String patientName, LocalDateTime reservationDate) {
        this.userId = userId;
        this.reservationId = reservationId;
        this.signupDate = signupDate;
        this.patientName = patientName;
        this.reservationDate = reservationDate;
    }
}
