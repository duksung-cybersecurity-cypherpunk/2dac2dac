package dac2dac.doctect.login.controller;


import dac2dac.doctect.login.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor // 생성자 주입 방식 사용
@CrossOrigin(origins = "http://10.67.78.11:8081")
public class EmailController {
    private final EmailService emailService;

    @PostMapping("/infoCheck/sendEmail")
    public ResponseEntity<Void> sendEmail(@RequestParam("email") String email, HttpSession session) {
        // 이메일 전송 및 인증번호 저장
        System.out.println("email: " + email);
        String verificationCode = emailService.sendVerificationCode(email);
        session.setAttribute("verificationCode", verificationCode); // 세션에 전송된 인증번호를 저장.
        return ResponseEntity.ok().build();
    }

    //    입력한 인증번호와 세션에 저장된 인증번호를 비교
    @PostMapping("/infoCheck/verifyCode")
    public ResponseEntity<Boolean> verifyCode(@RequestParam("inputCode") String inputCode, HttpSession session) {
        String storedCode = (String) session.getAttribute("verificationCode");

        if (storedCode != null && storedCode.equals(inputCode)) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.ok(false);
        }
    }
}