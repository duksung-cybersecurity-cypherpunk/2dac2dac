package dac2dac.doctect.user.controller;

import dac2dac.doctect.user.dto.EmailAuthRequestDto;
import dac2dac.doctect.user.dto.UserDTO;
import dac2dac.doctect.user.entity.User;
import dac2dac.doctect.user.service.MailService;
import dac2dac.doctect.user.service.UserService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
@RequiredArgsConstructor
public class EmailController {

    private final MailService mailService;
    private final UserService userService;

    // Endpoint for sending email without user authentication
    @PostMapping("/api/v1/login/mailConfirm")
    public String mailConfirm(@RequestBody EmailAuthRequestDto emailDto) throws MessagingException, UnsupportedEncodingException {
        String authCode = mailService.sendEmail(emailDto.getEmail());
        return authCode;
    }

    // Endpoint for sending email with user authentication
    @PostMapping("/api/v1/login/user/mailConfirm")
    public String mailConfirm(@RequestBody UserDTO userDto) throws MessagingException, UnsupportedEncodingException {
        // Validate user credentials
        boolean isAuthenticated = userService.authenticateUser(userDto.getUsername(), userDto.getPassword());

        if (!isAuthenticated) {
            throw new RuntimeException("Invalid username or password");
        }

        // Fetch the user's email from the database
        User user = userService.findByUsername(userDto.getUsername());
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // If authentication is successful, send the confirmation email
        //System.out.println("user's email: " + user.getEmail());
        String authCode = mailService.sendEmail(user.getEmail());
        return authCode;
    }


    // Endpoint for verifying the auth code
    @PostMapping("/api/v1/login/verifyAuthCode")
    public boolean verifyAuthCode(@RequestBody EmailAuthRequestDto emailDto) {
        return mailService.verifyAuthCode(emailDto.getEmail(), emailDto.getAuthCode());
    }
}
