package dac2dac.doctect.health_list.repository;

import dac2dac.doctect.health_list.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

    void deleteByUserId(Long userId);

    List<Prescription> findByUserId(Long userId);
}
