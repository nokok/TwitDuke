/*
 * The MIT License
 *
 * Copyright 2014 noko <nokok.kz at gmail.com>.
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
package net.nokok.twitduke.core.api.auth;

/**
 * OAuth認証を実行できます。TwitterAuthenticationインターフェースと同じ役割ですが、
 * このインターフェースを用いるとラムダ式による記述が可能になります。
 * <p>
 * @see TwitterAuthentication
 */
public interface OAuthRunnable {

    /**
     * 認証処理を開始します
     */
    void startOAuth();

    /**
     * 認証が成功した時に呼ばれます
     * <p>
     * @param onSuccess 認証が成功した時に実行されるリスナー
     */
    void onSuccess(OAuthOnSuccess onSuccess);

    /**
     * 認証が失敗した時に呼ばれます
     * <p>
     * @param onError 認証が失敗した時に実行されるリスナー
     */
    void onError(OAuthOnError onError);
}
