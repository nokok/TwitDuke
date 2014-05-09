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
package net.nokok.twitduke.pluginsupport;

import net.nokok.twitduke.core.api.twitter.SendTweetAPI;
import net.nokok.twitduke.core.impl.twitter.AsyncTwitterInstanceGeneratorImpl;
import net.nokok.twitduke.core.type.Tweet;
import twitter4j.AsyncTwitter;
import twitter4j.auth.AccessToken;

/**
 * 指定されたフッターを追加してつぶやく機能を提供するクラスです。
 * フッターを指定しなかった場合は渡されたツイートをそのままつぶやきます。
 * 一度フッターを指定するとこのオブジェクトが破棄されるまでフッター文字列は保持されます。
 * フッターを指定しなおした場合や、リセットした場合は古いフッターが破棄され
 * 新しいフッターに置き換わります。
 *
 * 渡されたツイートとフッターを連結した時の長さが140文字を超える場合は
 * IllegalArgumentExceptionがTweetクラスでスローされます。
 * また、つぶやきの送信は非同期で行われ、エラーは無視されます。
 */
public class Footer implements SendTweetAPI {

    private final AsyncTwitter twitter;
    private Tweet footer = new Tweet("");

    /**
     * 指定したアクセストークンでFooterオブジェクトを生成します。
     *
     * @param accessToken アクセストークン
     */
    public Footer(AccessToken accessToken) {
        this.twitter = new AsyncTwitterInstanceGeneratorImpl().generate(accessToken);
    }

    /**
     * 指定したTwitterインスタンスでFooterオブジェクトを生成します
     *
     * @param twitter Twitterインスタンス
     */
    public Footer(AsyncTwitter twitter) {
        this.twitter = twitter;
    }

    /**
     * 渡されたツイートに指定されたフッターを連結してつぶやきを送信します。
     * 合計して140文字を超えた場合はIllegalArgumentExceptionがスローされます。
     *
     * @param tweet ツイート
     *
     * @exception IllegalArgumentException 渡されたツイートとフッターを合わせた時に140文字を超える場合
     */
    @Override
    public void sendTweet(Tweet tweet) {
        Tweet tweetWithFooter = new Tweet(tweet.get() + footer.get());
        twitter.updateStatus(tweetWithFooter.get());
    }

    /**
     * フッターテキストを指定します。
     *
     * @param footer フッター
     */
    public void setFooterText(String footer) {
        this.footer = new Tweet(footer);
    }

    /**
     * フッターをリセットします。
     */
    public void resetFooter() {
        this.footer = new Tweet("");
    }

    /**
     * フッターの長さを返します
     *
     * @return フッターの長さ
     */
    public int footerLength() {
        return footer.get().length();
    }
}
