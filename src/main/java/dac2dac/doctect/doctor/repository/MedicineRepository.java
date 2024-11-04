package dac2dac.doctect.doctor.repository;

import dac2dac.doctect.doctor.entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {
}
