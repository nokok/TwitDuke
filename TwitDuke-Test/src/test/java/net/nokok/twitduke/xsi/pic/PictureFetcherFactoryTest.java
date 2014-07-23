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
package net.nokok.twitduke.xsi.pic;

import java.net.MalformedURLException;
import java.net.URL;
import net.nokok.twitduke.xsi.pic.PictureFetcherFactory.NopPictureFetcher;
import net.nokok.twitduke.xsi.pic.gyazo.GyazoImageImpl;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.matchers.JUnitMatchers;

/**
 *
 * @author noko
 */
public class PictureFetcherFactoryTest extends JUnitMatchers {

    @Test
    public void testGyazoFetcher() throws MalformedURLException {
        URL url = new URL("http://gyazo.com/hogehoge");
        PictureFetcher gyazoFetcher = PictureFetcherFactory.newInstance(url);
        assertTrue(gyazoFetcher instanceof GyazoImageImpl);
    }

    @Test
    public void testNotSupportedServiceFetcher() throws MalformedURLException {
        URL url = new URL("http://google.com");
        PictureFetcher invalidURLFetcher = PictureFetcherFactory.newInstance(url);
        assertFalse(invalidURLFetcher instanceof GyazoImageImpl);
        assertTrue(invalidURLFetcher instanceof NopPictureFetcher);
    }

}
