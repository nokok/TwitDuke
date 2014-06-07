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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.mortbay.jetty.Handler;
import org.mortbay.jetty.Server;

public class WebServiceConfiguration implements Runnable {

    private int port = 8192;
    private final List<Handler> handlers = new ArrayList<>();

    private WebServiceConfiguration() {

    }

    public static WebServiceConfiguration newService() {
        return new WebServiceConfiguration();
    }

    public WebServiceConfiguration port(int port) {
        this.port = port;
        return this;
    }

    public WebServiceConfiguration addHandler(Handler handler) {
        handlers.add(handler);
        return this;
    }

    public WebServiceConfiguration addHandler(Class<? extends Handler> clazz) {
        Optional<Handler> mayBeHandler = clazzToObject(clazz);
        if ( mayBeHandler.isPresent() ) {
            handlers.add(mayBeHandler.get());
        } else {
            throw new IllegalArgumentException("インスタンスの生成に失敗しました" + clazz.getName());
        }
        return this;
    }

    private Optional<Handler> clazzToObject(Class<? extends Handler> clazz) {
        try {
            return Optional.of(clazz.newInstance());
        } catch (ReflectiveOperationException e) {
            return Optional.empty();
        }
    }

    @Override
    public void run() {
        try {
            Server server = new Server(port);
            handlers.forEach(server::addHandler);
            server.start();
            server.join();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
