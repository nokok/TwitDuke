/*
 * The MIT License
 *
 * Copyright 2014 noko <nokok.kz at gmail.com>.
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
package net.nokok.twitduke.components.async;

import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import net.nokok.twitduke.components.basic.TWLabel;
import net.nokok.twitduke.core.type.AsyncTaskOnSuccess;
import net.nokok.twitduke.core.type.ErrorMessageReceivable;

/**
 * 非同期でユーザーアイコンを取得できるラベルコンポーネントです
 *
 * @author noko
 */
public class UserIcon extends TWLabel {

    private static final long serialVersionUID = -350591259780147393L;

    /**
     * 指定されたURLの画像でラベルを生成します。
     *
     * @param url 画像のURL
     */
    public UserIcon(String url) {
        AsyncImageLoader task = new AsyncImageLoader(url);
        task.onSuccess(icon -> setIcon(icon));
        task.onError(System.out::println);
        UIAsyncTaskPool.INSTANCE.runTask(task);
    }

    private static class AsyncImageLoader implements Runnable {

        private final String url;
        private AsyncTaskOnSuccess<Icon> onSuccess;
        private ErrorMessageReceivable receivable;

        public AsyncImageLoader(String url) {
            this.url = url;
        }

        void onSuccess(AsyncTaskOnSuccess<Icon> onSuccess) {
            this.onSuccess = onSuccess;
        }

        void onError(ErrorMessageReceivable receivable) {
            this.receivable = receivable;
        }

        @Override
        public void run() {
            try {
                Icon icon = new ImageIcon(new URL(url));
                onSuccess.onSuccess(icon);
            } catch (MalformedURLException ex) {
                receivable.onError(ex.getMessage());
            }
        }
    }
}
