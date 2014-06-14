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
package net.nokok.twitduke.core.type;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class FooterTest {

    @Test(expected = NullPointerException.class)
    public void testNullConstructorParameter() {
        new Footer(null);
    }

    @Test
    public void testGet() {
        String str = "hoge";
        Footer footer = new Footer(str);
        assertEquals(footer.get(), "#" + str);
        String str1 = "#fuga";
        Footer f1 = new Footer(str1);
        assertEquals(f1.get(), str1);
    }

    @Test
    public void testEquals() {
        String str = "hoge";
        Footer f1 = new Footer(str);
        Footer f2 = new Footer(str);
        assertTrue(f1.equals(f2));
        assertTrue(f2.equals(f1));
        assertFalse(f1.equals(null));
        assertFalse(f1.equals(str));
    }

    @Test
    public void testHashCode() {
        String str = "hoge";
        Footer f1 = new Footer(str);
        Footer f2 = new Footer(str);
        assertTrue(f1.equals(f2));
        assertEquals(f1.hashCode(), f2.hashCode());
    }

    @Test
    public void testToString() {
        String s1 = "hoge";
        Footer f1 = new Footer(s1);
        assertEquals(f1.toString(), "#hoge");
        String s2 = "#hoge";
        Footer f2 = new Footer(s2);
        assertEquals(f2.toString(), "#hoge");
    }
}
