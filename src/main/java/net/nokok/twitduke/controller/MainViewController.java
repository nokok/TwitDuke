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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
        mainView.setSendButtonAction(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                sendTweet();
            }
        });

        mainView.setSettingButtonAction(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                settingViewController.showSettingView();
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
        twitter4jAsyncWrapper.sendTweet(mainView.getTweetText());
        mainView.clearTextField();
    }
}
