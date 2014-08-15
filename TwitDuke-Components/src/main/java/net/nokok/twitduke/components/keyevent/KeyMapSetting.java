/*
 * The MIT License
 *
 * Copyright 2014 satanabe1.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package net.nokok.twitduke.components.keyevent;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * キーボードショートカット設定を管理するメソッドを提供するインターフェース
 * {@link KeyBind}のインスタンスと、それにひもづけるコマンド名、コマンドクラス、ターゲットセレクタを管理する
 * Created by wtnbsts on 2014/07/23.
 */
public interface KeyMapSetting {

    /**
     * キーマップ設定の名前を取得する
     *
     * @return 設定名
     */
    Optional<String> getSettingName();

    /**
     * 登録されているコマンド名一覧を取得する
     *
     * @return コメンド名一覧
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
     * @param keyBind キー入力
     *
     * @return true : 登録成功
     *         false : 登録失敗
     *
     */
    boolean addKeyBind(final String id, final KeyBind keyBind);

    /**
     * コマンドにキーバインドをまとめて登録する
     *
     * @param id       コマンド名
     * @param keyBinds キー入力いろいろ
     *
     * @see KeyMapSetting#addKeyBind(String, KeyBind)
     */
    void addKeyBinds(final String id, final List<KeyBind> keyBinds);

    /**
     * コマンドとキー入力のひもづけを切る
     *
     * @param id      コマンド名
     * @param keyBind キー入力
     *
     * @return true : 削除成功
     *         false : 該当するidが存在しない
     */
    boolean removeKeyBind(final String id, final KeyBind keyBind);

    /**
     * コマンドに登録されているキーバインド一覧を取得する
     *
     * @param id コメンド名
     *
     * @return キーバインド一覧
     */
    Optional<List<KeyBind>> getKeyBinds(final String id);

    /**
     * 該当するコンポーネントに登録べきキーバインド一覧を取得する
     *
     * @param targetSelector コンポーネントのセレクタ (現状はクラス名)
     *
     * @return キーバインド一覧
     *
     */
    Optional<Map<String, List<KeyBind>>> collectKeyBinds(final String targetSelector);
}
