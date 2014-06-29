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
package net.nokok.twitduke.core.web;

import net.nokok.twitduke.core.web.handlers.AddAccountHandler;
import net.nokok.twitduke.core.web.handlers.BurnOwenHandler;
import net.nokok.twitduke.core.web.handlers.JavaJavaHandler;
import net.nokok.twitduke.core.web.handlers.SendTweetHandler;
import net.nokok.twitduke.core.web.handlers.ShindanmakerHandler;
import net.nokok.twitduke.core.web.handlers.TweetWithFooterHandler;
import twitter4j.auth.AccessToken;

public class WebServerStarter implements Runnable {

    private final AccessToken accessToken;

    public WebServerStarter(AccessToken accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public void run() {
        WebServiceConfiguration
                .newService()
                .addHandler(new SendTweetHandler(accessToken).getHandler())
                .addHandler(new ShindanmakerHandler(accessToken).getHandler())
                .addHandler(new TweetWithFooterHandler(accessToken).getHandler())
                .addHandler(new BurnOwenHandler(accessToken).get())
                .addHandler(new JavaJavaHandler(accessToken).get())
                .addHandlerRetrievable(AddAccountHandler.class)
                .call();
    }

}
