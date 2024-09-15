package dac2dac.doctect.keypad.repository;

import dac2dac.doctect.keypad.entity.IntegrityId;
import dac2dac.doctect.keypad.entity.KeyHashMap;
import java.time.Duration;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class KeypadRepository {

    private final StringRedisTemplate stringRedisTemplate;

    private static final Duration CLEAR_DURATION = Duration.ofDays(1);

    public void saveKeypad(IntegrityId integrityId, KeyHashMap keyHashMap) {
        stringRedisTemplate.opsForHash().putAll(integrityId.getId(), keyHashMap.getMap());
        stringRedisTemplate.expire(integrityId.getId(), CLEAR_DURATION);
    }

    public KeyHashMap getKeypad(IntegrityId integrityId) {
        HashOperations<String, String, String> hashOps = stringRedisTemplate.opsForHash();

        // Redis에서 해시 맵을 가져옴
        Map<String, String> entries = hashOps.entries(integrityId.getId());

        // KeyHashMap으로 변환
        KeyHashMap keyHashMap = KeyHashMap.toKeyHashMap(entries);

        // Redis에서 해당 ID의 데이터 삭제
        stringRedisTemplate.delete(integrityId.getId());

        return keyHashMap;
    }

}
