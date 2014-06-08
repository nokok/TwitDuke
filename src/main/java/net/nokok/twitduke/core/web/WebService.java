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

import java.util.Properties;
import net.nokok.twitduke.core.io.Paths;
import net.nokok.twitduke.core.io.PropertyReader;
import net.nokok.twitduke.core.web.handlers.SendTweetHandler;
import org.mortbay.jetty.Server;
import twitter4j.auth.AccessToken;

public class WebService implements Runnable {

    private Server server;
    private final Properties DEFAULT_PROPERTIES = new Properties();

    public WebService() {
        DEFAULT_PROPERTIES.put(WebServicePropertyKey.PORT_KEY, "8192");
    }

    public WebService(AccessToken accessToken) {
        PropertyReader reader = new PropertyReader(Paths.WEB_CONFIG_PATH_STR);
        Properties prop = reader.read().orElse(DEFAULT_PROPERTIES);
        int port = Integer.parseInt(prop.getProperty(WebServicePropertyKey.PORT_KEY));
        server = new Server(port);
        server.addHandler(new SendTweetHandler(accessToken));
    }

    @Override
    public void run() {
        try {
            server.start();
            server.join();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
