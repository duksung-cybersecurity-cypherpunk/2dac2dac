package dac2dac.doctect.doctor.service;

import dac2dac.doctect.doctor.dto.response.AcceptedReservationItemList;
import dac2dac.doctect.doctor.dto.response.RequestReservationItemList;
import dac2dac.doctect.doctor.dto.response.ReservationItem;
import dac2dac.doctect.doctor.dto.response.ReservationListDto;
import dac2dac.doctect.noncontact_diag.entity.NoncontactDiagReservation;
import dac2dac.doctect.noncontact_diag.entity.constant.ReservationStatus;
import dac2dac.doctect.noncontact_diag.repository.NoncontactDiagReservationRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DoctorService {

    private final NoncontactDiagReservationRepository noncontactDiagReservationRepository;

    public ReservationListDto getReservations(Long doctorId, String reservationDate) {
        // 날짜에 해당하면서 doctor id에 해당하는 예약 조회
        LocalDate localReservationDate = LocalDate.parse(reservationDate);
        List<NoncontactDiagReservation> noncontactDiagReservationList = noncontactDiagReservationRepository.findByDoctorIdAndReservationDate(doctorId, localReservationDate);

        // 요청된 예약: SIGN_UP 상태인 예약 필터링
        List<NoncontactDiagReservation> requestReservations = noncontactDiagReservationList.stream()
            .filter(reservation -> ReservationStatus.SIGN_UP.equals(reservation.getStatus()))
            .collect(Collectors.toList());

        List<ReservationItem> requestReservationItems = requestReservations.stream()
            .map(r -> ReservationItem.builder()
                .reservationId(r.getId())
                .signupDate(r.getCreateDate())
                .patientName(r.getUser().getUsername())
                .reservationDate(LocalDateTime.of(r.getReservationDate(), r.getReservationTime()))
                .build())
            .sorted(Comparator.comparing(ReservationItem::getSignupDate))
            .collect(Collectors.toList());

        RequestReservationItemList requestReservationItemList = RequestReservationItemList.builder()
            .totalCnt(requestReservationItems.size())
            .requestReservationItemList(requestReservationItems)
            .build();

        // 수락된 예약: COMPLETE 상태인 예약 필터링
        List<NoncontactDiagReservation> acceptedReservations = noncontactDiagReservationList.stream()
            .filter(reservation -> ReservationStatus.COMPLETE.equals(reservation.getStatus()))
            .collect(Collectors.toList());

        List<ReservationItem> acceptedReservationItems = acceptedReservations.stream()
            .map(r -> ReservationItem.builder()
                .reservationId(r.getId())
                .signupDate(r.getCreateDate())
                .patientName(r.getUser().getUsername())
                .reservationDate(LocalDateTime.of(r.getReservationDate(), r.getReservationTime()))
                .build())
            .sorted(Comparator.comparing(ReservationItem::getReservationDate))
            .collect(Collectors.toList());

        AcceptedReservationItemList acceptedReservationItemList = AcceptedReservationItemList.builder()
            .totalCnt(acceptedReservationItems.size())
            .acceptedReservationItemList(acceptedReservationItems)
            .build();

        return ReservationListDto.builder()
            .requestReservationList(requestReservationItemList)
            .acceptedReservationList(acceptedReservationItemList)
            .build();
    }
}
