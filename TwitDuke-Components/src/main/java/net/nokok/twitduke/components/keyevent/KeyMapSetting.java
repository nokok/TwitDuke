package net.nokok.twitduke.components.keyevent;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by wtnbsts on 2014/07/23.
 */
public interface KeyMapSetting {

    /**
     * キーマップ設定の名前を取得する
     *
     * @return
     */
    Optional<String> getSettingName();

    /**
     * 登録されているコマンド名一覧を取得する
     *
     * @return
     */
    Optional<List<String>> getCommandIds();

    /**
     * コマンド名と、コマンドクラスを指定して、新しくコマンドを登録する
     *
     * @param id               追加したいコマンド名 すでに存在する場合、何もせずにfalseが帰る
     * @param commandClassName 実行コマンドのクラス名 クラスの存在確認等はしない ただし、空文字列とnullぽは例外が発生する
     *
     * @return true: コマンド作成に成功
     *         false: コマンド作成に失敗 (すでに存在するコマンド名)
     *
     */
    boolean addCommand(final String id, final String commandClassName);

    /**
     * コマンドを削除する
     *
     * @param id 削除したいコマンド名
     *
     * @return true: 削除成功
     *         false: 削除失敗
     */
    boolean removeCommand(final String id);

    /**
     * コマンド名に紐付けられている実行コマンドのクラス名を取得する
     *
     * @param id コマンド名
     *
     * @return 実行コマンドのクラス名
     */
    Optional<String> getCommandClassName(final String id);

    /**
     * コマンドとキー入力をひもづける ＊このメソッドを呼ぶ前にaddCommandメソッドでコマンドを登録しておくこと！
     *
     * @param id      コマンド名
     * @param keyBind
     *
     * @return
     *
     */
    boolean addKeyBind(final String id, final KeyBind keyBind);

    /**
     * @param id
     * @param keyBinds
     *
     * @see KeyMapSetting#addKeyBind(String, KeyBind)
     */
    void addKeyBinds(final String id, final List<KeyBind> keyBinds);

    /**
     * コマンドとキー入力のひもづけを切る
     *
     * @param id
     * @param keyBind
     *
     * @return
     */
    boolean removeKeyBind(final String id, final KeyBind keyBind);

    /**
     * コマンドに登録されているキーバインド一覧を取得する
     *
     * @param id
     *
     * @return
     */
    Optional<List<KeyBind>> getKeyBinds(final String id);

    /**
     * コンポーネントに登録するキーバインド一覧を取得する
     *
     * @param targetSelector
     *
     * @return
     *
     */
    Optional<Map<String, List<KeyBind>>> collectKeyBinds(final String targetSelector);
}
