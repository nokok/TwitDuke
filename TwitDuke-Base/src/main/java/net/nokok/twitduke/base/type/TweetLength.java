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
package net.nokok.twitduke.base.type;

import java.util.Objects;

public class TweetLength {

    private final int length;

    public TweetLength(int length) {
        if ( length < 0 ) {
            throw new IllegalArgumentException("ツイートの長さは正の値でなければなりません:" + length);
        }
        this.length = length;
    }

    public TweetLength(String str) {
        this(Objects.requireNonNull(str).length());
    }

    public boolean isSendable() {
        return length <= 140;
    }

    public int length() {
        return length;
    }

    @Override
    public boolean equals(Object obj) {
        return this.hashCode() == Objects.requireNonNull(obj).hashCode();
    }

    @Override
    public int hashCode() {
        return length;
    }

    @Override
    public String toString() {
        return String.valueOf(length);
    }
}
