package net.nokok.twitduke.model.thread;

import java.awt.Point;
import net.nokok.twitduke.util.Threads;
import net.nokok.twitduke.view.MainView;
import net.nokok.twitduke.view.ui.TWLabel;


/**
 * ステータスバーに表示した通知のテキストを下に落とすアニメーション処理を実行します
 */
public class StatusBarAnimationInvoker extends Thread implements Runnable {

    private final TWLabel statusLabel;
    private final String  notificationText;
    private int NUMBER_OF_SECONDS_TO_DISPLAY_NOTIFICATOIN = 5000; //ms

    public StatusBarAnimationInvoker(MainView mainView, String text) {
        this.statusLabel = mainView.getStatusLabel();
        this.notificationText = text;
    }

    @Override
    public void run() {
        this.statusLabel.setText(notificationText);
        Point old = new Point(this.statusLabel.getLocation());
        Point moved = new Point(old);
        Threads.sleep(this, NUMBER_OF_SECONDS_TO_DISPLAY_NOTIFICATOIN);
        for (int i = 0; i < 30; i++) {
            moved.y += i;
            statusLabel.setLocation(moved);
            Threads.sleep(this, 30);
        }
        statusLabel.setText("");
        statusLabel.setLocation(old);
    }
}
