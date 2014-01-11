package net.nokok.twitduke.model;

import net.nokok.twitduke.view.MainView;

public class TitleAnimationInvoker extends Thread implements Runnable {

    private MainView mainView;

    public TitleAnimationInvoker(MainView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void run() {
        try {
            String oldTitle = mainView.getTitle();
            for (int i = 0; i < (oldTitle.length() / 2) + 1; i++) {
                mainView.setTitle(oldTitle.substring(i, oldTitle.length() - i));
                Thread.sleep(30);
            }
            String welcome = "Welcome to TwitDuke";
            for (int i = 0; i < welcome.length(); i++) {
                mainView.setTitle(welcome.substring(0, i + 1));
                Thread.sleep(30);
            }
            Thread.sleep(100);
            for (int i = 0; i < 12; i++) {
                mainView.setTitle(welcome.substring(i, welcome.length()));
                Thread.sleep(30);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            mainView.setTitle("TwitDuke");
        }
    }
}
