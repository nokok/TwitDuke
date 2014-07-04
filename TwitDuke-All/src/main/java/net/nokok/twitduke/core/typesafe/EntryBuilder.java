package net.nokok.twitduke.core.typesafe;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;
import static net.nokok.twitduke.core.util.BooleanFunctionalUtil.throwWhenFalseCondition;
import static net.nokok.twitduke.core.util.BooleanFunctionalUtil.throwWhenTrueCondition;

/**
 * エントリを生成するビルダークラスです。
 * <p>
 * コード例:
 * <blockquote><pre>
 *  EntryBuilder.newEntryType(String.class, Integer.class)
 *              .key("key").value(123)
 *              .create();
 *
 *  //キーやバリューに制約条件を与える場合
 *  String key = doSomething1();
 *  int value = doSomething2();
 *  EntryBuilder.newEntryType(String.class, Integer.class)
 *              .key(key, k -> k.length() > 3) //キーが3文字以上
 *              .value(value, num -> num > 0)  //バリューは自然数
 *              .create();
 * </pre></blockquote>
 * キーやバリューには制約条件を与える事ができます。キーのみ、またはバリューのみに制約を与える事もできます。
 * 渡されたパラメーターが制約を満たさない場合、
 * {@link EntryBuilder#key(java.lang.Object, java.util.function.Predicate) }または
 * {@link EntryBuilder#value(java.lang.Object, java.util.function.Predicate) }が
 * 呼ばれた時点で実行時に{@link IllegalArgumentException}がスローされます。
 * このクラスのメソッドはnullを許容しません。パラメーターとしてnullを渡した場合、
 * nullを渡したメソッドから{@link NullPointerException}がスローされます。
 * <p>
 * {@link EntryBuilder#addKeyConstraint(java.util.function.Predicate) }及び
 * {@link EntryBuilder#addValueConstraint(java.util.function.Predicate) }メソッドを使用することで
 * キーやバリューに複数の制約を与えることができます。
 * このメソッドによって追加した制約は{@link EntryBuilder#key(java.lang.Object)}メソッド及び
 * {@link EntryBuilder#value(java.lang.Object) }メソッドでのみ評価されます。
 * {@link EntryBuilder#key(java.lang.Object, java.util.function.Predicate) }と
 * {@link EntryBuilder#value(java.lang.Object, java.util.function.Predicate) }メソッドではあらかじめ追加されている制約は全て無視され、指定した制約のみ評価されることに注意が必要です。
 * キーやバリューが既にセットされている場合は、制約を追加した時点で制約が評価されます。
 * また、制約が1つ以上追加されている場合、キーやバリューをセットする時点で制約が評価されます。
 * 新たに追加した制約をキーやバリューが満たしていない場合は追加した時点で{@link IllegalArgumentException}がスローされます。
 * <p>
 * コード例:
 * <blockquote><pre>
 *  EntryBuilder.newEntryType(String.class, Integer.class)
 *              .addKeyConstraint(str -> !str.isEmpty())
 *              .addValueConstraint(num -> num > 100)
 *              .key("key")
 *              .value(123)
 *              .create();
 *
 *  EntryBuilder.newEntryType(String.class, Integer.class)
 *              .addValueConstraint(num -> num > 1000)
 *              .key("key")
 *              .value(123) //123 > 1000はfalseなのでIllegalArgumentExceptionがスロー
 *              .create();
 *
 *  EntryBuilder.newEntryType(String.class, Integer.class)
 *              .addValueConstraint(num -> num > 1000)
 *              .addValueConstraint(num -> num == 0)
 *              .key("key")
 *              .value(123, num -> num > 100) //addValueConstraintメソッドの制約は無視され、num -> num > 100の制約のみ評価される
 *              .create();
 *
 *  //先にキーやバリューをセットする場合
 *  EntryBuilder.newEntryType(String.class, Integer.class)
 *              .key("key")
 *              .value(123)
 *              .addKeyConstraint(str -> str.equals("hoge")) //"key".equals("hoge")はfalseなのでIllegalArgumentExceptionがスロー
 *              .create();
 * </pre></blockquote>
 *
 *
 * @param <K> キーの型
 * @param <V> バリューの型
 */
public class EntryBuilder<K, V> {

    private final Entry<K, V> entry = new Entry<>();
    private final EntryConstraints<K, V> constraints = new EntryConstraints<>();

    /**
     * 空の文字列をフィルタします
     */
    public static final Predicate<String> NOT_EMPTY_STRING = (str) -> !str.isEmpty();

    /**
     * 指定した型でエントリーを構築するビルダーを生成します。
     * クラスオブジェクトを渡すことで{@code key}と{@code value}の型を保証します
     *
     * @param <K>        キーの型
     * @param <V>        バリューの型
     * @param keyClazz   キーのクラスオブジェクト
     * @param valueClazz バリューのクラスオブジェクト
     *
     * @exception NullPointerException パラメータがnullの場合
     *
     * @return 新しく生成されたビルダー
     */
    public static <K, V> EntryBuilder<K, V> newEntryType(Class<K> keyClazz, Class<V> valueClazz) {
        EntryBuilder<K, V> builder = new EntryBuilder<>();
        builder.entry.setKeyClazz(Objects.requireNonNull(keyClazz));
        builder.entry.setValueClazz(Objects.requireNonNull(valueClazz));
        return builder;
    }

    /**
     * キーをセットします。
     * {@link EntryBuilder#addKeyConstraint(java.util.function.Predicate)}メソッドによって1つ以上制約が指定されている場合は全ての制約を満たしているかを評価します。
     * 制約を満たしていない場合{@link IllegalArgumentException}がスローされます。
     *
     * @param key キー
     *
     * @exception NullPointerException     パラメータがnullの場合
     * @exception IllegalArgumentException (既にキー制約が指定されている場合に)指定されたキーが制約を満たさない場合
     * @return ビルダー自身
     */
    public EntryBuilder<K, V> key(K key) {
        Objects.requireNonNull(key);
        constraints.keyConstraintsStream().forEach(c -> {
            Stream.of(key).filter(c).findAny().orElseThrow(IllegalArgumentException::new);
        });
        entry.setKey(key);
        return this;
    }

    /**
     * 1つの制約付きのキーをセットします。指定したキーが指定した制約を満たさない場合{@link IllegalArgumentException}がスローされます。
     * キーがnullかどうかの判定処理は必要ありません。
     * このメソッドは{@link EntryBuilder#addKeyConstraint(java.util.function.Predicate) }によって追加されたキー制約を<strong>全て無視</strong>し、
     * 指定された制約のみを評価します。
     *
     * @param key        キー
     * @param constraint 制約
     *
     * @exception IllegalArgumentException 指定したキーが{@link EntryBuilder#addKeyConstraint(java.util.function.Predicate) }で追加された制約を満たさない場合
     * @exception NullPointerException     パラメーターがnullの場合
     * @return ビルダー自身
     */
    public EntryBuilder<K, V> key(K key, Predicate<K> constraint) {
        Objects.requireNonNull(key, "キーがnullです");
        Objects.requireNonNull(constraint, "制約がnullです");
        entry.setKey(Stream.of(key).filter(constraint).findFirst().orElseThrow(IllegalArgumentException::new));
        return this;
    }

    /**
     * キー制約を追加します。このキー制約はビルダー毎に独立しています。
     * キーが既にセットされている場合は制約を満たしているか評価され、制約を満たしていない場合は{@link IllegalArgumentException}がスローされます。
     * キーがセットされていない場合は評価は行われず、{@link EntryBuilder#key(java.lang.Object) }を呼んだ時点で制約を満たしているか評価されます。
     *
     * @param constraint 追加するキー制約
     *
     * @exception IllegalArgumentException (既にキーがセットされている場合に)制約を満たしていない場合
     * @exception NullPointerException     指定された制約がnull
     * @return ビルダー自身
     */
    public EntryBuilder<K, V> addKeyConstraint(Predicate<K> constraint) {
        constraints.addKeyConstraint(Objects.requireNonNull(constraint));
        throwWhenFalseCondition(entry.assertKeyConstraints(constraint), IllegalArgumentException::new);
        return this;
    }

    /**
     * キー制約をリストから複数追加します。リストで制約を指定することが出来るという点以外は{@link EntryBuilder#addKeyConstraint(java.util.function.Predicate) }と同じです。
     *
     * @param constraints 制約のリスト
     *
     * @return ビルダー自身
     */
    public EntryBuilder<K, V> addKeyConstraint(List<Predicate<K>> constraints) {
        List<Predicate<K>> c = Objects.requireNonNull(constraints);
        throwWhenTrueCondition(c::isEmpty, IllegalArgumentException::new);
        c.forEach(this::addKeyConstraint);
        return this;
    }

    /**
     * バリューをセットします。
     * {@link EntryBuilder#addValueConstraint(java.util.function.Predicate)}メソッドによって1つ以上制約が指定されている場合は全ての制約を満たしているかを評価します。
     * 制約を満たしていない場合{@link IllegalArgumentException}がスローされます。
     *
     * @param value バリュー
     *
     * @exception NullPointerException     パラメータがnullの場合
     * @exception IllegalArgumentException 指定したバリューが{@link EntryBuilder#addValueConstraint(java.util.function.Predicate) }で追加された制約を満たさない場合
     * @return ビルダー自身
     */
    public EntryBuilder<K, V> value(V value) {
        Objects.requireNonNull(value);
        constraints.valueConstraintsStream().forEach(c -> {
            Stream.of(value).filter(c).findAny().orElseThrow(IllegalArgumentException::new);
        });
        entry.setValue(value);
        return this;
    }

    /**
     * 1つの制約付きのバリューをセットします。指定したバリューが指定した制約を満たさない場合{@link IllegalArgumentException}がスローされます。
     * バリューがnullかどうかの判定処理は必要ありません。
     * このメソッドは{@link EntryBuilder#addValueConstraint(java.util.function.Predicate) }によって追加されたバリュー制約を<strong>全て無視</strong>し、
     * 指定された制約のみを評価します。
     *
     * @param value      バリュー
     * @param constraint 制約
     *
     * @exception IllegalArgumentException 指定したバリューが制約を満たさない場合
     * @exception NullPointerException     パラメータがnullの場合
     * @return ビルダー自身
     */
    public EntryBuilder<K, V> value(V value, Predicate<V> constraint) {
        Objects.requireNonNull(value, "バリューがnullです");
        Objects.requireNonNull(constraint, "制約がnullです");
        entry.setValue(Stream.of(value).filter(constraint).findFirst().orElseThrow(IllegalArgumentException::new));
        return this;
    }

    /**
     * バリュー制約を追加します。このバリュー制約はビルダー毎に独立しています。
     * バリューが既にセットされている場合は制約を満たしているか評価され、制約を満たしていない場合は{@link IllegalArgumentException}がスローされます。
     * バリューがセットされていない場合は評価は行われず、{@link EntryBuilder#value(java.lang.Object) }を呼んだ時点で制約を満たしているか評価されます。
     *
     * @param constraint 追加するバリュー制約
     *
     * @exception IllegalArgumentException (既にバリューがセットされている場合に)制約を満たしていない場合
     * @exception NullPointerException     指定された制約がnull
     * @return ビルダー自身
     */
    public EntryBuilder<K, V> addValueConstraint(Predicate<V> constraint) {
        constraints.addValueConstraint(Objects.requireNonNull(constraint));
        throwWhenTrueCondition(entry.assertValueConstraints(constraint), IllegalArgumentException::new);
        return this;
    }

    /**
     * バリュー制約をリストから複数追加します。リストで制約を指定することが出来るという点以外は{@link EntryBuilder#addValueonstraint(java.util.function.Predicate) }と同じです。
     *
     * @param constraints 制約のリスト
     *
     * @return ビルダー自身
     */
    public EntryBuilder<K, V> addValueConstraint(List<Predicate<V>> constraints) {
        List<Predicate<V>> c = Objects.requireNonNull(constraints);
        throwWhenTrueCondition(c::isEmpty, IllegalArgumentException::new);
        c.forEach(this::addValueConstraint);
        return this;
    }

    /**
     * このビルダーからエントリを生成します。エントリーが不正な状態にある場合は{@link IllegalArgumentException}がスローされます
     *
     * @exception IllegalStateException セットしていない値がある場合
     * @return エントリー
     */
    public Entry<K, V> create() {
        throwWhenFalseCondition(entry.isValidEntry(),
                                () -> new IllegalStateException(String.format("nullを含んではいけません。全ての項目は必須です %s%n", entry.toString())));
        return entry;
    }

}
