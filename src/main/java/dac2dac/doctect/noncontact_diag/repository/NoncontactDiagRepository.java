package dac2dac.doctect.noncontact_diag.repository;

import dac2dac.doctect.noncontact_diag.entity.NoncontactDiag;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoncontactDiagRepository extends JpaRepository<NoncontactDiag, Long> {

    List<NoncontactDiag> findByUserId(Long userId);

    List<NoncontactDiag> findByDoctorId(Long doctorId);
}
