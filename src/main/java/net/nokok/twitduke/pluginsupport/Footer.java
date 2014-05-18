/*
 * The MIT License
 *
 * Copyright 2014 noko
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
package net.nokok.twitduke.pluginsupport;

import java.util.Objects;
import net.nokok.twitduke.core.type.Retrievable;
import net.nokok.twitduke.core.type.Tweet;

/**
 * フッター機能を持つオブジェクトです
 *
 */
public class Footer implements Retrievable<String> {

    private final String footer;

    /**
     * 指定したテキストでフッタークラスをを構築します。
     *
     * @param footer フッターにするテキスト
     */
    public Footer(String footer) {
        this.footer = Objects.requireNonNull(footer);
    }

    /**
     * 指定したツイートオブジェクトにフッターを付けて返します
     *
     * @param tweet フッターを付けるツイートオブジェクト
     *
     * @return フッターが付いたツイートオブジェクト
     *
     * @exception java.lang.IllegalArgumentException ツイートオブジェクトの文字数とフッターの文字数の合計が140文字を超える場合
     */
    public Tweet applyFooter(Tweet tweet) {
        return applyFooter(tweet.get());
    }

    /**
     * 指定したテキストにフッターを付けて返します
     *
     * @param tweet フッターを付けるテキスト
     *
     * @return フッターが付いたツイートオブジェクト
     *
     * @exception java.lang.IllegalArgumentException ツイートオブジェクトの文字数とフッターの文字数の合計が140文字を超える場合
     */
    public Tweet applyFooter(String tweet) {
        return new Tweet(tweet + footer);
    }

    /**
     * 現在指定されているフッターを返します
     *
     * @return フッター
     */
    @Override
    public String get() {
        return footer;
    }
}
