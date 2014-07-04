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
package net.nokok.twitduke.xsi.shindanmaker;

import net.nokok.twitduke.core.type.AsyncTaskOnSuccess;
import net.nokok.twitduke.core.type.ThrowableReceivable;

/**
 * 診断メーカーの自動診断に必要なメソッドを定義するインターフェースです
 *
 */
public interface Shindanmaker {

    /**
     * 診断メーカーにリクエストを送信します
     *
     * @param shindanmakerURL 診断メーカーのURL
     * @param name            診断メーカーを実行するID
     */
    void sendRequest(String shindanmakerURL, String name);

    /**
     * エラーが発生した時に呼ばれます
     *
     * @param receiver Throwableが受信可能なオブジェクト
     */
    void onError(ThrowableReceivable receiver);

    /**
     * 成功した時に呼ばれます
     *
     * @param result String型を受け取れるオブジェクト
     */
    void onSuccess(AsyncTaskOnSuccess<String> result);
}
