/*
 * The MIT License
 *
 * Copyright 2014 noko <nokok.kz at gmail.com>.
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
package net.nokok.twitduke.controller;

import com.sun.net.httpserver.HttpServer;
import java.awt.Desktop;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import net.nokok.twitduke.core.api.twitter.TwitterAuthentication;
import net.nokok.twitduke.core.api.twitter.TwitterAuthenticationListener;
import net.nokok.twitduke.core.impl.twitter.AsyncTwitterInstanceGeneratorImpl;
import twitter4j.AsyncTwitter;
import twitter4j.HttpResponseCode;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

/**
 * HttpServerを使用した自動認証を実行します。
 * <p>
 * @author noko <nokok.kz at gmail.com>
 */
public class AutoAuthentication implements TwitterAuthentication {

    private TwitterAuthenticationListener authenticationListener;

    @Override
    public void setListener(TwitterAuthenticationListener authenticationListener) {
        this.authenticationListener = authenticationListener;
    }

    @Override
    public void start() {
        try {
            AsyncTwitter twitter = new AsyncTwitterInstanceGeneratorImpl().generate();
            RequestToken requestToken = twitter.getOAuthRequestToken("http://localhost:8000");
            openInBrowser(requestToken.getAuthorizationURL());
            HttpServer server = HttpServer.create(new InetSocketAddress(8000), -1);
            server.createContext("/", he -> {
                he.getRequestMethod();
                he.sendResponseHeaders(HttpResponseCode.OK, 0);
                try (OutputStreamWriter responseWriter = new OutputStreamWriter(he.getResponseBody())) {
                    responseWriter.append("認証が完了しました。このタブ(またはウィンドウ)を閉じて下さい");
                }
                server.stop(0);
                String q = he.getRequestURI().getQuery();
                try {
                    String verifier = q.split("=")[2];
                    AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, verifier);
                    authenticationListener.success(accessToken);
                } catch (TwitterException ex) {
                    authenticationListener.error(ex.getErrorMessage());
                }
            });
            server.start();
        } catch (IOException | TwitterException e) {
            authenticationListener.error(e.getMessage());
        }
    }

    private void openInBrowser(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (IOException | URISyntaxException ex) {
            authenticationListener.error(ex.getMessage());
        }
    }
}
