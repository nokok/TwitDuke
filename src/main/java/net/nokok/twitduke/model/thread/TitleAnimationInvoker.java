package net.nokok.twitduke.model.thread;

import net.nokok.twitduke.main.Config;
import net.nokok.twitduke.util.ThreadUtil;
import net.nokok.twitduke.view.MainView;

/**
 * タイトルのアニメーション処理をします
 */
public class TitleAnimationInvoker extends Thread implements Runnable {

    private final MainView mainView;

    public TitleAnimationInvoker(MainView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void run() {
        String oldTitle = mainView.getTitle();
        for (int i = 0; i < ((oldTitle.length() / 2) + 1); i++) {
            mainView.setTitle(oldTitle.substring(i, oldTitle.length() - i));
            ThreadUtil.sleep(this, Config.AnimationWait.TITLE_SPEED);
        }
        String welcome = "Welcome to TwitDuke";
        for (int i = 0; i < welcome.length(); i++) {
            mainView.setTitle(welcome.substring(0, i + 1));
            ThreadUtil.sleep(this, Config.AnimationWait.TITLE_SPEED);
        }
        ThreadUtil.sleep(this, 100);
        for (int i = 0; i < 12; i++) {
            mainView.setTitle(welcome.substring(i, welcome.length()));
            ThreadUtil.sleep(this, Config.AnimationWait.TITLE_SPEED);
        }
    }
}
