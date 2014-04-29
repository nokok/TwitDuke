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
package net.nokok.twitduke.core.api.twitter;

import twitter4j.AsyncTwitter;
import twitter4j.auth.AccessToken;

/**
 * AsyncTwitterインスタンスを生成するメソッドを定義するインターフェースです
 * <p>
 * @author noko <nokok.kz at gmail.com>
 */
public interface AsyncTwitterInstanceGenerator {

    /**
     * デフォルトのキーを使用してAsyncTwitterオブジェクトを生成します
     * <p>
     * @return デフォルトのキーを使用して生成されたAsyncTwitterオブジェクト
     */
    AsyncTwitter generate();

    /**
     * 渡されたキーを使用してAsyncTwitterオブジェクトを生成します
     * <p>
     * @param consumer       コンシューマーキー
     * @param consumerSecret コンシューマーシークレットキー
     * <p>
     * @return 独自のキーを使用して生成されたAsyncTwitterオブジェクト
     */
    AsyncTwitter generate(String consumer, String consumerSecret);

    /**
     * 渡されたアクセストークンを使用してAsyncTwitterオブジェクトを生成します
     * <p>
     * @param accessToken AsyncTwitterオブジェクトを生成するAccessToken
     * <p>
     * @return 渡されたアクセストークンを使用して生成されたAsyncTwitterオブジェクト
     */
    AsyncTwitter generate(AccessToken accessToken);
}
