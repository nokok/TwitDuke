package net.nokok.twitduke.util;

import net.nokok.twitduke.util.config.DefaultConfig;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * @author noko
 *         Twitter4jのラッパクラスです
 */
public class TwitterWrapper {

    private static Logger  logger;
    private        Twitter twitter;
    private static final TwitterWrapper instance = new TwitterWrapper();

    private TwitterWrapper() {
        twitter = new TwitterFactory().getInstance();
        logger = Logger.getLogger("TwitterWrapper Logger");
    }

    public static TwitterWrapper getInstance() {
        return instance;
    }

    public void sendTweet(String tweet) {
        try {
            twitter.updateStatus(tweet);
        } catch (TwitterException e) {
            Log.write("Twitterへのポストに失敗しました");
        }
    }

}
