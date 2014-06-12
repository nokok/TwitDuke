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
package net.nokok.twitduke.components.async;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import static java.util.Objects.requireNonNull;
import javax.swing.ImageIcon;
import net.nokok.twitduke.core.type.AsyncTaskOnSuccess;
import net.nokok.twitduke.core.type.ThrowableReceivable;

/**
 * このクラスは画像を非同期で読み込みます。
 * 読み込みの結果はリスナーインターフェースを介して呼び出し元のクラスへ通知されます。
 * リスナーインターフェースがセットされない場合、取得された画像は呼び出し元クラスへの
 * 通知無しで破棄されます。例外はスローされません。
 */
class AsyncImageLoader implements Runnable {

    private final String url;
    private final List<AsyncTaskOnSuccess<ImageIcon>> onSuccessReceiver = new ArrayList<>(2);
    private final List<ThrowableReceivable> onErrorReceiver = new ArrayList<>(2);

    /**
     * 指定されたURLの画像を非同期で読み込むオブジェクトを生成します
     *
     * @param url 画像のURL
     */
    AsyncImageLoader(String url) {
        this.url = requireNonNull(url, "指定されたURLがnullです");
    }

    /**
     * 画像の取得が成功した時に呼ばれます。
     *
     * @param receiver 画像が取得される時に実行するタスク
     */
    void onSuccess(AsyncTaskOnSuccess<ImageIcon> receiver) {
        onSuccessReceiver.add(receiver);
    }

    /**
     * 画像の取得が失敗した時に呼ばれます
     *
     * @param receiver 画像の取得が失敗した時に実行するタスク
     */
    void onError(ThrowableReceivable receiver) {
        onErrorReceiver.add(receiver);
    }

    /**
     * 画像の取得処理を実行します。
     */
    @Override
    public void run() {
        try {
            ImageIcon icon = new ImageIcon(new URL(url));
            onSuccessReceiver.forEach(r -> r.onSuccess(icon));
        } catch (MalformedURLException ex) {
            onErrorReceiver.forEach(r -> r.onError(ex));
        }
    }
}
