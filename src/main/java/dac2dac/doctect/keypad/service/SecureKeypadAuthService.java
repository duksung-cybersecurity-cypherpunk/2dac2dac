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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecureKeypadAuthService {

    private static final Logger LOGGER = Logger.getLogger(SecureKeypadAuthService.class.getName());

    private final IntegrityIdFactoryService integrityIdFactoryService;
    private final KeypadRepository keypadRepository;

    @Value("${secure-keypad.private-key}")
    private String PRIVATE_KEY;

    private static final Duration EXPIRES_IN = Duration.ofMinutes(15);
    private static final int HASH_LENGTH = 40;

    public String authKeypadInput(@Valid SecureKeypadAuthRequest secureKeypadAuthRequest) {
        LOGGER.log(Level.INFO, "키패드 입력 요청을 받았습니다. 무결성 ID: {0}", secureKeypadAuthRequest.getIntegrityId().toString());

        checkIntegrity(secureKeypadAuthRequest.getIntegrityId());
        LOGGER.log(Level.INFO, "무결성 검사에 성공했습니다. 무결성 ID: {0}", secureKeypadAuthRequest.getIntegrityId().toString());

        KeyHashMap keyHashMap = keypadRepository.getKeypad(secureKeypadAuthRequest.getIntegrityId());
        LOGGER.log(Level.INFO, "무결성 ID에 대한 키맵을 가져왔습니다. 무결성 ID: {0}", secureKeypadAuthRequest.getIntegrityId().toString());

        String decryptedInput = decryptUserInputWithPrivateKey(secureKeypadAuthRequest.getUserInput());
        LOGGER.log(Level.INFO, "사용자 입력을 복호화했습니다: {0}", decryptedInput);

        String originalValues = decryptUserInput(decryptedInput, keyHashMap);
        LOGGER.log(Level.INFO, "키패드 원래 값: {0}", originalValues);

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
                    LOGGER.log(Level.INFO, "해시 값 일치: {0} -> {1}", new Object[]{hash, entry.getKey()});
                    break; // 해시 값이 일치하면 바로 루프를 빠져나감
                }
            }
            if (!found) {
                LOGGER.log(Level.WARNING, "해시 값이 매칭되지 않았습니다: {0}", hash);
            }
        }
        return originalValues.toString();
    }

    private String decryptUserInputWithPrivateKey(String encryptedInput) {
        try {
            LOGGER.log(Level.INFO, "비공개 키로 입력을 복호화합니다...");

            byte[] keyBytes = Base64.getDecoder().decode(PRIVATE_KEY);

            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedInput);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

            String decryptedString = new String(decryptedBytes);
            LOGGER.log(Level.INFO, "복호화 성공. 복호화된 입력: {0}", decryptedString);

            return decryptedString;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "복호화 중 오류 발생: {0}", e.getMessage());
            throw new RuntimeException("비공개 키로 사용자 입력을 복호화하는 중 오류가 발생했습니다.", e);
        }
    }
}