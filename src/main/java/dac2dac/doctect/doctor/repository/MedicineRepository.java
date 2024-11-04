package dac2dac.doctect.doctor.repository;

import dac2dac.doctect.agency.entity.Hospital;
import dac2dac.doctect.doctor.entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {

    @Query(value =
            "SELECT * FROM Medicine WHERE name like :name%", nativeQuery = true)
    List<Medicine> findMedicineWithKeyword(@Param("name") String keyword);
}
