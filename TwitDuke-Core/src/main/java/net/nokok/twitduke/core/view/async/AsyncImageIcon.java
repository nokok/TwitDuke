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
package net.nokok.twitduke.core.view.async;

import java.awt.Dimension;
import net.nokok.twitduke.core.view.basics.TWLabel;

/**
 * 非同期でユーザーアイコンを取得できるラベルコンポーネントです
 */
public class AsyncImageIcon extends TWLabel {

    private static final long serialVersionUID = -350591259780147393L;

    /**
     * 指定されたURLから取得できる画像でラベルを生成します。
     *
     * @param url 画像のURL
     */
    public AsyncImageIcon(String url) {
        setPreferredSize(new Dimension(50, 50));
        AsyncImageLoader task = new AsyncImageLoader(url);
        task.onSuccess(this::setIcon);
        task.onError(System.out::println);
        UIAsyncTaskPool.INSTANCE.runTask(task);
    }

    public AsyncImageIcon(String url, Dimension size) {
        this(url);
        setPreferredSize(size);
    }

}
