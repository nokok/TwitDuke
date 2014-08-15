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

import java.util.Objects;
import javafx.scene.input.KeyCombination;

/**
 * Created by wtnbsts on 2014/07/23.
 */
public class KeyBind implements Comparable<KeyBind> {

    /**
     * キー入力
     */
    private final KeyCombination keyStroke;
    /**
     * キーボードショートカットが有効になるコンポーネントの条件 (現在はクラス名の完全一致)
     * 例) net.nokok.twitduke.components.TWButton
     */
    private final String selector;

    /**
     * キーボードショートカットのキー設定と、
     * ショートカットが有効になるコンポーネントの条件を取得してインスタンスを生成する
     *
     * @param shortcutKeyBind ショートカットキー
     * @param selector        キーボードショートカットが有効になるコンポーネントの条件
     *
     * @throws NullPointerException
     */
    public KeyBind(final KeyCombination shortcutKeyBind, final String selector) {
        this.keyStroke = Objects.requireNonNull(shortcutKeyBind, "shortcutKeyBind");
        this.selector = Objects.requireNonNull(selector, "selector");
    }

    /**
     * キー入力の文字列表現を取得する
     *
     * @return キー入力の文字列表現
     */
    public KeyCombination getKeyStroke() {
        return keyStroke;
    }

    /**
     * キーボードショートカットが有効になるコンポーネントの条件を取得する
     *
     * @return キーボードショートカットが有効になるコンポーネントの条件
     */
    public String getSelector() {
        return selector;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.keyStroke);
        hash = 11 * hash + Objects.hashCode(this.selector);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        final KeyBind other = (KeyBind) obj;
        if ( !Objects.equals(this.keyStroke, other.keyStroke) ) {
            return false;
        }
        if ( !Objects.equals(this.selector, other.selector) ) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(final KeyBind keyBind) {
        if ( keyBind == null ) {
            throw new NullPointerException();
        }

        int namedif = selector.compareTo(keyBind.selector);
        if ( namedif != 0 ) {
            return namedif;
        }

        int keydif = keyStroke.getName().compareTo(keyBind.keyStroke.getName());
        if ( keydif != 0 ) {
            return keydif;
        }

        return 0;
    }
}
