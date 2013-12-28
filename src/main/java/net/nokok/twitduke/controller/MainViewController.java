package net.nokok.twitduke.controller;

import net.nokok.twitduke.model.AccessTokenManager;
import net.nokok.twitduke.model.ConsumerKey;
import net.nokok.twitduke.view.MainView;
import net.nokok.twitduke.wrapper.Twitter4jAsyncWrapper;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.UserStreamAdapter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainViewController {

    private final Twitter4jAsyncWrapper twitter4jAsyncWrapper = new Twitter4jAsyncWrapper();
    private final MainView              mainView              = new MainView();
    private final SettingViewController settingViewController = new SettingViewController();

    public void start() {
        mainView.setVisible(true);
        bindActionListener();
        AccessTokenManager tokenManager = AccessTokenManager.getInstance();

        while (!tokenManager.isAuthenticated()) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        tokenManager.readTokenList();
        TwitterStream twitterStream = TwitterStreamFactory.getSingleton();
        twitter4jAsyncWrapper.setView(mainView);
        UserStreamAdapter userStream = twitter4jAsyncWrapper.getUserStream();
        twitterStream.setOAuthConsumer(ConsumerKey.TWITTER_CONSUMER_KEY, ConsumerKey.TWITTER_CONSUMER_SECRET);
        twitterStream.setOAuthAccessToken(AccessTokenManager.getInstance().readPrimaryAccount());
        twitterStream.addListener(userStream);
        twitterStream.user();
    }

    private void bindActionListener() {
        mainView.getTweetTextField().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                twitter4jAsyncWrapper.sendTweet(mainView.getTweetTextField().getText());
                mainView.clearTextField();
            }
        });

        mainView.getSettingButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                settingViewController.showSettingView();
            }
        });

        mainView.getSendButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                twitter4jAsyncWrapper.sendTweet(mainView.getTweetTextField().getText());
                mainView.clearTextField();
            }
        });
    }
}
