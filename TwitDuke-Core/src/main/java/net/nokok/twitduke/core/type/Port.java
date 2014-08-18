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

import net.nokok.twitduke.base.type.Retrievable;

/**
 * ポート番号を表現するクラスです。
 * intをラップします。
 */
public class Port implements Retrievable<Integer> {

    private final int value;

    /**
     * ポート番号を表現するオブジェクトを構築します
     *
     * @param port ポート番号
     *
     * @exception IllegalArgumentException 使用可能なポート番号(0-65535)の範囲外の場合
     */
    public Port(int port) {
        if ( port < 0 || port > 65535 ) {
            throw new IllegalArgumentException("正しいポート番号(0-65535)ではありません: " + port);
        }
        this.value = port;
    }

    /**
     * ポート番号を表現するオブジェクトを文字列から構築します。
     * 指定する文字列は数字のみが格納されている必要があります。
     *
     * @param port ポート番号
     *
     * @exception NumberFormatException 数値に変換出来ない文字が含まれている場合
     */
    public Port(String port) {
        this(Integer.parseInt(port));
    }

    @Override
    public Integer get() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public int hashCode() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if ( obj == null ) {
            return false;
        }
        if ( !(obj instanceof Port) ) {
            return false;
        }
        return obj.toString().equals(this.toString());
    }
}
