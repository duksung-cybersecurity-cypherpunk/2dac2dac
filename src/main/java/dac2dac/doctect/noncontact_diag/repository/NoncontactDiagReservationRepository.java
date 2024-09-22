package dac2dac.doctect.noncontact_diag.repository;

import dac2dac.doctect.noncontact_diag.entity.NoncontactDiagReservation;
import dac2dac.doctect.noncontact_diag.entity.constant.ReservationStatus;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NoncontactDiagReservationRepository extends JpaRepository<NoncontactDiagReservation, Long> {

    List<NoncontactDiagReservation> findByUserIdAndStatus(Long userId, ReservationStatus status);

    Optional<List<NoncontactDiagReservation>> findByDoctorIdAndReservationDate(Long doctorId, LocalDate date);

    /**
     * 현재 시간 기준, 가장 가까운 진료에 해당하는 진료 엔티티를 조회한다.
     **/
    @Query(value = "SELECT * FROM noncontact_diag_reservation " +
        "WHERE user_id = :userId " +
        "AND TIMESTAMP(reservation_date, reservation_time) >= NOW() " +
        "AND status = :status " +
        "ORDER BY reservation_date ASC, reservation_time ASC " +
        "LIMIT 1", nativeQuery = true)
    Optional<NoncontactDiagReservation> findNearestReservationByUserIdAndStatus(@Param("userId") Long userId, @Param("status") String status);
}