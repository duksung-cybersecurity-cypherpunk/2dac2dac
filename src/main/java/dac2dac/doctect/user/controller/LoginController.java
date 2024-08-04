package dac2dac.doctect.user.controller;

import dac2dac.doctect.user.dto.UserDTO;
import dac2dac.doctect.user.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Tag(name = "유저", description = "회원가입")
@RestController
@CrossOrigin(origins = "http://192.168.219.180:8081") // Expo 개발 서버 주소
public class LoginController {

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @PostMapping("/api/v1/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDTO userDTO) {
        try {
            userService.registerUser(
                    userDTO.getUsername(),
                    userDTO.getEmail(),
                    userDTO.getPassword(),
                    userDTO.getPhoneNumber(),
                    userDTO.getCode(),
                    userDTO.getSocialType()
            );
            return ResponseEntity.ok("Registration successful!!");
        } catch (Exception e) {
            logger.error("Error during registration: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body("Registration failed: " + e.getMessage());
        }
    }

    @PostMapping("/api/v1/login")
    public ResponseEntity<String> loginUser(@RequestBody UserDTO userDTO) {
        //System.out.println("loginUser 호출됨"); // 로그 추가
        try {
            boolean isAuthenticated = userService.authenticateUser(
                    userDTO.getUsername(),
                    userDTO.getPassword()
            );
            if (isAuthenticated) {
                // JWT Token 생성하기
                //String token = jwtUtility.generateToken(userDTO.getUsername());
                //ResponseEntity.ok(token);
                return ResponseEntity.ok("Login successful");
            } else {
                return ResponseEntity.status(401).body("Invalid2 username or password");
            }
        } catch (Exception e) {
            logger.error("Error during login: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("Login failed::: " + e.getMessage());
        }
    }
}
