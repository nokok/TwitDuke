package net.nokok.twitduke.core.typesafe;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * 制約を保持するクラスです。
 *
 * @param <K> キーの型
 * @param <V> バリューの型
 */
class EntryConstraints<K, V> {

    private final List<Predicate<K>> keyConstraints = new ArrayList<>();
    private final List<Predicate<V>> valueConstraints = new ArrayList<>();

    /**
     * 指定されたキー制約を登録します
     *
     * @param constraint キー制約
     */
    public void addKeyConstraint(Predicate<K> constraint) {
        keyConstraints.add(Objects.requireNonNull(constraint));
    }

    /**
     * 指定されたバリュー制約を登録します
     *
     * @param constraint バリュー制約
     */
    public void addValueConstraint(Predicate<V> constraint) {
        valueConstraints.add(Objects.requireNonNull(constraint));
    }

    /**
     * @return キー制約のストリーム
     */
    public Stream<Predicate<K>> keyConstraintsStream() {
        return keyConstraints.stream();
    }

    /**
     * @return バリュー制約のストリーム
     */
    public Stream<Predicate<V>> valueConstraintsStream() {
        return valueConstraints.stream();
    }

    /**
     * @return キー制約が1つ以上登録されている場合true、1つも登録されていない場合false
     */
    public boolean isEmptyKeyConstraints() {
        return keyConstraints.isEmpty();
    }

    /**
     * @return バリュー制約が1つ以上登録されている場合true、1つも登録されていない場合false
     */
    public boolean isEmptyValueConstraints() {
        return valueConstraints.isEmpty();
    }
}
