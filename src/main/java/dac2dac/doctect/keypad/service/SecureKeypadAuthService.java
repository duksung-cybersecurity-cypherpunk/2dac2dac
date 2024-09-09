package dac2dac.doctect.keypad.service;

import dac2dac.doctect.keypad.dto.SecureKeypadAuthRequest;
import dac2dac.doctect.keypad.entity.IntegrityId;
import dac2dac.doctect.keypad.repository.KeypadRepository;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecureKeypadAuthService {

    private final IntegrityIdFactoryService integrityIdFactoryService;
    private final KeypadRepository keypadRepository;

    private static final Duration EXPIRES_IN = Duration.ofMinutes(15);


    public String sendKeypadInput(SecureKeypadAuthRequest secureKeypadAuthRequest) {
        checkIntegrity(secureKeypadAuthRequest.getIntegrityId());
        var keyHashMap = keypadRepository.getKeypad(secureKeypadAuthRequest.getIntegrityId());
        // userInput 복호화 후 keyHashMap을 통해 원래 값을 찾아낸 후 저장하는 로직
        return secureKeypadAuthRequest.getUserInput();
    }

    private void checkIntegrity(IntegrityId integrityId) {
        integrityIdFactoryService.checkIntegrity(integrityId, EXPIRES_IN);
    }
}
