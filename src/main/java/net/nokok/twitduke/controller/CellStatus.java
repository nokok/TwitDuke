package net.nokok.twitduke.controller;

import net.nokok.twitduke.view.TweetCell;
import twitter4j.Status;

public class CellStatus {
    public final TweetCell tweetCell;
    public final Status    status;

    public CellStatus(TweetCell tweetCell, Status status) {
        this.tweetCell = tweetCell;
        this.status = status;
    }
}
