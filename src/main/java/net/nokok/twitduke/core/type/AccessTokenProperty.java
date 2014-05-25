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
package net.nokok.twitduke.core.type;

import java.util.Optional;
import java.util.Properties;
import twitter4j.auth.AccessToken;

/**
 * アクセストークンをプロパティオブジェクトとして保持します
 *
 */
public class AccessTokenProperty {

    private final Properties properties;
    private final AccessToken accessToken;
    private final String TOKEN_KEY = "token";
    private final String TOKEN_SECRET_KEY = "tokensecret";
    private final String SCREEN_NAME_KEY = "name";
    private final String USER_ID_KEY = "id";

    /**
     * 指定されたアクセストークンからアクセストークンプロパティを構築します
     *
     * @param accessToken
     */
    public AccessTokenProperty(AccessToken accessToken) {
        this.accessToken = accessToken;
        properties = new Properties();
        properties.put(TOKEN_KEY, accessToken.getToken());
        properties.put(TOKEN_SECRET_KEY, accessToken.getTokenSecret());
        properties.put(SCREEN_NAME_KEY, Optional.ofNullable(accessToken.getScreenName()).orElse(""));
        properties.put(USER_ID_KEY, accessToken.getUserId());
    }

    /**
     * 指定されたプロパティからアクセストークンプロパティを構築します
     *
     * @param properties
     */
    public AccessTokenProperty(Properties properties) {
        String token = Optional.of(properties.getProperty(TOKEN_KEY)).orElseThrow(IllegalArgumentException::new);
        String secret = Optional.of(properties.getProperty(TOKEN_SECRET_KEY)).orElseThrow(IllegalArgumentException::new);
        Optional.of(properties.getProperty(SCREEN_NAME_KEY)).orElseThrow(IllegalArgumentException::new);
        Optional.of(properties.getProperty(USER_ID_KEY)).orElseThrow(IllegalArgumentException::new);
        this.properties = properties;
        accessToken = new AccessToken(token, secret);
    }

    /**
     * 構築されたプロパティを返します
     *
     * @return プロパティ
     */
    public Properties toProperties() {
        return properties;
    }

    public String token() {
        return properties.getProperty(TOKEN_KEY);
    }

    public String tokenSecret() {
        return properties.getProperty(TOKEN_SECRET_KEY);
    }

    public String screenName() {
        return properties.getProperty(SCREEN_NAME_KEY);
    }

    public long id() {
        return Long.parseLong(properties.getProperty(USER_ID_KEY));
    }

    public AccessToken toAccessToken() {
        return accessToken;
    }
}
