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
import java.nio.file.Path;
import java.util.Properties;
import net.nokok.twitduke.core.type.ScreenName;
import twitter4j.auth.AccessToken;

public class AccountModel {

    private final AccessToken accessToken;
    private final ScreenName screenName;
    private final boolean isPrimary;

    public AccountModel(AccessToken accessToken, ScreenName screenName, boolean isPrimary) {
        this.accessToken = accessToken;
        this.screenName = screenName;
        this.isPrimary = isPrimary;
    }

    public Path tokenPath() {
        return new File(DirectoryHelper.getAccountDirectory(screenName.get()), AccountPath.TOKEN_FILE_NAME).toPath();
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public Properties toProperties() {
        Properties p = new Properties();
        p.put(PropertyKey.USER_ID, String.valueOf(accessToken.getUserId()));
        p.put(PropertyKey.SCREEN_NAME, screenName.get());
        p.put(PropertyKey.TOKEN, accessToken.getToken());
        p.put(PropertyKey.TOKEN_SECRET, accessToken.getTokenSecret());
        p.put(PropertyKey.PRIMARY_ACCOUNT, String.valueOf(isPrimary));
        return p;
    }
}
