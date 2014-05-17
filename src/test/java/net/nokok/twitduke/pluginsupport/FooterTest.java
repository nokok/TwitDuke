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
package net.nokok.twitduke.pluginsupport;

import net.nokok.twitduke.core.type.Tweet;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author noko
 */
public class FooterTest {

    @Test
    public void testApplyFooter_Tweet() {
        Footer footer2 = new Footer("fuga");
        footer2.applyFooter("hoge");
        assertEquals(footer2.applyFooter("hoge").get(), new Tweet("hogefuga").get());
        assertEquals(footer2.applyFooter("").get(), new Tweet("fuga").get());
    }

    @Test
    public void testApplyFooter_String() {
        Footer footer2 = new Footer("hoge");
        assertEquals(footer2.applyFooter(new Tweet("fuga")).get(), "fugahoge");
    }

    public void testGet() {
        Footer footer = new Footer("hoge");
        assertEquals(footer.get(), "hoge");
    }
}
