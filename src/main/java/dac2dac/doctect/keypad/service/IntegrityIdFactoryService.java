package dac2dac.doctect.keypad.service;

import dac2dac.doctect.keypad.entity.IntegrityId;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.tomcat.util.buf.HexUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IntegrityIdFactoryService {

    private static final String SECRET_HMAC_KEY = "DOCTECH";
    private static final String ALGORITHM = "HmacSHA256";
    private static final DateTimeFormatter DATE_TIME_PATTERN = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
    private final Random random = new Random();

    public IntegrityId makeIntegrityId(String idPrefix) {
        String id = makeId(idPrefix);
        long timestamp = Instant.now().toEpochMilli();
        String hmac = makeHmac(id + timestamp);

        return new IntegrityId(id, timestamp, hmac);
    }

    public String makeId(String idPrefix) {
        String dateTime = LocalDateTime.now().format(DATE_TIME_PATTERN);
        String randomStr = DigestUtils.sha1Hex(Integer.toString(random.nextInt())).substring(0, 5);
        return String.format("%s-%s-%s", idPrefix, dateTime, randomStr);
    }

    public void checkIntegrity(IntegrityId integrityId, Duration expiresIn) {
        String hmac = makeHmac(integrityId.getId() + integrityId.getTimestamp());
        if (!integrityId.getHmac().equals(hmac)) {
            throw new IllegalArgumentException("Invalid HMAC");
        }
        if (integrityId.getTimestamp() <= Instant.now().minus(expiresIn).toEpochMilli()) {
            throw new IllegalArgumentException("Integrity ID has expired");
        }
    }

    private String makeHmac(String message) {
        try {
            SecretKey secretKey = new SecretKeySpec(SECRET_HMAC_KEY.getBytes(StandardCharsets.UTF_8), ALGORITHM);
            Mac hasher = Mac.getInstance(ALGORITHM);
            hasher.init(secretKey);
            byte[] hash = hasher.doFinal(message.getBytes(StandardCharsets.UTF_8));
            return HexUtils.toHexString(hash);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Error generating HMAC", e);
        }
    }
}

