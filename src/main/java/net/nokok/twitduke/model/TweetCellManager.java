package net.nokok.twitduke.model;

import java.util.HashMap;
import java.util.Map;
import net.nokok.twitduke.model.type.TweetCellUpdater;
import net.nokok.twitduke.model.type.UpdateCategory;
import net.nokok.twitduke.model.type.CellStatus;
import net.nokok.twitduke.model.listener.TweetCellUpdateListener;
import net.nokok.twitduke.view.tweetcell.TweetCell;
import twitter4j.Status;

/**
 * MIT License. http://opensource.org/licenses/mit-license.php
 * Copyright (c) 2014 noko
 */
public class TweetCellManager implements TweetCellUpdateListener {

    private final HashMap<Long, CellStatus> cellHashMap = new HashMap<>();
    private long selectedUser;

    public TweetCellManager() {
        cellHashMap.put(0L, new CellStatus(new TweetCell(), null)); //デフォルトセル
    }

    @Override
    public void set(TweetCell cell, Status status) {
        cellHashMap.put(status.getId(), new CellStatus(cell, status));
        long userId = status.getUser().getId();
        if (userId == selectedUser) {
            updateTweetCellStatus(new TweetCellUpdater(userId, UpdateCategory.SELECTED));
        }
    }

    @Override
    public void updateTweetCellStatus(TweetCellUpdater update) {
        long id = update.id;
        switch (update.category) {
            case FAVORITED:
                searchTweetCell(id).setFavoriteState(true);
                break;
            case UNFAVORITED:
                searchTweetCell(id).setFavoriteState(false);
                break;
            case RETWEETED:
                searchTweetCell(id).setRetweetState(true);
                break;
            case DELETED:
                searchTweetCell(id).setDeleted();
                break;
            case SELECTED:
                highlightUserCell(id);
                break;
        }
    }

    private TweetCell searchTweetCell(long id) {
        TweetCell cell = cellHashMap.get(id).tweetCell;
        if (cell == null) {
            return cellHashMap.get(0L).tweetCell;
        }
        return cell;
    }

    /**
     * 指定されたユーザーIDのセルをハイライトします
     */
    private void highlightUserCell(long userId) {
        selectedUser = userId;
        for (Map.Entry<Long, CellStatus> cellEntry : cellHashMap.entrySet()) {
            if (cellEntry.getValue().status == null) {
                continue;
            }
            long cellUserId = cellEntry.getValue().status.getUser().getId();
            TweetCell cell = cellEntry.getValue().tweetCell;
            cell.setSelectState(cellUserId == userId);
        }
    }
}
