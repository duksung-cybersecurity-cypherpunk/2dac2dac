package dac2dac.doctect.doctor.entity;

import dac2dac.doctect.noncontact_diag.entity.NoncontactPrescription;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String chart;
    private String imageUrl;
    private String className;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "noncontact_prescription_id")
    private NoncontactPrescription noncontactPrescription;

    @Builder
    public Medicine(String name, String chart, String imageUrl, String className) {
        this.name = name;
        this.chart = chart;
        this.imageUrl = imageUrl;
        this.className = className;
    }

    public void setNoncontactPrescription(NoncontactPrescription noncontactPrescription) {
        this.noncontactPrescription = noncontactPrescription;
    }
}
