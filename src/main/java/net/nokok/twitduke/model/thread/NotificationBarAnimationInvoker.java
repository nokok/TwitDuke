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

    private static final int WAIT_SHOW_DISPLAY_NOTIFICATION = 5000; //ms
    private static final int ANIMATION_SLEEP_MS             = 15;
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
        mainViewController.notificationLabelMoveToDefault();
        Point moved = statusLabel.getLocation();
        if (mainViewController.isLargerThanNotificationLabel()) {
            int statusLabelWidth = statusLabel.getWidth();
            for (int i = statusLabel.getLocation().x + statusLabelWidth; i > -statusLabelWidth; i--) {
                moved.x--;
                statusLabel.setLocation(moved);
                Threads.sleep(this, ANIMATION_SLEEP_MS);
            }
        } else {
            Threads.sleep(this, WAIT_SHOW_DISPLAY_NOTIFICATION);
            for (int i = 0; i < 30; i++) {
                moved.y += i;
                statusLabel.setLocation(moved);
                Threads.sleep(this, ANIMATION_SLEEP_MS);
            }
        }
        statusLabel.setText("");
        mainViewController.notificationLabelMoveToDefault();
        animationThreadSyncronizer.unlock();
    }
}
