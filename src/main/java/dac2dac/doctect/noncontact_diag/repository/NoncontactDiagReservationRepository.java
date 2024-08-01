package dac2dac.doctect.noncontact_diag.repository;

import dac2dac.doctect.noncontact_diag.entity.NoncontactDiagReservation;
import dac2dac.doctect.noncontact_diag.entity.constant.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoncontactDiagReservationRepository extends JpaRepository<NoncontactDiagReservation, Long> {

    List<NoncontactDiagReservation> findByUserIdAndStatus(Long userId, ReservationStatus status);
}