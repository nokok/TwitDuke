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
import net.nokok.twitduke.components.basic.TWLabel;
import net.nokok.twitduke.core.type.AsyncTaskOnSuccess;
import net.nokok.twitduke.core.type.ErrorMessageReceivable;

/**
 *
 * @author noko
 */
public class UserIcon extends TWLabel {

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
