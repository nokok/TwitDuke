package net.nokok.twitduke.model.thread;

import java.awt.Point;
import net.nokok.twitduke.controller.MainViewController;
import net.nokok.twitduke.util.Threads;
import net.nokok.twitduke.view.ui.TWLabel;


/**
 * ステータスバーに表示した通知のテキストを下に落とすアニメーション処理を実行します
 */
public class NotificationBarAnimationInvoker extends Thread {

    private AnimationThreadSyncronizer animationThreadSyncronizer;

    private static final int WAIT_SHOW_DISPLAY_NOTIFICATION = 3000; //ms
    private static final int ANIMATION_SLEEP_MS             = 10;
    private final TWLabel            statusLabel;
    private final String             notificationText;
    private final MainViewController mainViewController;


    public NotificationBarAnimationInvoker(MainViewController mainViewController, String text) {
        animationThreadSyncronizer = AnimationThreadSyncronizer.getInstance();
        this.mainViewController = mainViewController;
        notificationText = text;
        statusLabel = mainViewController.getNotificationLabel();
    }

    @Override
    public synchronized void run() {
        animationThreadSyncronizer.lock();
        mainViewController.notificationLabelMoveToDefault();
        statusLabel.setText(notificationText);
        if (mainViewController.isLargerThanNotificationLabel()) {
            moveToLeft();
        } else {
            dropDown();
        }
        statusLabel.setText("");
        mainViewController.notificationLabelMoveToDefault();
        animationThreadSyncronizer.unlock();
    }

    private synchronized void moveToLeft() {
        Point moved = statusLabel.getLocation();
        int statusLabelWidth = (int) statusLabel.getPreferredSize().getWidth();
        for (int i = statusLabelWidth; i > 0; i--) {
            moved.x--;
            statusLabel.setLocation(moved);
            Threads.sleep(this, ANIMATION_SLEEP_MS);
        }
    }

    private synchronized void dropDown() {
        Point moved = statusLabel.getLocation();
        Threads.sleep(this, WAIT_SHOW_DISPLAY_NOTIFICATION);
        for (int i = 0; i < 30; i++) {
            moved.y += i;
            statusLabel.setLocation(moved);
            Threads.sleep(this, ANIMATION_SLEEP_MS);
        }
    }
}
