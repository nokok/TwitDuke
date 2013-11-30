package net.nokok.twitduke.util;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

/**
 * @author noko
 *         Twitter4jのラッパクラスです
 */
public class TwitterWrapper {

    private Twitter twitter;
    private static final TwitterWrapper instance = new TwitterWrapper();

    private TwitterWrapper() {
        twitter = new TwitterFactory().getInstance();
    }

    public static TwitterWrapper getInstance() {
        return instance;
    }

    public void sendTweet(String tweet) {
        try {
            twitter.updateStatus(tweet);
        } catch (TwitterException e) {
            Log.log("Twitterへのポストに失敗しました");
        }
    }

}
