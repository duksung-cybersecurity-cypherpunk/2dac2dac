package dac2dac.doctect.bootpay.entity;

import dac2dac.doctect.user.entity.User;
import dac2dac.doctect.bootpay.entity.constant.ActiveStatus;
import dac2dac.doctect.bootpay.entity.constant.PaymentType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @Column(name = "card_last_4_digits")
    private String cardLast4Digits;
    private String cardCompany;
    private String billingKey;

    @Enumerated(EnumType.STRING)
    private ActiveStatus status;

    @Builder
    public PaymentMethod(User user, PaymentType paymentType, String cardLast4Digits, String cardCompany, String billingKey, ActiveStatus status) {
        this.user = user;
        this.paymentType = paymentType;
        this.cardLast4Digits = cardLast4Digits;
        this.cardCompany = cardCompany;
        this.billingKey = billingKey;
        this.status = status;
    }

    public void deletePayment() {
        this.status = ActiveStatus.INACTIVE;
    }
}
