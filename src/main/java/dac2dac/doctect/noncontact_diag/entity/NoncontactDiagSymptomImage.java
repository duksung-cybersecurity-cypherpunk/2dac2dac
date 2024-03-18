package dac2dac.doctect.noncontact_diag.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoncontactDiagSymptomImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "noncontact_diag_id")
    private NoncontactDiag noncontactDiag;

    private String symptom_image1;
    private String symptom_image2;
    private String symptom_image3;
    private String symptom_image4;
    private String symptom_image5;

}
