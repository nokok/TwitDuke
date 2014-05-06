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
package net.nokok.twitduke.core.impl.twitter;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.Configuration;

/**
 * 同期通信でAPIを操作するTwitterインスタンスを生成するメソッドを定義したクラスです。
 *
 */
public class TwitterInstanceGenerator {

    private TwitterInstanceGenerator() {

    }

    /**
     * Consumer/ConsumerSecretキーがセットされたアクセストークン無しのTwitterインスタンスを生成します。
     *
     * アクセストークンがセットされていないためこのままでは利用できません。
     * 認証前でアクセストークンが必要な場合などに呼び出します。
     *
     * @return アクセストークン無しのTwitterインスタンス
     */
    public static Twitter newInstance() {
        Configuration configuration = ConfigurationProvider.getConfiguration();
        Twitter twitter = new TwitterFactory(configuration).getInstance();
        return twitter;
    }

    /**
     * 渡されたアクセストークンを使用してTwitterインスタンスを生成します。
     *
     * このメソッドを使用して生成されたTwitterインスタンスはAPIの操作に使用可能です。
     *
     * @param accessToken アクセストークン
     *
     * @return アクセストークンがセットされたTwitterインスタンス
     */
    public static Twitter newInstance(AccessToken accessToken) {
        Configuration configuration = ConfigurationProvider.getConfiguration();
        Twitter twitter = new TwitterFactory(configuration).getInstance(accessToken);
        return twitter;
    }
}
