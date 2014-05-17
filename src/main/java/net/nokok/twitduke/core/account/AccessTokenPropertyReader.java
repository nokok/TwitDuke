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
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;
import net.nokok.twitduke.core.type.ScreenName;
import twitter4j.auth.AccessToken;

/**
 * アクセストークンをプロパティから読み込みます
 *
 */
public class AccessTokenPropertyReader implements AccessTokenReader2 {

    @Override
    public Optional<AccessToken> readAccessToken(ScreenName screenName) {
        File accountDir = DirectoryHelper.getAccountDirectory(screenName.get());
        return Optional.ofNullable(readAccessTokenUnsafe(new File(accountDir, AccountPath.TOKEN_FILE_NAME).getAbsolutePath()));
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
            long id = Long.parseLong(properties.getProperty(PropertyKey.ID));
            return new AccessToken(token, secret, id);
        } catch (IOException | NumberFormatException e) {
            return null;
        }
    }

}
