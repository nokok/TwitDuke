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

import net.nokok.twitduke.core.type.ThrowableReceivable;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

/**
 * ユーザー情報の変更をします
 *
 */
public class UpdateProfileImpl implements UpdateProfile {

    private final Twitter user;
    private ThrowableReceivable resultOnError;

    /**
     * 渡されたTwitterオブジェクトで認証されているユーザー情報を変更します。
     *
     * @param twitter 認証済みのTwitterオブジェクト
     *
     * @exception java.lang.NullPointerException nullを渡した場合
     * @exception IllegalArgumentException       渡されたTwitterオブジェクトの認証が完了していない場合
     */
    public UpdateProfileImpl(Twitter twitter) {
        if ( twitter == null ) {
            throw new NullPointerException("渡されたTwitterオブジェクトがnullです");
        }
        if ( !twitter.getAuthorization().isEnabled() ) {
            throw new IllegalArgumentException("渡されたTwitterオブジェクトは認証が完了していません。");
        }
        this.user = twitter;
    }

    public UpdateProfileImpl(AccessToken accessToken) {
        if ( accessToken == null ) {
            throw new NullPointerException("渡されたアクセストークンがnullです");
        }
        this.user = new TwitterFactory(ConfigurationProvider.getConfiguration()).getInstance(accessToken);
    }

    @Override
    public void updateName(String name) {
        try {
            user.updateProfile(name, null, null, null);
        } catch (TwitterException e) {
            resultOnError.onError(e);
        }
    }

    @Override
    public void updateUrl(String url) {
        try {
            user.updateProfile(null, url, null, null);
        } catch (TwitterException e) {
            resultOnError.onError(e);
        }
    }

    @Override
    public void updateLocation(String location) {
        try {
            user.updateProfile(null, null, location, null);
        } catch (TwitterException e) {
            resultOnError.onError(e);
        }
    }

    @Override
    public void updateBio(String bio) {
        try {
            user.updateProfile(null, null, null, bio);
        } catch (TwitterException e) {
            resultOnError.onError(e);
        }
    }

    @Override
    public void onError(ThrowableReceivable receivable) {
        this.resultOnError = receivable;
    }
}
