package dac2dac.doctect.user.controller;

import dac2dac.doctect.doctor.dto.DoctorDTO;
import dac2dac.doctect.doctor.entity.Doctor;
import dac2dac.doctect.doctor.service.DoctorLoginService;
import dac2dac.doctect.user.dto.request.EmailAuthRequestDto;
import dac2dac.doctect.user.dto.request.UserDTO;
import dac2dac.doctect.user.entity.User;
import dac2dac.doctect.user.service.MailService;
import dac2dac.doctect.user.service.UserService;
import jakarta.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmailController {

    private final MailService mailService;
    private final UserService userService;
    private final DoctorLoginService doctorService;

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
        String authCode = mailService.sendEmail(user.getEmail());
        return authCode;
    }

    @PostMapping("/api/v1/login/doctor/mailConfirm")
    public String mailConfirm(@RequestBody DoctorDTO doctorDTO) throws MessagingException, UnsupportedEncodingException {
        // Validate user credentials
        boolean isAuthenticated = doctorService.authenticateUser(doctorDTO.getName(), doctorDTO.getPassword());

        if (!isAuthenticated) {
            throw new RuntimeException("Invalid username or password");
        }

        // Fetch the user's email from the database
        Doctor doctor = doctorService.findByName(doctorDTO.getName());
        if (doctor == null) {
            throw new RuntimeException("User not found");
        }

        // If authentication is successful, send the confirmation email
        String authCode = mailService.sendEmail(doctor.getEmail());
        return authCode;
    }

}
