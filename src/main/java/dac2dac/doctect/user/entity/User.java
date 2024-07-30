package dac2dac.doctect.user.entity;

import dac2dac.doctect.common.entity.BaseEntity;
import dac2dac.doctect.user.entity.constant.SocialType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Getter
@Table(name = "\"user\"")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    private String username;
    private String email;
    private String phoneNumber;
    private String pin;

    private String code; // 구글, 카카오의 로그아웃, 탈퇴를 위한 코드

    private Double longitude;
    private Double latitude;

    private String password;

    public void setLocation(Double longitude, Double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    // 사용자 이름 설정
    public void setUsername(String username) {
        this.username = username;
    }

    // 암호 설정 (암호화 필요)
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password; // 비밀번호를 반환하는 메서드
    }

    public String getName() {
        return username; // 비밀번호를 반환하는 메서드
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setCode(String code) {
        this.code=code;
    }
}
