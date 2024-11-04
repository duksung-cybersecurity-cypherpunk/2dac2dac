package dac2dac.doctect.agency.repository;

import dac2dac.doctect.agency.entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {
}
