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
package net.nokok.twitduke.core.io;

import net.nokok.twitduke.base.io.Writer;
import net.nokok.twitduke.base.io.Reader;
import net.nokok.twitduke.core.type.ScreenName;
import twitter4j.auth.AccessToken;

public class AccessTokenIOSelector {

    private final Reader<AccessToken> reader;
    private final Writer<AccessToken> writer;

    public AccessTokenIOSelector(ScreenName screenName) {
        reader = new AccessTokenPropertyReader(screenName);
        writer = new AccessTokenPropertyWriter(screenName);
    }

    public Reader<AccessToken> getReader() {
        return reader;
    }

    public Writer<AccessToken> getWriter() {
        return writer;
    }
}
