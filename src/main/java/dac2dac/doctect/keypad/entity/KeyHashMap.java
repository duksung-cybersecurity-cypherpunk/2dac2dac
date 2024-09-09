package dac2dac.doctect.keypad.entity;

import java.util.Map;
import lombok.Getter;

@Getter
public class KeyHashMap implements Map<String, String> {

    private final Map<String, String> map;

    public KeyHashMap(Map<String, String> map) {
        this.map = map;
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public String get(Object key) {
        return map.get(key);
    }

    @Override
    public String put(String key, String value) {
        throw new UnsupportedOperationException("This map is immutable");
    }

    @Override
    public String remove(Object key) {
        throw new UnsupportedOperationException("This map is immutable");
    }

    @Override
    public void putAll(Map<? extends String, ? extends String> m) {
        throw new UnsupportedOperationException("This map is immutable");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("This map is immutable");
    }

    @Override
    public java.util.Set<String> keySet() {
        return map.keySet();
    }

    @Override
    public java.util.Collection<String> values() {
        return map.values();
    }

    @Override
    public java.util.Set<Entry<String, String>> entrySet() {
        return map.entrySet();
    }

    public static KeyHashMap toKeyHashMap(Map<String, String> map) {
        return new KeyHashMap(map);
    }
}