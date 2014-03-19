package net.nokok.twitduke.model.factory;

import com.google.common.base.Strings;
import net.nokok.twitduke.controller.tweetcellstatus.TweetCellUpdater;
import net.nokok.twitduke.controller.tweetcellstatus.UpdateCategory;
import net.nokok.twitduke.model.listener.TweetCellUpdateListener;
import net.nokok.twitduke.util.URLUtil;
import net.nokok.twitduke.view.TweetPopupMenu;
import net.nokok.twitduke.view.tweetcell.TweetCell;
import net.nokok.twitduke.view.ui.TWMenuItem;
import net.nokok.twitduke.wrapper.Twitter4jAsyncWrapper;
import twitter4j.MediaEntity;
import twitter4j.Status;
import twitter4j.URLEntity;
import twitter4j.UserMentionEntity;

class PopupMenuFactory {

    private final Twitter4jAsyncWrapper   wrapper;
    private       TweetCellUpdateListener tweetCellUpdateListener;

    /**
     * PopupMenuFactoryに必要なラッパーをセットします
     *
     * @param wrapper セットするラッパ
     */
    public PopupMenuFactory(Twitter4jAsyncWrapper wrapper) {
        this.wrapper = wrapper;
    }

    public void setTweetCellUpdateListener(TweetCellUpdateListener tweetCellUpdateListener) {
        this.tweetCellUpdateListener = tweetCellUpdateListener;
    }

    /**
     * 渡されたステータスを用いて渡されたセルにPopupMenuをセットします。
     *
     * @param cell   PopupMenuをセットするセル
     * @param status PopupMenuを生成する元となるステータス
     * @return 生成されたPopupMenu
     */
    public TweetPopupMenu createPopupMenu(TweetCell cell, Status status) {
        TweetPopupMenu popupMenu = new TweetPopupMenu();

        cell.setMouseListener(new TweetCellMouseAdapter(popupMenu, status, cell));

        popupMenu.setReplyAction(e -> {
            if (status.isRetweet()) {
                wrapper.replyPreprocess(status.getRetweetedStatus());
                return;
            }
            wrapper.replyPreprocess(status);
        });

        popupMenu.setFavoriteAction(e -> favorite(cell, status.getId()));

        popupMenu.setRetweetAction(e -> retweet(cell, status.getId()));

        popupMenu.setAllURLOpenAction(e -> {
            URLUtil.allURLEntitiesOpen(status.getURLEntities());
            URLUtil.allMediaEntitiesOpen(status.getMediaEntities());
        });

        popupMenu.setHighlightAction(e -> {
            if (cell.isSelected()) {
                tweetCellUpdateListener.updateTweetCellStatus(new TweetCellUpdater(0, UpdateCategory.SELECTED));
            } else {
                tweetCellUpdateListener.updateTweetCellStatus(new TweetCellUpdater(status.getUser().getId(), UpdateCategory.SELECTED));
            }
        });


        popupMenu.setSearchAction(e -> {
            String searchString = cell.getSelectedText();
            if (Strings.isNullOrEmpty(searchString)) {
                return;
            }
            URLUtil.openInBrowser("http://www.google.co.jp/search?q=" + URLUtil.encodeString(cell.getSelectedText()));
        });

        popupMenu.setDeleteAction(e -> wrapper.deleteTweet(status.getId()));

        for (UserMentionEntity entity : status.getUserMentionEntities()) {
            TWMenuItem menuItem = new TWMenuItem(entity.getScreenName() + " の詳細を開く");
            menuItem.addActionListener(e -> wrapper.getUserInfomation(entity.getId()));
            popupMenu.addMenuItem(menuItem);
        }

        for (URLEntity entity : status.getURLEntities()) {
            TWMenuItem menuItem = new TWMenuItem(entity.getDisplayURL() + "を開く");
            menuItem.addActionListener(e -> URLUtil.openInBrowser(entity.getExpandedURL()));
            popupMenu.addMenuItem(menuItem);
        }

        for (MediaEntity entity : status.getMediaEntities()) {
            TWMenuItem menuItem = new TWMenuItem(entity.getDisplayURL() + "を開く");
            menuItem.addActionListener(e -> URLUtil.openInBrowser(entity.getExpandedURL()));
            popupMenu.addMenuItem(menuItem);
        }
        return popupMenu;
    }

    /**
     * お気に入りのセル側の処理です。
     *
     * @param cell     お気に入りのアクションが発生したセル
     * @param statusId お気に入り登録/登録解除するステータスのID
     */
    private void favorite(TweetCell cell, long statusId) {
        //TODO:TweetCellFactoryと重複
        if (cell.isFavorited()) {
            cell.setFavoriteState(false);
            wrapper.removeFavoriteTweet(statusId);
        } else {
            cell.setFavoriteState(true);
            wrapper.favoriteTweet(statusId);
        }
    }

    /**
     * リツイートのセル側の処理です
     *
     * @param cell     リツイートのアクションが発生したセル
     * @param statusId リツイートするステータスのID
     */
    private void retweet(TweetCell cell, long statusId) {
        //TODO:TweetCellFactoryと重複
        if (cell.isRetweeted()) {
            cell.setRetweetState(false);
            wrapper.deleteTweet(statusId);
        } else {
            cell.setRetweetState(true);
            wrapper.retweetTweet(statusId);
        }
    }

}
