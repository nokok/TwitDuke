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

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * POSTリクエストのみを許可するハンドラです。
 * このクラスを継承するハンドラに対してGETでリクエストを送信すると
 * クライアントに対して405 Method Not Allowedが返されます。
 */
public abstract class AbstractPostRequestHandler extends CommonHandler {

    public AbstractPostRequestHandler(String contextPath) {
        super(contextPath);
    }

    @Override
    public void handle(String target, HttpServletRequest request, HttpServletResponse response, int dispatch) throws IOException, ServletException {
        super.handle(target, request, response, dispatch);
        if ( !super.isTarget() ) {
            return;
        }
        if ( isGetRequest() ) {
            response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        }
        super.getBaseRequest().ifPresent(base -> base.setHandled(true));
        doHandle(request, response);
    }

    public abstract void doHandle(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
}
