package net.nokok.twitduke.model;

import twitter4j.AsyncTwitter;
import twitter4j.AsyncTwitterFactory;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

import java.util.ArrayList;

public class TwitterInstanceManager {

    private static final TwitterInstanceManager instance = new TwitterInstanceManager();

    private final ArrayList<Twitter>      twitterArrayList      = new ArrayList<>();
    private final ArrayList<AsyncTwitter> asyncTwitterArrayList = new ArrayList<>();

    private Twitter      currentTwitter;
    private AsyncTwitter currentAsyncTwitter;

    private TwitterInstanceManager() {

    }

    public void addUser(AccessToken token) {
        Twitter twitter = new TwitterFactory(
            new ConfigurationBuilder()
                .setOAuthConsumerKey(ConsumerKey.TWITTER_CONSUMER_KEY)
                .setOAuthConsumerSecret(ConsumerKey.TWITTER_CONSUMER_SECRET)
                .build())
            .getInstance(token);
        AsyncTwitter asyncTwitter = new AsyncTwitterFactory(
            new ConfigurationBuilder()
                .setOAuthConsumerKey(ConsumerKey.TWITTER_CONSUMER_KEY)
                .setOAuthConsumerSecret(ConsumerKey.TWITTER_CONSUMER_SECRET)
                .build())
            .getInstance(token);
        if (currentTwitter == null) {
            currentTwitter = twitter;
            currentAsyncTwitter = asyncTwitter;
        }
        twitterArrayList.add(twitter);
        asyncTwitterArrayList.add(asyncTwitter);
    }

    public static TwitterInstanceManager getInstance() {
        return instance;
    }

    public Twitter getCurrentTwitterInstance() {
        return currentTwitter;
    }

    public AsyncTwitter getCurrentAsyncTwitter() {
        return currentAsyncTwitter;
    }
}
