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

import java.io.IOException;
import java.net.URL;
import javafx.scene.image.Image;
import net.nokok.twitduke.core.async.AsyncTaskOnSuccess;
import net.nokok.twitduke.core.async.ThrowableReceivable;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public abstract class AbstractPictureFetcher implements PictureFetcher {

    protected final URL url;
    private ThrowableReceivable throwableReceiver;
    private AsyncTaskOnSuccess<Image> imageReceiver;

    public AbstractPictureFetcher(URL url) {
        this.url = url;
    }

    @Override
    public void asyncFetchImage() {
        Connection connection = Jsoup.connect(url.toString());
        try {
            imageReceiver.onSuccess(new Image(selectImage(connection.get())));
        } catch (IOException e) {
            throwableReceiver.onError(e);
        }
    }

    @Override
    public void onError(ThrowableReceivable throwableReceiver) {
        this.throwableReceiver = throwableReceiver;
    }

    @Override
    public void onSuccess(AsyncTaskOnSuccess<Image> imageReceiver) {
        this.imageReceiver = imageReceiver;
    }

    public abstract String selectImage(Document document);

}
