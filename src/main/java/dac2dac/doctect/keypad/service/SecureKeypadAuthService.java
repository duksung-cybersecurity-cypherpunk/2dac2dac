package dac2dac.doctect.keypad.service;

import dac2dac.doctect.keypad.dto.SecureKeypadAuthRequest;
import dac2dac.doctect.keypad.entity.IntegrityId;
import dac2dac.doctect.keypad.entity.KeyHashMap;
import dac2dac.doctect.keypad.repository.KeypadRepository;
import jakarta.validation.Valid;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Duration;
import java.util.Base64;
import java.util.Map;
import javax.crypto.Cipher;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecureKeypadAuthService {

    private final IntegrityIdFactoryService integrityIdFactoryService;
    private final KeypadRepository keypadRepository;

    @Value("${secure-keypad.private-key}")
    private String PRIVATE_KEY;

    private static final Duration EXPIRES_IN = Duration.ofMinutes(15);
    private static final int HASH_LENGTH = 40;

    public String authKeypadInput(@Valid SecureKeypadAuthRequest secureKeypadAuthRequest) {
        checkIntegrity(secureKeypadAuthRequest.getIntegrityId());
        KeyHashMap keyHashMap = keypadRepository.getKeypad(secureKeypadAuthRequest.getIntegrityId());

        String decryptedInput = decryptUserInputWithPrivateKey(secureKeypadAuthRequest.getUserInput());
        String originalValues = decryptUserInput(decryptedInput, keyHashMap);

        return originalValues;
    }

    private void checkIntegrity(IntegrityId integrityId) {
        integrityIdFactoryService.checkIntegrity(integrityId, EXPIRES_IN);
    }

    private String decryptUserInput(String userInput, KeyHashMap keyHashMap) {
        StringBuilder originalValues = new StringBuilder();

        for (int i = 0; i <= userInput.length() - HASH_LENGTH; i += HASH_LENGTH) {
            String hash = userInput.substring(i, i + HASH_LENGTH);
            boolean found = false;
            for (Map.Entry<String, String> entry : keyHashMap.entrySet()) {
                if (entry.getValue().equals(hash)) {
                    originalValues.append(entry.getKey());
                    found = true;
                    break; // 해시 값이 일치하면 바로 루프를 빠져나감
                }
            }
            if (!found) {
                System.err.println("해시 값이 매칭되지 않았습니다: " + hash);
            }
        }
        return originalValues.toString();
    }

    private String decryptUserInputWithPrivateKey(String encryptedInput) {
        try {
            // Base64로 인코딩된 비공개 키를 디코딩하여 바이트 배열로 변환
            byte[] keyBytes = Base64.getDecoder().decode(PRIVATE_KEY);

            // 비공개 키 객체 생성
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

            // RSA 복호화 설정
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            // 암호화된 입력값 디코딩 및 복호화
            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedInput);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

            // 복호화된 바이트 배열을 문자열로 변환하여 반환
            return new String(decryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error while decrypting user input with private key", e);
        }
    }
}
