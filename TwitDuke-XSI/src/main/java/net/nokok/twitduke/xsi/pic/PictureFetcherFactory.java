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

import java.net.URL;
import javafx.scene.image.Image;
import net.nokok.twitduke.base.async.AsyncTaskOnSuccess;
import net.nokok.twitduke.base.async.ThrowableReceivable;
import net.nokok.twitduke.xsi.pic.gyazo.GyazoImageImpl;

public class PictureFetcherFactory {

    public static PictureFetcher newInstance(URL url) {
        if ( url.getHost().contains("gyazo.com") ) {
            return new GyazoImageImpl(url);
        } else {
            return new NopPictureFetcher();
        }
    }

    static class NopPictureFetcher implements PictureFetcher {

        @Override
        public void asyncFetchImage() {
        }

        @Override
        public void onError(ThrowableReceivable throwableReceiver) {
        }

        @Override
        public void onSuccess(AsyncTaskOnSuccess<Image> imageReceiver) {
        }

    }
}
