package net.nokok.twitduke.util;

/**
 * @author noko
 *         Twitter4jのラッパクラスです
 */
public class TwitterWrapper {

    private static final TwitterWrapper instance = new TwitterWrapper();

    private TwitterWrapper() {

    }

    public static TwitterWrapper getInstance() {
        return instance;
    }

}
