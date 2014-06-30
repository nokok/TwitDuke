package net.nokok.twitduke.core.typesafe;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * キーとバリューを型安全な状態で保持出来るオブジェクトです。
 * バリューオブジェクトを指定したキーオブジェクトで保持します。
 * エントリのキーの型が同じである必要はありません。
 * ただし、キーやバリューで格納される型がいつも同じ場合は、
 * このクラスではなく{@link Map}などのコレクションを使用するべきです
 * <p>
 * コード例:
 * <blockquote><pre>
 *  TypeSafeDataStore store = new TypeSafeDataStore();
 *  map.addEntry(EntryBuilder
 *                      .newEntryType(String.class, AccessToken.class) //キーとバリューの型を指定
 *                      .key("orekyuu") //String型以外はコンパイルエラー
 *                      .value(accessToken) //AccessToken以外はコンパイルエラー
 *                      .create());
 *  //直接型を指定して追加する
 *  store.addEntry(String.class, AccessToken.class, "orekyuu", accessToken);
 *
 *  //異なる型のエントリを同一のオブジェクトに追加可能
 *  store.addEntry(EntryBuilder
 *                      .newEntryType(AccessToken.class, Twitter.class)
 *                      .key(orekyuu) //AccessToken以外はコンパイルエラー
 *                      .value(twitter) //Twittter以外はコンパイルエラー
 *                      .create());
 * {@code Optional<Twitter> t = store.getEntry(Twitter.class, orekyuu);}
 * </pre></blockquote>
 *
 */
public class TypeSafeDataStore {

    private final Map<Object, Object> map;

    /**
     * ObjectMapを初期容量で構築します
     */
    public TypeSafeDataStore() {
        map = new HashMap<>();
    }

    /**
     * 指定された初期容量でデータストアを構築します
     *
     * @param capacity
     */
    public TypeSafeDataStore(int capacity) {
        map = new HashMap<>(capacity);
    }

    /**
     * 指定されたエントリオブジェクトを追加します
     *
     * @param <K>   キーの型
     * @param <V>   バリューの型
     * @param entry エントリ
     *
     * @exception NullPointerException エントリーがnullの場合
     */
    public <K, V> void addEntry(Entry<K, V> entry) {
        if ( entry == null ) {
            throw new NullPointerException("エントリがnullです");
        }
        map.put(entry.getKeyClazz().cast(entry.getKey()), entry.getValueClazz().cast(entry.getValue()));
    }

    /**
     * エントリオブジェクトを使用せずにエントリを追加します。以下の2つのコードは同じ動作をします
     *
     * <blockquote><pre>
     * map.addEntry(EntryBuilder.newEntryType(String.class, Integer.class).key("hoge").value(123).create());
     * map.addEntry(String.class, Integer.class, "hoge", 123);
     * </pre></blockquote>
     *
     * @param <K>        キーの型
     * @param <V>        バリューの型
     * @param keyClazz   キーのクラスオブジェクト
     * @param valueClazz バリューのクラスオブジェクト
     * @param key        キー
     * @param value      バリュー
     *
     * @exception NullPointerException いずれかのパラメーターがnullの場合
     * @exception ClassCastException   キャストに失敗した場合
     */
    public <K, V> void addEntry(Class<K> keyClazz, Class<V> valueClazz, K key, V value) {
        if ( keyClazz == null ) {
            throw new NullPointerException("キーの型がnullです");
        }
        if ( valueClazz == null ) {
            throw new NullPointerException("値の型がnullです");
        }
        if ( key == null ) {
            throw new NullPointerException("キーがnullです");
        }
        if ( value == null ) {
            throw new NullPointerException("オブジェクトがnullです");
        }
        addEntry(EntryBuilder.newEntryType(keyClazz, valueClazz).key(key).value(value).create());
    }

    /**
     * 指定したキーのオブジェクトを指定した型で取得します。
     * 指定された型によってはキャストが発生する場合があります。
     * キーが存在しなかった場合、または指定した型に出来なかった場合(間違えて指定した場合など)はOptional.emptyが返されます。
     * nullが返ることはありません
     *
     * @param <K>  キーの型
     * @param <V>  バリューの型
     * @param type 返す型のクラスオブジェクト
     * @param key  キーオブジェクト
     *
     * @exception NullPointerException いずれかのパラメーターがnullの場合
     * @return Optionalでラップされたバリュー
     */
    public <K, V> Optional<V> getEntry(Class<V> type, K key) {
        if ( type == null ) {
            throw new NullPointerException("型がnullです");
        }
        if ( key == null ) {
            throw new NullPointerException("キーがnullです");
        }
        try {
            return Optional.of(type.cast(map.get(key)));
        } catch (ClassCastException | NullPointerException e) {
            return Optional.empty();
        }
    }

    /**
     * 指定したキーのエントリを返し、削除します
     *
     * @param <K>  キーの型
     * @param <V>  バリューの型
     * @param type 返す型のクラスオブジェクト
     * @param key  キーオブジェクト
     *
     * @exception IllegalArgumentException 指定されたキーが存在しない場合
     * @exception ClassCastException       指定された型にキャスト出来ない場合
     * @return 削除されたエントリ
     */
    public <K, V> V remove(Class<V> type, K key) {
        if ( !map.containsKey(key) ) {
            throw new IllegalArgumentException("指定されたキーが存在しません:" + key);
        }
        return type.cast(map.remove(key));
    }

    /**
     * 指定したエントリを返し、削除します。
     *
     * @param <K>   キーの型
     * @param <V>   バリューの型
     * @param type  取得する型のクラスオブジェクト
     * @param entry エントリ
     *
     * @exception IllegalArgumentException 指定されたエントリが存在しない場合
     * @return 削除されたバリュー
     */
    public <K, V> V remove(Class<V> type, Entry<K, V> entry) {
        Optional<V> obj = getEntry(type, entry.getKey());
        if ( obj.isPresent() ) {
            map.remove(entry.getKey());
            return obj.get();
        }
        throw new IllegalArgumentException("指定されたエントリが存在しません");
    }

    /**
     * 指定したエントリのキーに関連付けられているバリューが指定したクラスにキャスト出来るかチェックします。
     *
     * @param <K>  キーの型
     * @param <V>  バリューの型
     * @param type バリューのクラスオブジェクト
     * @param key  キーオブジェクト
     *
     * @return キャスト出来る場合はtrue キャスト出来ない場合はfalse
     */
    public <K, V> boolean canCast(Class<V> type, K key) {
        Optional<V> obj = getEntry(type, key);
        return obj.isPresent();
    }

    /**
     * 指定したエントリが存在するかチェックします
     *
     * @param <K> キーの型
     * @param <V> バリューの型
     * @param key キーオブジェクト
     *
     * @return 存在する場合はtrue
     */
    public <K, V> boolean hasKey(K key) {
        return map.containsKey(key);
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if ( obj == null ) {
            return false;
        }
        if ( !(obj instanceof TypeSafeDataStore) ) {
            return false;
        }
        return map.equals(((TypeSafeDataStore) obj).map);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        map.entrySet()
                .stream()
                .map(entry -> String.format("Key=\"%s\", Value=\"%s\"%n", entry.getKey(), entry.getValue()))
                .forEach(builder::append);
        return builder.toString();
    }
}
