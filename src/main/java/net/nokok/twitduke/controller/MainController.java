package net.nokok.twitduke.controller;

import net.nokok.twitduke.model.Twitter4jWrapper;
import net.nokok.twitduke.view.MainView;
import net.nokok.twitduke.view.TweetCell;
import org.jetbrains.annotations.NotNull;
import twitter4j.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class MainController {

    private final AsyncTwitter asyncTwitter = Twitter4jWrapper.getTwitter();
    @NotNull
    private final MainView mainView;

    @NotNull
    UserStreamAdapter userStreamAdapter = new UserStreamAdapter() {
        @Override
        public void onStatus(@NotNull Status status) {
            mainView.insertTweetCell(new TweetCell(status));
        }

    };

    public MainController(@NotNull MainView mainView) {
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
