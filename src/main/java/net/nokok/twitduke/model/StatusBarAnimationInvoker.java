package net.nokok.twitduke.model;

import java.awt.Point;
import net.nokok.twitduke.view.MainView;
import net.nokok.twitduke.view.ui.TWLabel;

public class StatusBarAnimationInvoker extends Thread implements Runnable {

    private final MainView mainView;
    private final TWLabel  statusLabel;

    public StatusBarAnimationInvoker(MainView mainView) {
        this.mainView = mainView;
        this.statusLabel = mainView.getStatusLabel();
    }

    @Override
    public void run() {
        Point old = new Point(this.statusLabel.getLocation());
        Point moved = new Point(old);
        try {
            Thread.sleep(5000);
            for (int i = 0; i < 30; i++) {
                moved.y += i;
                statusLabel.setLocation(moved);
                Thread.sleep(30);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        statusLabel.setText("");
        statusLabel.setLocation(old);
    }
}
