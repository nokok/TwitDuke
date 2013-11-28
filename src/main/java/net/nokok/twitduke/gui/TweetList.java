package net.nokok.twitduke.gui;

import net.nokok.twitduke.util.config.DefaultConfig;

/**
 * @author noko
 *         ツイートのリストを保持するクラスです
 *         このリストにTweetCellが追加されてタイムラインを表示します
 */
public class TweetList extends ElementList {
    private static final TweetList instance = new TweetList();

    private TweetList() {
        this.setBackground(DefaultConfig.TWEETLIST_COLOR);
    }

    public static TweetList getInstance() {
        return instance;
    }
}
