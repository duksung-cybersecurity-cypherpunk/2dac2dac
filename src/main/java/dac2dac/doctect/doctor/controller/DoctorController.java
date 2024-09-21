package dac2dac.doctect.doctor.controller;

import dac2dac.doctect.doctor.dto.CustomDoctorDetails;
import dac2dac.doctect.doctor.dto.DoctorDTO;
import dac2dac.doctect.doctor.entity.Doctor;
import dac2dac.doctect.doctor.repository.DoctorRepository;
import dac2dac.doctect.doctor.service.DoctorService;
import dac2dac.doctect.user.controller.LoginController;
import dac2dac.doctect.user.dto.UserDTO;
import dac2dac.doctect.user.jwt.JWTUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    @Autowired
    private DoctorRepository doctorRepository; // 세미콜론 추가


    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);


    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    // Endpoint to register a new doctor
    @PostMapping("/signup")
    public ResponseEntity<String> registerDoctor(@RequestBody DoctorDTO doctorDTO) {

        // 이메일 중복 체크
        if (doctorRepository.findByEmail(doctorDTO.getEmail()) != null) {
            return ResponseEntity.badRequest().body("Email is already registered.");
        }

        Doctor registeredDoctor = doctorService.registerDoctor(doctorDTO);
        return ResponseEntity.ok("Doctor registered successfully with ID: " + registeredDoctor.getId());
    }


    @PostMapping("/login/jwt")
    public ResponseEntity<String> loginUser(@RequestBody UserDTO userDTO, HttpServletResponse response) {
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
    public ResponseEntity<Map<String, String>>  getProtectedResource() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            CustomDoctorDetails customDoctorDetails = (CustomDoctorDetails) authentication.getPrincipal();

            String name = customDoctorDetails.getUsername();
            String email = customDoctorDetails.getEmail(); // CustomUserDetails에서 이메일 가져오기
            String oneLiner= customDoctorDetails.getOneLiner();
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
