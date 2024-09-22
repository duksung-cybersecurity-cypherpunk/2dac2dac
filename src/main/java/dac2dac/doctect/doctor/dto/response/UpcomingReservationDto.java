package dac2dac.doctect.doctor.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpcomingReservationDto {

    private Long reservationId;
    private LocalDateTime reservationDate;
    private String patientName;

    @Builder
    public UpcomingReservationDto(Long reservationId, LocalDateTime reservationDate, String patientName) {
        this.reservationId = reservationId;
        this.reservationDate = reservationDate;
        this.patientName = patientName;
    }
}
