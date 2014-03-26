package net.nokok.twitduke.model.listener;

import net.nokok.twitduke.model.type.TweetCellUpdater;
import net.nokok.twitduke.view.tweetcell.TweetCell;
import twitter4j.Status;

/**
 * MIT License. http://opensource.org/licenses/mit-license.php
 * Copyright (c) 2014 noko
 */
public interface TweetCellUpdateListener {
    void updateTweetCellStatus(TweetCellUpdater updater);

    void set(TweetCell cell, Status status);
}
