package net.nokok.twitduke.model.type;

import net.nokok.twitduke.view.tweetcell.TweetCell;
import twitter4j.Status;

public class CellStatus {
    public final TweetCell tweetCell;
    public final Status    status;

    public CellStatus(TweetCell tweetCell, Status status) {
        this.tweetCell = tweetCell;
        this.status = status;
    }
}
