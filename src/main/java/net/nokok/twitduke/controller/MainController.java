package net.nokok.twitduke.controller;

import net.nokok.twitduke.model.Twitter4jWrapper;
import net.nokok.twitduke.view.MainView;
import net.nokok.twitduke.view.TweetCell;
import twitter4j.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class MainController {

    private final AsyncTwitter asyncTwitter = Twitter4jWrapper.getTwitter();
    private final MainView mainView;

    UserStreamAdapter userStreamAdapter = new UserStreamAdapter() {
        @Override
        public void onStatus(Status status) {
            mainView.insertTweetCell(new TweetCell(status));
        }

    };

    public MainController(MainView mainView) {
        this.mainView = mainView;
        ActionListener sendButtonActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendTweet();
            }
        };
        mainView.bindActionListenerSendTweetButton(sendButtonActionListener);
        ActionListener enterKeyPressed = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendTweet();
            }
        };
        mainView.bindActionListenerTextField(enterKeyPressed);
        TwitterStream stream = new TwitterStreamFactory().getInstance();

        stream.addListener(userStreamAdapter);
        stream.user();
    }

    private void sendTweet() {
        asyncTwitter.updateStatus(mainView.getTextFieldText());
        mainView.clearTextField();
    }
}
