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
package net.nokok.twitduke.core.io;

import net.nokok.twitduke.base.io.Writer;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.UncheckedIOException;
import twitter4j.auth.AccessToken;

/**
 * アクセストークンをシリアライズしてファイルとして書き込みます。
 *
 */
class AccessTokenSerializer implements Writer<AccessToken> {

    private final String PATH;

    /**
     * 指定したパスにアクセストークンを保存するAccessTokenSerializerを構築します
     *
     * @param path アクセストークンを保存するファイル名を含んだパス
     */
    public AccessTokenSerializer(String path) {
        this.PATH = path;
    }

    /**
     * アクセストークンを格納されているスクリーンネームの名前でディスクに書き込みます
     *
     * @param accessToken アクセストークン
     *
     * @exception UncheckedIOException アクセストークンの書き込みに失敗した場合
     */
    @Override
    public void write(AccessToken accessToken) {
        try (ObjectOutputStream objectOutputStream
                                = new ObjectOutputStream(new FileOutputStream(PATH))) {
            objectOutputStream.writeObject(accessToken);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
