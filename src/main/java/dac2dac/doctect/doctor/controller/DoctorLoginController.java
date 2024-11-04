package dac2dac.doctect.doctor.controller;

import dac2dac.doctect.doctor.dto.CustomDoctorDetails;
import dac2dac.doctect.doctor.dto.request.DoctorLoginRequestDto;
import dac2dac.doctect.doctor.dto.request.DoctorRegisterRequestDto;
import dac2dac.doctect.doctor.entity.Doctor;
import dac2dac.doctect.doctor.repository.DoctorRepository;
import dac2dac.doctect.doctor.service.DoctorLoginService;
import dac2dac.doctect.user.controller.LoginController;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "(의사) 회원가입/로그인", description = "의사 회원가입 및 로그인 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/doctors")
public class DoctorLoginController {

    private final DoctorLoginService doctorService;
    private final DoctorRepository doctorRepository;
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @PostMapping("/signup")
    public ResponseEntity<String> registerDoctor(@RequestBody DoctorRegisterRequestDto doctorDTO) {
        Doctor registeredDoctor = doctorService.registerDoctor(doctorDTO);
        return ResponseEntity.ok("Doctor registered successfully with ID: " + registeredDoctor.getId());
    }


    @PostMapping("/login/jwt")
    public ResponseEntity<String> loginUser(@RequestBody DoctorLoginRequestDto userDTO, HttpServletResponse response) {
        try {
            String token = doctorService.authenticateAndGenerateToken(userDTO.getUsername(), userDTO.getPassword());
            response.addHeader("Authorization", "Bearer " + token);
            return ResponseEntity.ok("Login successful");
        } catch (Exception e) {
            logger.error("Error during login: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("Login failed: " + e.getMessage());
        }
    }


    @GetMapping("/login/jwt")
    public ResponseEntity<Map<String, String>> getProtectedResource() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            CustomDoctorDetails customDoctorDetails = (CustomDoctorDetails) authentication.getPrincipal();

            String name = customDoctorDetails.getUsername();
            String email = customDoctorDetails.getEmail();
            String oneLiner = customDoctorDetails.getOneLiner();
            String id = customDoctorDetails.getId();

            Map<String, String> response = new HashMap<>();
            response.put("username", name);
            response.put("email", email);
            response.put("id", id);
            response.put("oneLiner", oneLiner);

            return ResponseEntity.ok(response);

        } else {
            // 인증되지 않은 경우 오류 응답
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
}
