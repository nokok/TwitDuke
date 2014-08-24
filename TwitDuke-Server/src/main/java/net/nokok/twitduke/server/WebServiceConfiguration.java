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
package net.nokok.twitduke.server;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.function.Supplier;
import net.nokok.twitduke.base.type.Retrievable;
import net.nokok.twitduke.core.type.Port;
import org.mortbay.jetty.Handler;
import org.mortbay.jetty.Server;
import twitter4j.auth.AccessToken;

public class WebServiceConfiguration implements Callable<Server>, Runnable {

    private Port port;
    private final List<Handler> handlers = new ArrayList<>();
    private AccessToken token;

    private final Supplier<IllegalArgumentException> newInstanceError = () -> new IllegalArgumentException("インスタンスの生成に失敗しました");

    private WebServiceConfiguration() {
        this.port = new Port(8192);
    }

    public WebServiceConfiguration port(int port) {
        this.port = new Port(port);
        return this;
    }

    public static WebServiceConfiguration newService() {
        return new WebServiceConfiguration();
    }

    public WebServiceConfiguration addHandler(Handler handler) {
        handlers.add(handler);
        return this;
    }

    public WebServiceConfiguration addHandler(Class<? extends Handler> clazz) {
        Optional<Handler> mayBeHandler = clazzToObject(clazz);
        handlers.add(mayBeHandler.orElseThrow(newInstanceError));
        return this;
    }

    public WebServiceConfiguration addHandlerWithAccessToken(Class<? extends Handler> clazz, AccessToken token) {
        Optional<? extends Handler> mayBeHandler = clazzToObject(clazz, token);
        handlers.add(mayBeHandler.orElseThrow(newInstanceError));
        return this;
    }

    public WebServiceConfiguration addHandlerRetrievable(Class<? extends Retrievable<Handler>> clazz) {
        Optional<Retrievable<Handler>> oHandler = clazzToRetriveable(clazz);
        Retrievable<Handler> retrievable = oHandler.orElseThrow(newInstanceError);
        return addHandler(retrievable.get());
    }

    public WebServiceConfiguration addHandlerRetrievable(Class<? extends Retrievable<Handler>> clazz, AccessToken token) {
        Optional<Retrievable<Handler>> oHandler = clazzToRetriveable(clazz, token);
        Retrievable<Handler> retrievable = oHandler.orElseThrow(newInstanceError);
        return addHandler(retrievable.get());
    }

    @Override
    public void run() {
        call();
    }

    private Optional<Handler> clazzToObject(Class<? extends Handler> clazz) {
        try {
            return Optional.of(clazz.newInstance());
        } catch (ReflectiveOperationException e) {
            return Optional.empty();
        }
    }

    private Optional<Handler> clazzToObject(Class<? extends Handler> clazz, AccessToken obj) {
        Class<?>[] types = { AccessToken.class };
        try {
            Constructor<? extends Handler> constructor = clazz.getConstructor(types);
            return Optional.of(constructor.newInstance(obj));
        } catch (ReflectiveOperationException e) {
            return Optional.empty();
        }
    }

    private Optional<Retrievable<Handler>> clazzToRetriveable(Class<? extends Retrievable<Handler>> clazz) {
        try {
            return Optional.of(clazz.newInstance());
        } catch (ReflectiveOperationException e) {
            return Optional.empty();
        }
    }

    private Optional<Retrievable<Handler>> clazzToRetriveable(Class<? extends Retrievable<Handler>> clazz, AccessToken token) {
        Class<?>[] types = { AccessToken.class };
        try {
            Constructor<? extends Retrievable<Handler>> constructor = clazz.getConstructor(types);
            return Optional.of(constructor.newInstance(token));
        } catch (ReflectiveOperationException e) {
            return Optional.empty();
        }
    }

    @Override
    public Server call() {
        try {
            Server server = new Server(port.get());
            handlers.forEach(server::addHandler);
            server.start();
            server.join();
            return server;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
