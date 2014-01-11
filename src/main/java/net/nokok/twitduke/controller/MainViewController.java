package net.nokok.twitduke.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import net.nokok.twitduke.model.StatusBarAnimationInvoker;
import net.nokok.twitduke.model.TitleAnimationInvoker;
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
        this.setStatus("UserStreamに接続中です");
    }

    public void userStreamConnected() {
        this.setStatus("UserStreamに接続しました");
        launchTitleAnimation();
    }

    public void userStreamDisconnected() {
        this.setStatus("UserStreamとの接続が切れています");
    }

    private void launchTitleAnimation() {
        new TitleAnimationInvoker(mainView).start();
    }

    public void setStatus(String text) {
        mainView.setStatus(text);
        new StatusBarAnimationInvoker(mainView).start();
    }

    public void setReply(String screenName) {
        mainView.setTweetTextField("@" + screenName + " ");
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
