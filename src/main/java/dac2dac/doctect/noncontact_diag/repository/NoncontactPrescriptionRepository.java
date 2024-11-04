package dac2dac.doctect.noncontact_diag.repository;

import dac2dac.doctect.noncontact_diag.entity.NoncontactPrescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoncontactPrescriptionRepository extends JpaRepository<NoncontactPrescription, Long> {
    List<NoncontactPrescription> findByUserId(Long userId);

    Optional<NoncontactPrescription> findByNoncontactDiagId(Long noncontactDiagId);
}
