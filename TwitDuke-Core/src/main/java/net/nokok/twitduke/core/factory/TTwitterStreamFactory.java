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
package net.nokok.twitduke.core.factory;

import net.nokok.twitduke.core.ShutdownHook;
import net.nokok.twitduke.core.twitter.ConfigurationProvider;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.auth.AccessToken;

/**
 * ストリームを生成するstaticファクトリーメソッドが定義されています。
 * このクラスを使用するとストリームを実装の影響なしで生成することが出来ます。
 *
 */
public class TTwitterStreamFactory {

    /**
     * ストリームを新たに生成します
     *
     * @param accessToken アクセストークン
     *
     * @return 生成されたストリーム
     */
    public static TwitterStream newInstance(AccessToken accessToken) {
        final TwitterStream twitterStream = new TwitterStreamFactory(ConfigurationProvider.getConfiguration()).getInstance(accessToken);
        ShutdownHook.INSTANCE.addHook("Twitter Stream Shutdown Thread", twitterStream::shutdown);
        return twitterStream;
    }
}
