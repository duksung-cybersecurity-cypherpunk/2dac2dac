package dac2dac.doctect.health_list.repository;

import dac2dac.doctect.health_list.entity.ContactDiag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactDiagRepository extends JpaRepository<ContactDiag, Long> {

    void deleteByUserId(Long userId);
}
