package dac2dac.doctect.user.controller;

import dac2dac.doctect.user.dto.CustomUserDetails;
import dac2dac.doctect.user.dto.request.UserDTO;
import dac2dac.doctect.user.dto.request.UserLoginRequestDto;
import dac2dac.doctect.user.entity.constant.Gender;
import dac2dac.doctect.user.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
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
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "유저", description = "회원가입")
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @PostMapping("/api/v1/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDTO userDTO) {
        try {
            userService.registerUser(
                userDTO.getUsername(),
                userDTO.getEmail(),
                userDTO.getPassword(),
                userDTO.getPhoneNumber(),
                userDTO.getGender(),
                userDTO.getBirthDate()
            );
            return ResponseEntity.ok("Registration successful!!");
        } catch (Exception e) {
            logger.error("Error during registration: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body("Registration failed: " + e.getMessage());
        }
    }


    @PostMapping("/api/v1/login/jwt")
    public ResponseEntity<String> loginUser(@Valid @RequestBody UserLoginRequestDto userDTO, HttpServletResponse response) {
        try {
            String token = userService.authenticateAndGenerateToken(userDTO.getUsername(), userDTO.getPassword());
            response.addHeader("Authorization", "Bearer " + token);
            return ResponseEntity.ok("Login successful");
        } catch (Exception e) {
            logger.error("Error during login: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("Login failed: " + e.getMessage());
        }
    }


    @GetMapping("/api/v1/login/jwt")
    public ResponseEntity<Map<String, String>> getProtectedResource() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

            String name = customUserDetails.getUsername();
            String email = customUserDetails.getEmail();
            String PhoneNumber = customUserDetails.getPhoneNumber();
            String id = customUserDetails.getId();
            Gender gender = customUserDetails.getGender();
            String birthdDate = customUserDetails.getBirthDate();

            Map<String, String> response = new HashMap<>();
            response.put("username", name);
            response.put("email", email);
            response.put("id", id);
            response.put("phonenumber", PhoneNumber);
            response.put("gender", gender.toString());
            response.put("birthdDate", birthdDate);

            return ResponseEntity.ok(response);

        } else {
            // 인증되지 않은 경우 오류 응답
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
    
}
