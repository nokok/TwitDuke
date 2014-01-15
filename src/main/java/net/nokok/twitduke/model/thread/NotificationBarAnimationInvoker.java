package net.nokok.twitduke.model.thread;

import java.awt.Point;
import net.nokok.twitduke.util.Threads;
import net.nokok.twitduke.view.MainView;
import net.nokok.twitduke.view.ui.TWLabel;


/**
 * ステータスバーに表示した通知のテキストを下に落とすアニメーション処理を実行します
 */
public class NotificationBarAnimationInvoker extends Thread implements Runnable {

    private final TWLabel statusLabel;
    private final String  notificationText;
    private int NUMBER_OF_SECONDS_TO_DISPLAY_NOTIFICATOIN = 5000; //ms
    private int mainViewWidth;

    public NotificationBarAnimationInvoker(MainView mainView, String text) {
        this.statusLabel = mainView.getStatusLabel();
        this.notificationText = text;
        this.mainViewWidth = mainView.getWidth();
    }

    @Override
    public synchronized void run() {
        this.statusLabel.setText(notificationText);
        Point old = new Point(this.statusLabel.getLocation());
        Point moved = new Point(old);
        if (mainViewWidth < statusLabel.getPreferredSize().getWidth()) {
            int statusLabelWidth = statusLabel.getWidth();
            for (int i = statusLabel.getLocation().x + statusLabelWidth; i > -statusLabelWidth; i--) {
                moved.x--;
                statusLabel.setLocation(moved);
                Threads.sleep(this, 15);
            }
        } else {
            Threads.sleep(this, NUMBER_OF_SECONDS_TO_DISPLAY_NOTIFICATOIN);
            for (int i = 0; i < 30; i++) {
                moved.y += i;
                statusLabel.setLocation(moved);
                Threads.sleep(this, 15);
            }
        }
        statusLabel.setText("");
        statusLabel.setLocation(old);
    }
}
