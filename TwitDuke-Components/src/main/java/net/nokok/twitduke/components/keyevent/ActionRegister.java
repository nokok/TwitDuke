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
import javafx.scene.Node;

/**
 * キーボードショートカットをGUIパーツに登録するメソッドを提供するインターフェース
 * Created by wtnbsts on 2014/07/26.
 */
public interface ActionRegister {

    /**
     * キーボードショートカットを設定するノードを指定して、キーボードショートカット設定インスタンスを生成する
     * あとで、削除する
     *
     * @param root キーボードを設定したいノード
     *
     * @return ショートカット設定インスタンス
     */
    @Deprecated
    static ActionRegister newInstance(final Node root) {
        return new JavaFXActionRegister(root);
    }

    /**
     * キーボードショートカット設定を適用する
     *
     * @param setting キーボードショートカット設定
     */
    void registerKeyMap(final KeyMapSetting setting);

    /**
     * キーボードショートカット設定を解除する
     */
    void unregisterAll();

    /**
     * {@link ActionRegister#registerKeyMap(net.nokok.twitduke.components.keyevent.KeyMapSetting) },
     * {@link ActionRegister#unregisterAll() } のいずれかで発生した例外の一覧を取得する
     *
     * @return 例外のリスト
     */
    List<Exception> getErrors();

    /**
     * 発生した例外の履歴を削除する
     */
    void clearErrors();
}
