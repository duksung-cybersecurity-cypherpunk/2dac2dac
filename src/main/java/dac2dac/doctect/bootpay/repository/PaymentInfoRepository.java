package dac2dac.doctect.bootpay.repository;

import dac2dac.doctect.bootpay.entity.PaymentInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentInfoRepository extends JpaRepository<PaymentInfo, Long> {
}
