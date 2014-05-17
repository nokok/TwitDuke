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

import net.nokok.twitduke.core.auth.OAuthOnError;
import net.nokok.twitduke.core.auth.OAuthOnSuccess;
import net.nokok.twitduke.core.auth.OAuthRunnable;
import net.nokok.twitduke.core.auth.TwitterAuthentication;
import net.nokok.twitduke.core.auth.TwitterAuthenticationListener;
import twitter4j.auth.AccessToken;

/**
 * コールバック方式で認証を行います。
 *
 * クライアントアプリはPIN認証をするべきとの指摘からこのクラスは非推奨となりました。
 * 
 */
@Deprecated
public class OAuthWithCallback implements OAuthRunnable {

    private OAuthOnError error;
    private OAuthOnSuccess success;

    @Override
    public void onError(OAuthOnError onError) {
        this.error = onError;
    }

    @Override
    public void onSuccess(OAuthOnSuccess onSuccess) {
        this.success = onSuccess;
    }

    @Override
    public void startOAuth() {
        TwitterAuthentication authentication = new AutoAuthentication();
        authentication.setListener(new TwitterAuthenticationListener() {

            @Override
            public void error(String errorMessage) {
                error.onError(errorMessage);
            }

            @Override
            public void success(AccessToken accessToken) {
                success.onSuccess(accessToken);
            }
        });
        authentication.start();
    }

}
