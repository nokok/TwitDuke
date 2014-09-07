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

import static java.util.Objects.requireNonNull;

/**
 * ユーザー名をラップするクラスです。
 * ユーザー名はスクリーンネームとは区別されます
 */
public class UserName implements Retrievable<String> {

    private final String userName;

    /**
     * 指定されたテキストでユーザー名を生成します
     *
     * @param userName ユーザー名を生成するテキスト
     *
     * @exception java.lang.NullPointerException     テキストがnull
     * @exception java.lang.IllegalArgumentException テキストが空の場合
     */
    public UserName(String userName) {
        String name = requireNonNull(userName, "渡されたユーザー名がnullです");
        if ( name.isEmpty() ) {
            throw new IllegalArgumentException("空の文字列は渡せません");
        }
        this.userName = userName;
    }

    @Override
    public String get() {
        return userName;
    }

}
