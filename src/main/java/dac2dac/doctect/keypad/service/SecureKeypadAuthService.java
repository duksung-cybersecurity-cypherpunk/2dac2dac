package dac2dac.doctect.keypad.service;

import dac2dac.doctect.keypad.dto.SecureKeypadAuthRequest;
import dac2dac.doctect.keypad.entity.IntegrityId;
import dac2dac.doctect.keypad.entity.KeyHashMap;
import dac2dac.doctect.keypad.repository.KeypadRepository;
import java.time.Duration;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecureKeypadAuthService {

    private final IntegrityIdFactoryService integrityIdFactoryService;
    private final KeypadRepository keypadRepository;

    private static final Duration EXPIRES_IN = Duration.ofMinutes(15);
    private static final int HASH_LENGTH = 40;

    public String sendKeypadInput(SecureKeypadAuthRequest secureKeypadAuthRequest) {
        checkIntegrity(secureKeypadAuthRequest.getIntegrityId());
        KeyHashMap keyHashMap = keypadRepository.getKeypad(secureKeypadAuthRequest.getIntegrityId());

        System.out.println("keyHashMap = " + keyHashMap);
        // userInput 복호화 후 keyHashMap을 통해 원래 값을 찾아낸 후 저장하는 로직

        // 유저 인풋의 해시 값을 원본 값으로 변환
        String originalValues = decryptUserInput(secureKeypadAuthRequest.getUserInput(), keyHashMap);

        // 결과 출력
        System.out.println("원본 값: " + originalValues);
        return originalValues;
    }

    private void checkIntegrity(IntegrityId integrityId) {
        integrityIdFactoryService.checkIntegrity(integrityId, EXPIRES_IN);
    }

    private String decryptUserInput(String userInput, KeyHashMap keyHashMap) {
        StringBuilder originalValues = new StringBuilder();
        for (int i = 0; i <= userInput.length() - HASH_LENGTH; i += HASH_LENGTH) {
            String hash = userInput.substring(i, i + HASH_LENGTH);
            for (Map.Entry<String, String> entry : keyHashMap.entrySet()) {
                if (entry.getValue().equals(hash)) {
                    originalValues.append(entry.getKey());
                }
            }
        }
        return originalValues.toString();
    }
}
