package net.nokok.twitduke.main;

import net.nokok.twitduke.controller.MainViewController;
import net.nokok.twitduke.model.AccessTokenManager;
import net.nokok.twitduke.model.ConsumerKey;
import net.nokok.twitduke.model.TWUserStream;
import net.nokok.twitduke.wrapper.Twitter4jAsyncWrapper;
import twitter4j.ConnectionLifeCycleListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

public class Main {

    Twitter4jAsyncWrapper wrapper;
    MainViewController    mainViewController;
    TwitterStream         twitterStream;

    public static void main(String[] args) {
        new Main().boot();
    }

    private void boot() {
        bootInitialize();
        readConfigFiles();
        twitterAPIWrapperInitialize();
        mainViewInitialize();
        startUserStream();
        fetchTimelines();
    }

    private void bootInitialize() {
        mainViewController = new MainViewController();
        wrapper = Twitter4jAsyncWrapper.getInstance();
    }

    private void readConfigFiles() {/*TODO:設定画面の実装時に追加する*/}

    private void twitterAPIWrapperInitialize() {
        twitterStream = TwitterStreamFactory.getSingleton();
        twitterStream.addConnectionLifeCycleListener(new ConnectionLifeCycleListener() {
            @Override
            public void onConnect() {
                mainViewController.userStreamConnected();
            }

            @Override
            public void onDisconnect() {
                mainViewController.userStreamDisconnected();
            }

            @Override
            public void onCleanUp() {
            }
        });
    }

    private void mainViewInitialize() {
        mainViewController.start(wrapper);
    }

    private void startUserStream() {
        wrapper.setView(mainViewController);
        wrapper.enableTwitterListener();
        twitterStream.setOAuthConsumer(ConsumerKey.TWITTER_CONSUMER_KEY, ConsumerKey.TWITTER_CONSUMER_SECRET);
        twitterStream.setOAuthAccessToken(AccessTokenManager.getInstance().readPrimaryAccount());
        twitterStream.addListener(new TWUserStream(mainViewController));
        twitterStream.user();
    }

    private void fetchTimelines() {
        wrapper.getMentions();
        wrapper.getHomeTimeLine();
    }
}
