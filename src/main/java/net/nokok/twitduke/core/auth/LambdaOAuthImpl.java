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

import net.nokok.twitduke.core.twitter.AsyncTwitterInstanceGeneratorImpl;
import net.nokok.twitduke.core.type.ErrorMessageReceivable;
import net.nokok.twitduke.pluginsupport.event.EventWithSingleArg;
import twitter4j.AsyncTwitter;
import twitter4j.TwitterAdapter;
import twitter4j.TwitterException;
import twitter4j.TwitterMethod;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class LambdaOAuthImpl implements Runnable, LambdaOAuth {

    private AsyncTwitter asyncTwitter;
    private RequestToken requestToken;

    public LambdaOAuthImpl() {
        asyncTwitter = new AsyncTwitterInstanceGeneratorImpl().generate();
    }

    @Override
    public void gotRequestToken(EventWithSingleArg<RequestToken> requestTokenListener) {
        this.asyncTwitter.addListener(new TwitterAdapter() {

            @Override
            public void gotOAuthRequestToken(RequestToken token) {
                requestToken = token;
                requestTokenListener.onEvent(token);
            }

        });
    }

    @Override
    public void gotAccessToken(EventWithSingleArg<AccessToken> accessTokenListener) {
        this.asyncTwitter.addListener(new TwitterAdapter() {

            @Override
            public void gotOAuthAccessToken(AccessToken token) {
                accessTokenListener.onEvent(token);
            }

        });
    }

    @Override
    public void setPin(String pin) {
        asyncTwitter.getOAuthAccessTokenAsync(requestToken, pin);
    }

    @Override
    public void onException(ErrorMessageReceivable errorListener) {
        this.asyncTwitter.addListener(new TwitterAdapter() {

            @Override
            public void onException(TwitterException te, TwitterMethod method) {
                errorListener.onError(te.getErrorMessage());
            }

        });
    }

    @Override
    public void run() {
        asyncTwitter = new AsyncTwitterInstanceGeneratorImpl().generate();
        asyncTwitter.getOAuthRequestTokenAsync();
    }
}
