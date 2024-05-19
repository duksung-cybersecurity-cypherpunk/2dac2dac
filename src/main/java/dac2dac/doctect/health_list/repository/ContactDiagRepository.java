package dac2dac.doctect.health_list.repository;

import dac2dac.doctect.health_list.entity.ContactDiag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContactDiagRepository extends JpaRepository<ContactDiag, Long> {

    void deleteByUserId(Long userId);

    @Query("select cd from ContactDiag cd where cd.user.id =:userId and cd.agencyName =:agencyName")
    ContactDiag findByUserIdAndDiagId(Long userId, String agencyName);

    List<ContactDiag> findByUserId(Long userId);
}
