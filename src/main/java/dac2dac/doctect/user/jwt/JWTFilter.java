package dac2dac.doctect.user.jwt;

import dac2dac.doctect.doctor.dto.CustomDoctorDetails;
import dac2dac.doctect.doctor.entity.Doctor;
import dac2dac.doctect.user.dto.CustomUserDetails;
import dac2dac.doctect.user.entity.User;
import dac2dac.doctect.user.entity.constant.Gender;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

// JWT 검증 필터
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private static final Logger logger = LoggerFactory.getLogger(JWTFilter.class);

    public JWTFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            // Request에서 Authorization 헤더를 찾음
            String authorization = request.getHeader("Authorization");

            if (authorization == null || !authorization.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            logger.debug("Bearer token found in Authorization header.");

            // Bearer 부분 제거 후 순수 토큰만 획득
            String token = authorization.substring(7); // Bearer 다음부터 토큰

            // 토큰 소멸 시간 검증
            if (jwtUtil.isExpired(token)) {
                filterChain.doFilter(request, response);
                return;
            }

            logger.info("Token is valid and not expired.");

            // 토큰에서 사용자 정보 획득
            String username = jwtUtil.getUsername(token);
            String id = jwtUtil.getId(token);
            String phoneNumber = jwtUtil.getPhoneNumber(token);
            String email = jwtUtil.getEmail(token);
            String userType = jwtUtil.getUserType(token); // 사용자 타입 추가
            String oneLiner = jwtUtil.getOneLiner(token);
            Gender gender = jwtUtil.getGender(token);
            String birthDate = jwtUtil.getBirthdDate(token);

            // 사용자 타입에 따라 적절한 객체 생성
            if ("doctor".equals(userType)) {
                Doctor doctorEntity = new Doctor();
                doctorEntity.setName(username);
                doctorEntity.setEmail(email);
                doctorEntity.setOneLiner(oneLiner);
                doctorEntity.setId(id);

                CustomDoctorDetails customDoctorDetails = new CustomDoctorDetails(doctorEntity);
                Authentication authToken = new UsernamePasswordAuthenticationToken(customDoctorDetails, null, customDoctorDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authToken);
                logger.info("Doctor authenticated and set in SecurityContext.");
            } else if ("user".equals(userType)) {
                User userEntity = new User();
                userEntity.setUsername(username);
                userEntity.setEmail(email);
                userEntity.setPhoneNumber(phoneNumber);
                userEntity.setId(id);
                userEntity.setGender(gender);
                userEntity.setBirthDate(birthDate);

                CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);
                Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authToken);
                logger.info("User authenticated and set in SecurityContext.");
            }

        } catch (Exception e) {
            logger.error("Error processing JWT token: {}", e.getMessage(), e);
        }

        // 다음 필터로 요청 전달
        filterChain.doFilter(request, response);
    }

}
