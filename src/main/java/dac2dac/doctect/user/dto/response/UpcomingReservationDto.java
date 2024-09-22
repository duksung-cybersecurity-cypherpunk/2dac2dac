package dac2dac.doctect.user.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpcomingReservationDto {

    private Long reservationId;
    private LocalDateTime reservationDate;
    private String doctorName;
    private String department;
    private String paymentMethod;

    @Builder
    public UpcomingReservationDto(Long reservationId, LocalDateTime reservationDate, String doctorName, String department, String paymentMethod) {
        this.reservationId = reservationId;
        this.reservationDate = reservationDate;
        this.doctorName = doctorName;
        this.department = department;
        this.paymentMethod = paymentMethod;
    }
}
