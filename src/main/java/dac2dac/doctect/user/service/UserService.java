package dac2dac.doctect.user.service;

import ch.qos.logback.core.boolex.Matcher;
import dac2dac.doctect.user.entity.User;
import dac2dac.doctect.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 회원가입
    @Transactional
    public User registerUser(String username,  String email, String password, String phoneNumber, String code) {
        // 이메일 중복 체크
        if (userRepository.findByUsername(username) != null) {
            throw new RuntimeException("username is already registered.");
        }
        // 사용자 등록 로직
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));  // 비밀번호 암호화
        user.setPhoneNumber(phoneNumber);
        user.setCode(code);
        user.setCreateDate(LocalDateTime.now()); // 생성
        user.setUpdateDate(LocalDateTime.now()); // 업데이트
        // 사용자 저장
        return userRepository.save(user);
    }

    // 인증 서비스 구축
    // 로그인 메서드
    public boolean authenticateUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        //System.out.println("나는 user 입니다. : " + user);
        if (user != null) {
            // 비밀번호 확인
            return passwordEncoder.matches(password, user.getPassword());
        }
        return false;
    }
}

