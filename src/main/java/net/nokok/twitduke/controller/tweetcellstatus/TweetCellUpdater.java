package net.nokok.twitduke.controller.tweetcellstatus;

public class TweetCellUpdater {
    public final long           id;
    public final UpdateCategory category;

    public TweetCellUpdater(long id, UpdateCategory category) {
        this.id = id;
        this.category = category;
    }
}
