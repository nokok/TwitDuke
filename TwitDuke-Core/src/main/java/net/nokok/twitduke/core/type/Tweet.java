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
package net.nokok.twitduke.core.type;

import java.io.Serializable;
import net.nokok.twitduke.base.type.Retrievable;

/**
 * ツイート(String)のラッパクラスです
 *
 */
public class Tweet implements Cloneable, Serializable, Retrievable<String> {

    private static final long serialVersionUID = -669020520967130965L;

    private final String text;

    /**
     * 渡された文字列からツイートオブジェクトを生成します
     *
     * @param text ツイートを生成する文字列
     */
    public Tweet(String text) {
        if ( text == null ) {
            throw new NullPointerException("渡された文字列がnullです");
        }
        if ( text.length() > 140 ) {
            throw new IllegalArgumentException("140文字を超えています");
        }
        if ( text.isEmpty() ) {
            throw new IllegalArgumentException("ツイートが空です");
        }
        this.text = text;
    }

    /**
     * 渡された文字列とフッターでツイートオブジェクトを生成します
     *
     * @param tweet
     * @param footer
     */
    public Tweet(String tweet, Footer footer) {
        this(tweet + " " + footer.toString());
    }

    /**
     * このツイートオブジェクトにフッターを付けたツイートオブジェクトを新たに生成して返します
     *
     * @param footer フッター
     *
     * @return フッター付きツイート
     */
    public Tweet applyFooter(Footer footer) {
        return new Tweet(text, footer);
    }

    @Override
    public String get() {
        return text;
    }

    @Override
    public String toString() {
        return text;
    }

    @Override
    public int hashCode() {
        return text.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if ( obj == null || (obj instanceof Tweet) ) {
            return false;
        }
        Tweet tweet = (Tweet) obj;
        return this.text.equals(tweet.text);
    }

    @Override
    protected Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException ex) {
            throw new InternalError(ex);
        }
    }
}
