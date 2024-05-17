package dac2dac.doctect.health_list.repository;

import dac2dac.doctect.health_list.entity.PrescriptionDrug;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrescriptionDrugRepository extends JpaRepository<PrescriptionDrug, Long> {
    List<PrescriptionDrug> findByPrescriptionId(Long prescriptionId);
}
