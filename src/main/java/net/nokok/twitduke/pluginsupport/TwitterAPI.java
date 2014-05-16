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

import net.nokok.twitduke.core.api.twitter.SendDMAPI;
import net.nokok.twitduke.core.api.twitter.SendTweetAPI;
import net.nokok.twitduke.core.api.twitter.TwitterExceptionReceivable;
import net.nokok.twitduke.core.api.twitter.UpdateProfile;
import net.nokok.twitduke.core.impl.twitter.AsyncTwitterInstanceGeneratorImpl;
import net.nokok.twitduke.core.type.ErrorMessageReceivable;
import net.nokok.twitduke.core.type.ScreenName;
import net.nokok.twitduke.core.type.Tweet;
import twitter4j.AsyncTwitter;
import twitter4j.TwitterAdapter;
import twitter4j.TwitterException;
import twitter4j.TwitterMethod;
import twitter4j.auth.AccessToken;

/**
 * プラグイン側で利用可能なTwitter4jのラッパーです。
 *
 * @author noko
 */
public class TwitterAPI implements SendTweetAPI, SendDMAPI, UpdateProfile, TwitterExceptionReceivable {

    private final AsyncTwitter asyncTwitter;
    private ErrorMessageReceivable receivable;

    public TwitterAPI(AccessToken accessToken) {
        asyncTwitter = new AsyncTwitterInstanceGeneratorImpl().generate(accessToken);
    }

    @Override
    public void onError(ErrorMessageReceivable receivable) {
        this.receivable = receivable;
        asyncTwitter.addListener(new TwitterAdapter() {

            @Override
            public void onException(TwitterException te, TwitterMethod method) {
                receivable.onError(te.getErrorMessage());
            }

        });
    }

    @Override
    public void sendDM(ScreenName screenName, Tweet tweet) {
        asyncTwitter.sendDirectMessage(screenName.get(), tweet.get());
    }

    @Override
    public void sendTweet(Tweet tweet) {
        asyncTwitter.updateStatus(tweet.get());
    }

    @Override
    public void updateBio(String bio) {
        asyncTwitter.updateProfile(null, null, null, bio);
    }

    @Override
    public void updateLocation(String location) {
        asyncTwitter.updateProfile(null, null, location, null);
    }

    @Override
    public void updateName(String name) {
        asyncTwitter.updateProfile(name, null, null, null);
    }

    @Override
    public void updateUrl(String url) {
        asyncTwitter.updateProfile(null, url, null, null);
    }

}
