package net.nokok.twitduke.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import net.nokok.twitduke.view.MainView;
import net.nokok.twitduke.view.TweetCell;
import net.nokok.twitduke.wrapper.Twitter4jAsyncWrapper;

public class MainViewController {

    private Twitter4jAsyncWrapper wrapper;
    private final MainView mainView = new MainView();

    public void start(Twitter4jAsyncWrapper wrapper) {
        this.wrapper = wrapper;
        mainView.setVisible(true);
        bindActionListener();
        mainView.setTitle("UserStreamに接続中です");
    }

    public void userStreamConnected() {
        launchTitleAnimation();
    }

    public void userStreamDisconnected() {
        mainView.setTitle("UserStreamとの接続が切れています");
    }

    private void launchTitleAnimation() {
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
            mainView.setTitle("TwitDuke");
        }
    }

    public MainView getMainView() {
        return mainView;
    }

    public void insertTweetCell(TweetCell cell) {
        mainView.insertTweetCell(cell);
    }

    private void bindActionListener() {
        mainView.setSendButtonAction(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                sendTweet();
            }
        });

        mainView.setMentionButtonAction(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainView.swapTweetList();
            }
        });

        mainView.setTextFieldAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendTweet();
            }
        });

    }

    private void sendTweet() {
        wrapper.sendTweet(mainView.getTweetText());
        mainView.clearTextField();
    }
}
