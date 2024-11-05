package dac2dac.doctect.user.entity;

import dac2dac.doctect.common.entity.BaseEntity;
import dac2dac.doctect.user.entity.constant.Gender;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;


@Entity
@Getter
@Table(name = "\"user\"")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String phoneNumber;
    private String password;
    private String birthDate;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private Double longitude;
    private Double latitude;

    private boolean synced;
    private LocalDateTime lastSyncedDate;

    public void setLocation(Double longitude, Double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public static User registerUser(String username, String email, String password, String phoneNumber, String birthDate, Gender gender) {
        User user = new User();
        user.username = username;
        user.email = email;
        user.password = password;
        user.phoneNumber = phoneNumber;
        user.birthDate = birthDate;
        user.gender = gender;
        return user;
    }

    public void checkJWT(String id, String username, String email, String phoneNumber, String birthDate, Gender gender) {
        this.id = Long.parseLong(id);
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.gender = gender;
    }

    public void syncMydata() {
        this.synced = true;
        this.lastSyncedDate = LocalDateTime.now();
    }
}
