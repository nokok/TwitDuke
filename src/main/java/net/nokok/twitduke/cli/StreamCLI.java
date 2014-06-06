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
package net.nokok.twitduke.cli;

import net.nokok.twitduke.core.account.AccountManager;
import net.nokok.twitduke.core.factory.AccountManagerFactory;
import net.nokok.twitduke.core.twitter.PrintUserStreamListener;
import net.nokok.twitduke.core.twitter.TwitterStreamInstanceGeneratorImpl;
import twitter4j.TwitterStream;
import twitter4j.auth.AccessToken;

@Deprecated
public class StreamCLI {

    public static void main(String[] args) {
        AccountManager accountManager = AccountManagerFactory.newInstance();
        if ( accountManager.hasValidAccount() ) {
            AccessToken accessToken = accountManager.readPrimaryAccount().orElseThrow(() -> new IllegalStateException("アクセストークンを読み込めませんでした"));
            TwitterStream twitterStream = new TwitterStreamInstanceGeneratorImpl().generate(accessToken);
            twitterStream.addListener(new PrintUserStreamListener());
            twitterStream.user();
            return;
        }
        System.out.println("認証が完了していません。認証した後に起動してください");
    }
}
