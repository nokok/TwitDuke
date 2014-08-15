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

import java.io.InputStream;
import java.io.OutputStream;

/**
 * キーボードショートカット設定を、外部環境からload&saveするメソッドを提供するインターフェース
 * Created by wtnbsts on 2014/07/24.
 */
public interface KeyMapStore {

    /**
     * このインターフェースを実装したオブジェクトを生成する
     * あとで削除する
     *
     * @return オブジェクト
     */
    @Deprecated
    static KeyMapStore newInstance() {
        return new XmlKeyMapStore();
    }

    /**
     * 設定を外部から読み込む
     *
     * @param source 設定の入力元ストリーム
     *
     * @return 読み込んだ設定情報
     *
     * @throws Exception IOException当たりが考えられそう
     */
    KeyMapSetting load(final InputStream source) throws Exception;

    /**
     * 設定を外部へ出力する
     *
     * @param dist    設定の出力先ストリーム
     * @param setting 出力する設定情報
     *
     * @return true : 成功
     *         false : なんか失敗？
     *
     * @throws Exception
     */
    boolean save(final OutputStream dist, final KeyMapSetting setting) throws Exception;
}
