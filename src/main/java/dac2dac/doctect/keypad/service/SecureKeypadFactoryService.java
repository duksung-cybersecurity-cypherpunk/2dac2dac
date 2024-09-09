package dac2dac.doctect.keypad.service;

import dac2dac.doctect.keypad.dto.SecureKeypadResponse;
import dac2dac.doctect.keypad.entity.IntegrityId;
import dac2dac.doctect.keypad.entity.KeyHashMap;
import dac2dac.doctect.keypad.repository.KeypadRepository;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.imageio.ImageIO;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecureKeypadFactoryService {

    private final IntegrityIdFactoryService integrityIdFactoryService;
    private final KeypadRepository keypadRepository;

    private final Map<String, BufferedImage> images = createImages();

    private static final List<String> KEY_RANGE = IntStream.rangeClosed(0, 9)
        .mapToObj(String::valueOf)
        .collect(Collectors.toList());
    private static final String ID_PREFIX = "E2E";
    private static final String BLANK = "";
    private static final String IMAGE_B64_PREFIX = "data:image/png;base64,";
    private static final String IMAGE_FORMAT = "png";
    private static final int KEY_WIDTH = 120;
    private static final int KEY_HEIGHT = 64;
    private static final int NUMBER_OF_ROW = 4;
    private static final int NUMBER_OF_COL = 3;

    @SneakyThrows
    private Map<String, BufferedImage> createImages() {
        List<String> keys = new ArrayList<>(KEY_RANGE); // KEY_RANGE를 리스트로 변환
        keys.add(BLANK); // BLANK 추가

        return keys.stream()
            .collect(Collectors.toMap(key -> key,
                key -> {
                    try {
                        return getKeyPadImageFile(key);
                    } catch (IOException e) {
                        return null;
                    }
                }
            ));
    }

    public SecureKeypadResponse createSecureKeypad() {
        IntegrityId integrityId = integrityIdFactoryService.makeIntegrityId(ID_PREFIX);
        KeyHashMap keyHashMap = makeKeyHashMap();

        keypadRepository.saveKeypad(integrityId, keyHashMap);

        List<String> shuffledKeysWithBlank = makeShuffledKeysWithBlank();
        String imageBase64 = makeKeypadImage(shuffledKeysWithBlank);

        return new SecureKeypadResponse(
            integrityId,
            shuffledKeysWithBlank.stream()
                .map(keyHashMap::get)
                .map(key -> key != null ? key : BLANK)
                .collect(Collectors.toList()),
            imageBase64,
            NUMBER_OF_ROW,
            NUMBER_OF_COL
        );
    }

    private KeyHashMap makeKeyHashMap() {
        Map<String, String> map = KEY_RANGE.stream()
            .collect(Collectors.toMap(
                key -> key,
                key -> DigestUtils.sha1Hex(String.valueOf(new SecureRandom().nextInt()))
            ));
        return KeyHashMap.toKeyHashMap(map);
    }

    private List<String> makeShuffledKeysWithBlank() {
        List<String> keys = IntStream.range(0, 2)
            .mapToObj(i -> BLANK)
            .collect(Collectors.toList());
        keys.addAll(KEY_RANGE);
        Collections.shuffle(keys, new Random());
        return keys;
    }

    private String makeKeypadImage(List<String> keysWithBlank) {
        BufferedImage keypad = new BufferedImage(
            KEY_WIDTH * NUMBER_OF_COL,
            KEY_HEIGHT * NUMBER_OF_ROW,
            BufferedImage.TYPE_INT_ARGB);

        Graphics2D graphics2D = keypad.createGraphics();
        graphics2D.setColor(new Color(0, 0, 0, 0));

        for (int i = 0; i < keysWithBlank.size(); i++) {
            BufferedImage image = images.get(keysWithBlank.get(i));
            if (image != null) {
                graphics2D.drawImage(
                    image,
                    KEY_WIDTH * (i % NUMBER_OF_COL),
                    KEY_HEIGHT * (i / NUMBER_OF_COL),
                    null
                );
            }
        }

        graphics2D.dispose();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(keypad, IMAGE_FORMAT, baos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return IMAGE_B64_PREFIX + Base64.getEncoder().encodeToString(baos.toByteArray());
    }

    private BufferedImage getKeyPadImageFile(String key) throws IOException {
        String fileName = key.equals(BLANK) ? "_blank.png" : "_" + key + ".png";
        File file = new ClassPathResource("keypad/" + fileName).getFile();
        return ImageIO.read(file);
    }
}