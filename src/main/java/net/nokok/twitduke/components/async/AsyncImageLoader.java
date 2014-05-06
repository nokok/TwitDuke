/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.nokok.twitduke.components.async;

import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import net.nokok.twitduke.core.type.AsyncTaskOnSuccess;
import net.nokok.twitduke.core.type.ErrorMessageReceivable;

/**
 * このクラスは画像を非同期で読み込みます。
 * 読み込みの結果はリスナーインターフェースを介して呼び出し元のクラスへ通知されます。
 * リスナーインターフェースがセットされない場合、取得された画像は呼び出し元クラスへの
 * 通知無しで破棄されます。例外はスローされません。
 */
class AsyncImageLoader implements Runnable {

    private final String url;
    private AsyncTaskOnSuccess<Icon> onSuccess;
    private ErrorMessageReceivable receivable;

    /**
     * 指定されたURLの画像を非同期で読み込むオブジェクトを生成します
     *
     * @param url 画像のURL
     */
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
            if ( onSuccess == null ) {
                return;
            }
            onSuccess.onSuccess(icon);
        } catch (MalformedURLException ex) {
            if ( receivable == null ) {
                return;
            }
            receivable.onError(ex.getMessage());
        }
    }
}
