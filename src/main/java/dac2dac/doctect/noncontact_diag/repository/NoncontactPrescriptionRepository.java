package dac2dac.doctect.noncontact_diag.repository;

import dac2dac.doctect.noncontact_diag.entity.NoncontactPrescription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoncontactPrescriptionRepository extends JpaRepository<NoncontactPrescription, Long> {
}
