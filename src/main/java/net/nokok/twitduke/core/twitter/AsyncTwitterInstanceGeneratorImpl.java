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
package net.nokok.twitduke.core.twitter;

import twitter4j.AsyncTwitter;
import twitter4j.AsyncTwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

/**
 * このクラスは実際にAsyncTwitterオブジェクトを生成して返すメソッドを提供します。
 * 
 */
public class AsyncTwitterInstanceGeneratorImpl implements AsyncTwitterInstanceGenerator {

    private final Configuration configuration = ConfigurationProvider.getConfiguration();

    @Override
    public AsyncTwitter generate() {
        return new AsyncTwitterFactory(configuration).getInstance();
    }

    @Override
    public AsyncTwitter generate(String consumer, String consumerSecret) {
        return new AsyncTwitterFactory(new ConfigurationBuilder()
                .setOAuthConsumerKey(consumer)
                .setOAuthConsumerSecret(consumerSecret)
                .build()).getInstance();
    }

    @Override
    public AsyncTwitter generate(AccessToken accessToken) {
        return new AsyncTwitterFactory(configuration).getInstance(accessToken);
    }
}
