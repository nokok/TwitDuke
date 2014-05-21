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
package net.nokok.twitduke.core.auth;

import net.nokok.twitduke.core.type.ErrorMessageReceivable;
import net.nokok.twitduke.pluginsupport.event.EventWithSingleArg;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

/**
 * ラムダ式で認証関連の内部処理を記述できるインターフェースです
 *
 */
public interface LambdaOAuth extends Runnable {

    /**
     * アクセストークンが取得された時に呼ばれます。
     *
     * @param accessTokenListener アクセストークンに対して処理を行うオブジェクト
     */
    void gotAccessToken(EventWithSingleArg<AccessToken> accessTokenListener);

    /**
     * リクエストトークンが取得された時に呼ばれます。
     *
     * @param requestTokenListener リクエストトークンに対して処理を行うオブジェクト
     */
    void gotRequestToken(EventWithSingleArg<RequestToken> requestTokenListener);

    /**
     * エラーが発生した時に呼ばれます
     *
     * @param errorListener エラーメッセージが取得できるオブジェクト
     */
    void onException(ErrorMessageReceivable errorListener);

    /**
     * PINをセットします
     *
     * @param pin
     */
    void setPin(String pin);

}
