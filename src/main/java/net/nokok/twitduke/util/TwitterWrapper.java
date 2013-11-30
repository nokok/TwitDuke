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

    private Logger  logger;
    private Twitter twitter;
    private static final TwitterWrapper instance = new TwitterWrapper();

    private TwitterWrapper() {
        twitter = new TwitterFactory().getInstance();
        logger = Logger.getLogger("TwitterWrapper Logger");
        Properties properties = new Properties();
        try {
            properties.load(this.getClass().getResourceAsStream(DefaultConfig.APIKEY_FILEPATH));
        } catch (IOException e) {
            logger.severe("APIキーを格納したプロパティファイルが見つかりませんでした");
        }
    }

    public static TwitterWrapper getInstance() {
        return instance;
    }

    public void sendTweet(String tweet) {
        try {
            twitter.updateStatus(tweet);
        } catch (TwitterException e) {
            logger.warning("Twitterへのポストに失敗しました");
        }
    }

}
