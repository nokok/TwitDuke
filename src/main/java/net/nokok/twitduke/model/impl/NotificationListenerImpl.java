package net.nokok.twitduke.model.impl;

import net.nokok.twitduke.controller.MainViewController;
import net.nokok.twitduke.model.listener.NotificationListener;
import net.nokok.twitduke.model.thread.NotificationBarAnimationInvoker;

/**
 * MIT License. http://opensource.org/licenses/mit-license.php
 * Copyright (c) 2014 noko
 */
public class NotificationListenerImpl implements NotificationListener {
    private final MainViewController mainViewController;

    public NotificationListenerImpl(MainViewController mainViewController) {
        this.mainViewController = mainViewController;
    }

    /**
     * MainViewのステータスバーに通知を表示します
     * 通知は設定した秒数後(規定値:3秒)に消えるようアニメーション処理が実行されます
     * また、通知が消える前に新たな通知が発生した場合、今表示中の通知の処理が終わり次第、次の通知が表示される。
     *
     * @param notificationText 表示する通知のテキスト
     * @see net.nokok.twitduke.model.thread.NotificationBarAnimationInvoker
     */
    @Override
    public void setNotification(String notificationText) {
        new NotificationBarAnimationInvoker(mainViewController, notificationText).start();
    }
}
