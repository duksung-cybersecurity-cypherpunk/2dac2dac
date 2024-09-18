package dac2dac.doctect.noncontact_diag.repository;

import dac2dac.doctect.noncontact_diag.entity.NoncontactDiagReservation;
import dac2dac.doctect.noncontact_diag.entity.constant.ReservationStatus;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoncontactDiagReservationRepository extends JpaRepository<NoncontactDiagReservation, Long> {

    List<NoncontactDiagReservation> findByUserIdAndStatus(Long userId, ReservationStatus status);

    List<NoncontactDiagReservation> findByDoctorIdAndReservationDate(Long doctorId, LocalDate date);
}