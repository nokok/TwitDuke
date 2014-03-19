package net.nokok.twitduke.model.listener;

import net.nokok.twitduke.controller.tweetcellstatus.TweetCellUpdater;

/**
 * MIT License. http://opensource.org/licenses/mit-license.php
 * Copyright (c) 2014 noko
 */
public interface TweetCellUpdateListener {
    void updateTweetCellStatus(TweetCellUpdater updater);
}
