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

import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

/**
 * 認証関連の処理で必要な設定を供給するクラスです
 *
 */
public class ConfigurationProvider {

    private ConfigurationProvider() {
    }

    /**
     * TwitDukeのConsumer/ConsumerSecretキーがセットされたConfigurationオブジェクトを返します。
     * このオブジェクトにはアクセストークンがセットされていないためこのままでは利用できません
     *
     * @return Consumer/ConsumerSecretキーがセットされたTwitDukeConfigurationオブジェクト
     */
    public static Configuration getConfiguration() {
        return new ConfigurationBuilder()
            .setOAuthConsumerKey("VOIW6nzPVPEGyILu0kgMRQ")
            .setOAuthConsumerSecret("x42tjv2Xrzsi3p5hfiGSYSiNLfa7VZv8Ozd0VHEaQ")
            .build();
    }
}
