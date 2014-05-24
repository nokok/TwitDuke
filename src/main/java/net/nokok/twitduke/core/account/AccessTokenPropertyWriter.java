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
package net.nokok.twitduke.core.account;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Properties;
import net.nokok.twitduke.core.type.ScreenName;
import twitter4j.auth.AccessToken;

/**
 * アクセストークンをプロパティとして書き込みます
 */
public class AccessTokenPropertyWriter implements AccessTokenWriter {

    private final File ACCESS_TOKEN;

    /**
     * 指定したスクリーンネームのアカウントのアクセストークンをデフォルトのパスに書き込むライターを構築します
     *
     * @param screenName アカウント名
     */
    public AccessTokenPropertyWriter(ScreenName screenName) {
        ACCESS_TOKEN = new File(DirectoryHelper.getAccountDirectory(screenName), AccountPath.TOKEN_FILE_NAME);
    }

    /**
     * 指定したパスとファイル名でアクセストークンを書き込むライターを構築します
     *
     * @param path
     * @param fileName
     */
    public AccessTokenPropertyWriter(String path, String fileName) {
        ACCESS_TOKEN = new File(path, fileName);
    }

    /**
     * 指定したパスにアクセストークンを書き込むライターを構築します
     *
     * @param path
     */
    public AccessTokenPropertyWriter(String path) {
        ACCESS_TOKEN = new File(path);
    }

    @Override
    public void writeAccessToken(AccessToken accessToken) {
        Properties properties = new Properties();
        properties.put(PropertyKey.TOKEN, accessToken.getToken());
        properties.put(PropertyKey.TOKEN_SECRET, accessToken.getTokenSecret());
        properties.put(PropertyKey.USER_ID, String.valueOf(accessToken.getUserId()));
        properties.put(PropertyKey.SCREEN_NAME, accessToken.getScreenName());
        try (FileOutputStream outputStream = new FileOutputStream(ACCESS_TOKEN)) {
            properties.store(outputStream, null);
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

}
