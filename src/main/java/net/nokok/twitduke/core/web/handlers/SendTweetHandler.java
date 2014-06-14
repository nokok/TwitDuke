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
package net.nokok.twitduke.core.web.handlers;

import com.google.common.base.Strings;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.nokok.twitduke.core.factory.AsyncTwitterFactory;
import org.mortbay.jetty.HttpConnection;
import org.mortbay.jetty.Request;
import twitter4j.AsyncTwitter;
import twitter4j.auth.AccessToken;

public class SendTweetHandler extends PostHandler {

    private final AsyncTwitter asyncTwitter;

    public static final String CONTEXT_PATH = "/v1/tweet";

    public SendTweetHandler(AccessToken accessToken) {
        this.asyncTwitter = AsyncTwitterFactory.newInstance(accessToken);
        setContextPath("/v1/tweet");
    }

    public SendTweetHandler(AsyncTwitter asyncTwitter) {
        this.asyncTwitter = asyncTwitter;
    }

    @Override
    public void handle(String target, HttpServletRequest req, HttpServletResponse resp, int i) throws IOException, ServletException {
        super.handle(target, req, resp, i);
        if ( !target.equals(CONTEXT_PATH) ) {
            return;
        }
        String parameter = req.getParameter("text");
        if ( Strings.isNullOrEmpty(parameter) ) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        asyncTwitter.updateStatus(parameter);
        Request baseRequest = (req instanceof Request) ? (Request) req : HttpConnection.getCurrentConnection().getRequest();
        baseRequest.setHandled(true);
    }

}
