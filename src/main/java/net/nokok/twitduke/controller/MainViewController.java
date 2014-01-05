package net.nokok.twitduke.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;
import net.nokok.twitduke.model.AccessTokenManager;
import net.nokok.twitduke.model.ConsumerKey;
import net.nokok.twitduke.model.factory.TweetCellFactory;
import net.nokok.twitduke.view.MainView;
import net.nokok.twitduke.wrapper.Twitter4jAsyncWrapper;
import twitter4j.ConnectionLifeCycleListener;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.UserStreamAdapter;

public class MainViewController {

    private final Twitter4jAsyncWrapper wrapper  = Twitter4jAsyncWrapper.getInstance();
    private final MainView              mainView = new MainView();

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
        twitterStream.addConnectionLifeCycleListener(new ConnectionLifeCycleListener() {
            @Override
            public void onConnect() {
                try {
                    String oldTitle = mainView.getTitle();
                    for (int i = 0; i < (oldTitle.length() / 2) + 1; i++) {
                        mainView.setTitle(oldTitle.substring(i, oldTitle.length() - i));
                        Thread.sleep(50);
                    }
                    String welcome = "Welcome to TwitDuke";
                    for (int i = 0; i < welcome.length(); i++) {
                        mainView.setTitle(welcome.substring(0, i + 1));
                        Thread.sleep(50);
                    }
                    Thread.sleep(100);
                    for (int i = 0; i < 12; i++) {
                        mainView.setTitle(welcome.substring(i, welcome.length()));
                        Thread.sleep(50);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onDisconnect() {
                mainView.setTitle("接続が切れました");
            }

            @Override
            public void onCleanUp() {
            }
        });

        mainView.setTitle("UserStreamに接続中です");
        wrapper.setView(mainView);
        UserStreamAdapter userStream = wrapper.getUserStream();
        twitterStream.setOAuthConsumer(ConsumerKey.TWITTER_CONSUMER_KEY, ConsumerKey.TWITTER_CONSUMER_SECRET);
        twitterStream.setOAuthAccessToken(AccessTokenManager.getInstance().readPrimaryAccount());
        twitterStream.addListener(userStream);
        twitterStream.user();

        fetchTimeLines();
    }

    private void fetchTimeLines() {
        TweetCellFactory cellFactory = new TweetCellFactory(wrapper);
        ResponseList<Status> mentions = wrapper.fetchMentionsTimeLine();
        Collections.reverse(mentions);
        ResponseList<Status> timeline = wrapper.fetchHomeTimeLine();
        Collections.reverse(timeline);
        for (Status status : mentions) {
            mainView.insertTweetCell(cellFactory.createTweetCell(status));
        }
        for (Status status : timeline) {
            mainView.insertTweetCell(cellFactory.createTweetCell(status));
        }
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
