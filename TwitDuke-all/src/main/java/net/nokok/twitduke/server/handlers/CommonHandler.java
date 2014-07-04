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
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.mortbay.jetty.Request;
import org.mortbay.jetty.handler.ContextHandler;

/**
 *
 */
public class CommonHandler extends ContextHandler {

    private final String CONTEXT_PATH;
    private Optional<Request> baseRequest;
    private boolean isTarget;
    private boolean isPostRequest;
    private boolean isGetRequest;
    private HttpServletResponse response;

    public CommonHandler(String contextPath) {
        super(contextPath);
        setAllowNullPathInfo(true);
        this.CONTEXT_PATH = contextPath;
    }

    @Override
    public void handle(String target, HttpServletRequest request, HttpServletResponse response, int dispatch) throws IOException, ServletException {
        baseRequest = Optional.ofNullable((request instanceof Request) ? (Request) request : null);
        this.response = response;
        isTarget = target.equals(CONTEXT_PATH);
        isPostRequest = request.getMethod().equalsIgnoreCase("post");
        isGetRequest = request.getMethod().equalsIgnoreCase("get");
    }

    public boolean isTarget() {
        return isTarget;
    }

    protected Optional<Request> getBaseRequest() {
        return baseRequest;
    }

    protected boolean isPostRequest() {
        return isPostRequest;
    }

    protected boolean isGetRequest() {
        return isGetRequest;
    }

    protected void sendOK() {
        response.setStatus(HttpServletResponse.SC_OK);
    }

    protected void sendBadRequest() {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
}
