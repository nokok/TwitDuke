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
package net.nokok.twitduke.server.handlers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.nokok.twitduke.base.type.Retrievable;
import net.nokok.twitduke.core.factory.TAsyncTwitterFactory;
import org.mortbay.jetty.Handler;
import twitter4j.AsyncTwitter;
import twitter4j.auth.AccessToken;

public class JavaJavaHandler implements Retrievable<Handler> {

    private final AsyncTwitter asyncTwitter;
    private final AbstractGetRequestHandler handler = new AbstractGetRequestHandler("/v1/javajava") {

        @Override
        public void doHandle(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
            asyncTwitter.updateStatus(StringUtil.appendRandomString("JavaJavaJava〜〜〜ｗ", "ｗ"));
            sendOK();
        }
    };

    public JavaJavaHandler(AccessToken accessToken) {
        this.asyncTwitter = TAsyncTwitterFactory.newInstance(accessToken);
    }

    public JavaJavaHandler(AsyncTwitter asyncTwitter) {
        this.asyncTwitter = asyncTwitter;
    }

    @Override
    public Handler get() {
        return handler;
    }
}
