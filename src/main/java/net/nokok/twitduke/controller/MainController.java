package net.nokok.twitduke.controller;

import net.nokok.twitduke.model.Twitter4jWrapper;
import net.nokok.twitduke.view.MainView;
import net.nokok.twitduke.view.TweetCell;
import twitter4j.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainController {

    private final AsyncTwitter asyncTwitter = Twitter4jWrapper.getTwitter();
    private MainView mainView;

    private final ActionListener sendButtonActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            sendTweet();
        }
    };
    private final ActionListener enterKeyPressed = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            sendTweet();
        }
    };

    private final UserStreamAdapter userStreamAdapter = new UserStreamAdapter() {
        @Override
        public void onStatus(Status status) {
            mainView.insertTweetCell(new TweetCell(status));
        }

    };

    public MainController(MainView mainView) {
        this.mainView = mainView;
        mainView.bindActionListenerSendTweetButton(sendButtonActionListener);
        mainView.bindActionListenerTextField(enterKeyPressed);
        TwitterStream stream;

        stream = new TwitterStreamFactory().getInstance();
        stream.addListener(userStreamAdapter);
        stream.user();
    }

    private void sendTweet() {
        asyncTwitter.updateStatus(mainView.getTextFieldText());
        mainView.clearTextField();
    }
}
