package net.nokok.twitduke.components.keyevent;

import java.util.List;
import java.util.Map;

/**
 * Created by wtnbsts on 2014/07/23.
 */
public interface IKeyMapSetting {

    /**
     * キーマップ設定の名前を取得する
     *
     * @return
     */
    String getSettingName();

    /**
     * 登録されているコマンド名一覧を取得する
     *
     * @return
     */
    List<String> getCommandIds();

    /**
     * コマンド名と、コマンドクラスを指定して、新しくコマンドを登録する
     *
     * @param id               追加したいコマンド名 すでに存在する場合、何もせずにfalseが帰る
     * @param commandClassName 実行コマンドのクラス名 クラスの存在確認等はしない ただし、空文字列とnullぽは例外が発生する
     * @return true: コマンド作成に成功
     * false: コマンド作成に失敗 (すでに存在するコマンド名)
     * @throws java.lang.IllegalArgumentException 空文字列を渡すと発生する
     */
    boolean addCommand(final String id, final String commandClassName)
            throws IllegalArgumentException;

    /**
     * コマンドを削除する
     *
     * @param id 　削除したいコマンド名
     * @return true: 削除成功
     * false: 削除失敗
     */
    boolean removeCommand(final String id) throws IllegalArgumentException;

    /**
     * コマンド名に紐付けられている実行コマンドのクラス名を取得する
     *
     * @param id コマンド名
     * @return 実行コマンドのクラス名
     */
    String getCommandClassName(final String id);

    /**
     * コマンドとキー入力をひもづける ＊このメソッドを呼ぶ前にaddCommandメソッドでコマンドを登録しておくこと！
     *
     * @param id      コマンド名
     * @param keyBind
     * @return
     * @throws java.lang.IllegalArgumentException 不正なキーバインド
     */
    boolean addKeyBind(final String id, final KeyBind keyBind)
            throws IllegalArgumentException;

    /**
     * @param id
     * @param keyBinds
     * @return
     * @throws IllegalArgumentException
     * @see IKeyMapSetting#addKeyBind(String, KeyBind)
     */
    void addKeyBinds(final String id, final List<KeyBind> keyBinds) throws IllegalArgumentException;

    /**
     * コマンドとキー入力のひもづけを切る
     *
     * @param id
     * @param keyBind
     * @return
     */
    boolean removeKeyBind(final String id, final KeyBind keyBind) throws IllegalArgumentException;

    /**
     * コマンドに登録されているキーバインド一覧を取得する
     *
     * @param id
     * @return
     */
    List<KeyBind> getKeyBinds(final String id) throws IllegalArgumentException;

    /**
     * コンポーネントに登録するキーバインド一覧を取得する
     *
     * @param targetComponentName
     * @return
     * @throws IllegalArgumentException
     */
    Map<String, List<KeyBind>> collectKeyBinds(final String targetComponentName)
            throws IllegalArgumentException;
}
