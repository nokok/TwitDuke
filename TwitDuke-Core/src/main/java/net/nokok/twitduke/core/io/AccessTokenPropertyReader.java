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
package net.nokok.twitduke.core.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Optional;
import java.util.Properties;
import net.nokok.twitduke.core.type.ScreenName;
import twitter4j.auth.AccessToken;

/**
 * アクセストークンをプロパティから読み込みます
 *
 */
public class AccessTokenPropertyReader implements AccessTokenReader {

    private final File ACCESS_TOKEN;

    /**
     * 指定したアカウントのアクセストークンをデフォルトのパスから読み込むリーダーを構築します
     *
     * @param screenName アカウント名
     */
    public AccessTokenPropertyReader(ScreenName screenName) {
        File accountDir = DirectoryHelper.createAccountDirectory(screenName.get());
        ACCESS_TOKEN = new File(accountDir, AccountPath.TOKEN_FILE_NAME);
    }

    /**
     * 指定したパスとファイル名にあるアクセストークンを読み込むリーダーを構築します
     *
     * @param path     ディレクトリのパス
     * @param fileName ファイル名
     */
    public AccessTokenPropertyReader(String path, String fileName) {
        ACCESS_TOKEN = new File(path, fileName);
    }

    /**
     * 指定したパスのアクセストークンを読み込むリーダーを構築します
     *
     * @param path
     */
    public AccessTokenPropertyReader(String path) {
        ACCESS_TOKEN = new File(path);
    }

    @Override
    public Optional<AccessToken> readAccessToken() {
        return Optional.ofNullable(readAccessTokenUnsafe(ACCESS_TOKEN.getAbsolutePath()));
    }

    /**
     * アクセストークンを指定したパスのプロパティから読み込みます。
     * 読み込みに失敗した場合nullを返します。
     *
     * @param path アクセストークンの
     *
     * @return アクセストークン
     */
    public AccessToken readAccessTokenUnsafe(String path) {
        File file = new File(path);
        Properties properties = new Properties();
        try {
            try (FileInputStream inputStream = new FileInputStream(file)) {
                properties.load(inputStream);
            }
            String token = properties.getProperty(PropertyKey.TOKEN);
            String secret = properties.getProperty(PropertyKey.TOKEN_SECRET);
            long id = Long.parseLong(properties.getProperty(PropertyKey.USER_ID));
            String screenName = properties.getProperty(PropertyKey.SCREEN_NAME);
            AccessToken accessToken = new AccessToken(token, secret, id);
            setScreenNameWithReflection(accessToken, screenName);
            return accessToken;
        } catch (IOException | NumberFormatException e) {
            return null;
        }
    }

    private void setScreenNameWithReflection(AccessToken accessToken, String screenName) {
        Class<? extends AccessToken> clazz = accessToken.getClass();
        try {
            Field field = clazz.getDeclaredField("screenName");
            field.setAccessible(true);
            field.set(accessToken, screenName);
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException e) {
            throw new RuntimeException(e);
        }
    }
}
