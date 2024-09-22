package dac2dac.doctect.doctor.dto.response;

import dac2dac.doctect.noncontact_diag.dto.response.NoncontactDiagFormInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestReservationFormDto {

    ReservationItem reservationItem;
    NoncontactDiagFormInfo noncontactDiagFormInfo;

    @Builder
    public RequestReservationFormDto(ReservationItem reservationItem, NoncontactDiagFormInfo noncontactDiagFormInfo) {
        this.reservationItem = reservationItem;
        this.noncontactDiagFormInfo = noncontactDiagFormInfo;
    }
}
