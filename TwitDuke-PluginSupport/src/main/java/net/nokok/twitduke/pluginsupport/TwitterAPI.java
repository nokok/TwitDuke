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

import net.nokok.twitduke.core.factory.TAsyncTwitterFactory;
import net.nokok.twitduke.core.twitter.DMSendable;
import net.nokok.twitduke.core.twitter.TweetSendable;
import net.nokok.twitduke.core.twitter.TwitterExceptionReceivable;
import net.nokok.twitduke.core.twitter.UpdateProfile;
import net.nokok.twitduke.core.type.Footer;
import net.nokok.twitduke.core.type.ScreenName;
import net.nokok.twitduke.base.async.ThrowableReceivable;
import net.nokok.twitduke.core.type.Tweet;
import twitter4j.AsyncTwitter;
import twitter4j.TwitterAdapter;
import twitter4j.TwitterException;
import twitter4j.TwitterMethod;
import twitter4j.auth.AccessToken;

/**
 * プラグイン側で利用可能なTwitter4jのラッパーです。
 *
 */
public class TwitterAPI implements TweetSendable, DMSendable, UpdateProfile, TwitterExceptionReceivable {

    private final AsyncTwitter asyncTwitter;

    public TwitterAPI(AccessToken accessToken) {
        asyncTwitter = TAsyncTwitterFactory.newInstance(accessToken);
    }

    public TwitterAPI(AsyncTwitter asyncTwitter) {
        this.asyncTwitter = asyncTwitter;
    }

    @Override
    public void onException(ThrowableReceivable receiver) {
        asyncTwitter.addListener(new TwitterAdapter() {

            @Override
            public void onException(TwitterException te, TwitterMethod method) {
                receiver.onError(te);
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
    public void sendTweet(Tweet tweet, Footer footer) {
        asyncTwitter.updateStatus(tweet.applyFooter(footer).get());
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
