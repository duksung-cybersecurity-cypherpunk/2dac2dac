package dac2dac.doctect.keypad.controller;

import dac2dac.doctect.keypad.dto.SecureKeypadAuthRequest;
import dac2dac.doctect.keypad.dto.SecureKeypadResponse;
import dac2dac.doctect.keypad.service.SecureKeypadAuthService;
import dac2dac.doctect.keypad.service.SecureKeypadFactoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/keypad")
public class SecureKeypadController {

    private final SecureKeypadFactoryService secureKeypadFactoryService;
    private final SecureKeypadAuthService secureKeypadAuthService;

    @GetMapping("/create")
    public SecureKeypadResponse createKeypad() {
        return secureKeypadFactoryService.createSecureKeypad();
    }

    @PostMapping("/auth")
    public String handleKeypadInput(@RequestBody SecureKeypadAuthRequest secureKeypadAuthRequest) {
        return secureKeypadAuthService.sendKeypadInput(secureKeypadAuthRequest);
    }
}
