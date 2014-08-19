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
package net.nokok.twitduke.components.keyevent;

import net.nokok.twitduke.base.type.TweetLength;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public class TweetLengthTest {

    @Test(expected = IllegalArgumentException.class)
    public void whenNegativeValue() {
        new TweetLength(-1);
    }

    @Test
    public void whenZero() {
        new TweetLength(0);
    }

    @Test
    public void whenEmptyString() {
        new TweetLength("");
    }

    @Test(expected = NullPointerException.class)
    public void whenNull() {
        new TweetLength(null);
    }

    @Test
    public void testIsSendable() {
        TweetLength tl = new TweetLength(140);
        assertThat(tl.isSendable(), is(true));
        TweetLength tooLong = new TweetLength(141);
        assertThat(tooLong.isSendable(), is(false));
    }

    @Test
    public void testLengthWithInt() {
        TweetLength tl1 = new TweetLength(1);
        assertThat(tl1.length(), is(1));

        TweetLength tl0 = new TweetLength(0);
        assertThat(tl0.length(), is(0));

        TweetLength tl140 = new TweetLength(140);
        assertThat(tl140.length(), is(140));

        TweetLength tl141 = new TweetLength(141);
        assertThat(tl141.length(), is(141));
    }

    @Test
    public void testLengthWithString() {
        String longString_140 = "140文字の文字列140文字の文字列140文字の文字列140文字の文字列140文字の文字列140文字の文字列140文字の文字列140文字の文字列140文字の文字列140文字の文字列140文字の文字列140文字の文字列140文字の文字列140文字の文字列140文字の文字列140文字";

        TweetLength tl140 = new TweetLength(longString_140);
        assertThat(tl140.length(), is(140));
        TweetLength tl141 = new TweetLength("a" + longString_140); //141
        assertThat(tl141.length(), is(141)); //should NOT throw exception
        assertThat(tl141.isSendable(), is(false));
    }
}
