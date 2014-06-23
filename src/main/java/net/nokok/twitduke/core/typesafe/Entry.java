package net.nokok.twitduke.core.typesafe;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * {@link TypeSafeMap} のエントリーを表現するクラスです。{@link EntryBuilder}によって生成されることを想定しています。
 *
 * @param <K> キー
 * @param <V> バリュー
 */
public class Entry<K, V> {

    private K key;
    private V value;
    private Class<K> keyClazz;
    private Class<V> valueClazz;

    Entry() {
    }

    void setKey(K key) {
        this.key = key;
    }

    /**
     * @return キー
     */
    public K getKey() {
        return key;
    }

    void setKeyClazz(Class<K> clazz) {
        this.keyClazz = clazz;
    }

    /**
     * @return キーのクラスオブジェクト
     */
    Class<K> getKeyClazz() {
        return keyClazz;
    }

    void setValueClazz(Class<V> clazz) {
        this.valueClazz = clazz;
    }

    /**
     * @return バリューのクラスオブジェクト
     */
    Class<V> getValueClazz() {
        return valueClazz;
    }

    void setValue(V value) {
        this.value = value;
    }

    /**
     * @return バリュー
     */
    public V getValue() {
        return value;
    }

    public Optional<K> optionalKey() {
        return Optional.ofNullable(key);
    }

    public Optional<V> optionalValue() {
        return Optional.ofNullable(value);
    }

    /**
     * このエントリのキーが指定された制約を満たしているかチェックします。
     *
     * @param constraint 制約
     *
     * @return 制約を満たしている場合true,満たしていない場合false
     */
    boolean assertKeyConstraints(Predicate<K> constraint) {
        if ( key == null ) {
            return true;
        }
        return Stream.of(key).filter(constraint).findFirst().isPresent();
    }

    /**
     * このエントリのバリューが指定された制約を満たしているかチェックします。
     *
     * @param constraint 制約
     *
     * @return 制約を満たしている場合true,満たしていない場合false
     */
    boolean assertValueConstraints(Predicate<V> constraint) {
        if ( value == null ) {
            return true;
        }
        return Stream.of(value).filter(constraint).findFirst().isPresent();
    }

    boolean isValidEntry() {
        return !(key == null || value == null || keyClazz == null || valueClazz == null);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value, keyClazz, valueClazz);
    }

    @Override
    public boolean equals(Object obj) {
        if ( obj == null ) {
            return false;
        }
        if ( !(obj instanceof Entry) ) {
            return false;
        }
        return obj.hashCode() == this.hashCode();
    }

    /**
     * エントリを文字列表現にして返します。
     * <p>
     * <blockquote><pre>
     * Entry<String, Integer> entry = EntryBuilder.newEntryType(String.class, Integer.class).key("hoge").value(123).create();
     * System.out.println(entry.toString());
     * </pre></blockquote>
     * このコードで出力される文字列は以下の形式になります<br/>
     * {@code [KeyClass:java.lang.String,Key:hoge,ValueClass:java.lang.Integer,Value:123]}
     *
     * @return
     */
    @Override
    public String toString() {
        return String.format("[KeyClass:%s,Key:%s,ValueClass:%s,Value:%s]",
                             keyClazz == null ? "null" : keyClazz.getName(),
                             key,
                             valueClazz == null ? "null" : valueClazz.getName(),
                             value);
    }
}
