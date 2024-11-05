package dac2dac.doctect.doctor.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    @Builder
    public Medicine(String name, String chart, String imageUrl, String className) {
        this.name = name;
        this.chart = chart;
        this.imageUrl = imageUrl;
        this.className = className;
    }
}
