package dac2dac.doctect.user.service;

import dac2dac.doctect.bootpay.repository.PaymentMethodRepository;
import dac2dac.doctect.common.constant.ErrorCode;
import dac2dac.doctect.common.error.exception.NotFoundException;
import dac2dac.doctect.doctor.entity.Doctor;
import dac2dac.doctect.noncontact_diag.entity.NoncontactDiagReservation;
import dac2dac.doctect.noncontact_diag.entity.constant.ReservationStatus;
import dac2dac.doctect.noncontact_diag.repository.NoncontactDiagReservationRepository;
import dac2dac.doctect.user.dto.response.UpcomingReservationDto;
import dac2dac.doctect.user.dto.response.UserInfoDto;
import dac2dac.doctect.user.entity.User;
import dac2dac.doctect.user.entity.constant.Gender;
import dac2dac.doctect.user.entity.constant.SocialType;
import dac2dac.doctect.user.jwt.JWTUtil;
import dac2dac.doctect.user.repository.UserRepository;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Autowired
    private NoncontactDiagReservationRepository noncontactDiagReservationRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTUtil jwtUtil;

    // 회원가입
    @Transactional
    public User registerUser(String username, String email, String password, String phoneNumber, String code, SocialType socialType, Gender gender, String birthDate) {
        // 이메일 중복 체크
        if (userRepository.findByUsername(username) != null) {
            throw new RuntimeException("Username is already registered.");
        }

        // 이메일 중복 체크
        if (userRepository.findByEmail(email) != null) {
            throw new RuntimeException("Email is already registered.");
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
        user.setSocialType(socialType);
        user.setBirthDate(birthDate);
        user.setGender(gender);
        // 사용자 저장
        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // 인증 서비스 구축
    // 로그인 메서드
    public boolean authenticateUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            // 비밀번호 확인
            return passwordEncoder.matches(password, user.getPassword());
        }
        return false;
    }

    public UserInfoDto getUserInfo(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        Integer paymentMethodCnt = paymentMethodRepository.countAllByUserId(userId);

        return UserInfoDto.builder()
            .username(user.getUsername())
            .email(user.getEmail())
            .phoneNumber(user.getPhoneNumber())
            .paymentMethodCnt(paymentMethodCnt)
            .build();
    }


    public String authenticateAndGenerateToken(String username, String password) {
        // 사용자 인증 로직
        boolean authenticate = authenticateUser(username, password);
        User user = userRepository.findByUsername(username);
        if (authenticate) {
            // DB에서 조회한 사용자 ID를 사용하여 JWT 생성
            return jwtUtil.createJwt(username, user.getId().toString(), user.getPhoneNumber(), user.getEmail(), "user",user.getBirthDate(),user.getGender() ,36000L); // 1시간 유효한 토큰 생성
        } else {
            throw new RuntimeException("Invalid username or password");
        }
    }


    public UpcomingReservationDto getUpcomingReservation(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        NoncontactDiagReservation reservation = noncontactDiagReservationRepository.findNearestReservationByUserIdAndStatus(userId, ReservationStatus.COMPLETE.name())
            .orElseThrow(() -> new NotFoundException(ErrorCode.NONCONTACT_DIAGNOSIS_RESERVATION_NOT_FOUND));

        Doctor doctor = reservation.getDoctor();

        return UpcomingReservationDto.builder()
            .reservationId(reservation.getId())
            .reservationDate(LocalDateTime.of(reservation.getReservationDate(), reservation.getReservationTime()))
            .doctorName(doctor.getName())
            .department(doctor.getDepartment().getDepartmentName())
            .diagType(reservation.getDiagType().getNoncontactDiagTypeName())
            .build();
    }
}

