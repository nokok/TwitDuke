package net.nokok.twitduke.model.factory;

import com.google.common.base.Strings;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import net.nokok.twitduke.controller.MainViewController;
import net.nokok.twitduke.controller.tweetcellstatus.TweetCellUpdater;
import net.nokok.twitduke.controller.tweetcellstatus.UpdateCategory;
import net.nokok.twitduke.util.URLUtil;
import net.nokok.twitduke.view.TweetCell;
import net.nokok.twitduke.view.TweetPopupMenu;
import net.nokok.twitduke.view.ui.TWMenuItem;
import net.nokok.twitduke.wrapper.Twitter4jAsyncWrapper;
import twitter4j.MediaEntity;
import twitter4j.Status;
import twitter4j.URLEntity;
import twitter4j.UserMentionEntity;

class PopupMenuFactory {

    private final Twitter4jAsyncWrapper wrapper;
    private final MainViewController    mainViewController;

    /**
     * PopupMenuFactoryに必要なラッパーをセットします
     *
     * @param wrapper セットするラッパ
     */
    public PopupMenuFactory(Twitter4jAsyncWrapper wrapper, MainViewController mainViewController) {
        this.wrapper = wrapper;
        this.mainViewController = mainViewController;
    }

    /**
     * 渡されたステータスを用いて渡されたセルにPopupMenuをセットします。
     *
     * @param cell   PopupMenuをセットするセル
     * @param status PopupMenuを生成する元となるステータス
     * @return 生成されたPopupMenu
     */
    public TweetPopupMenu createPopupMenu(final TweetCell cell, final Status status) {
        TweetPopupMenu popupMenu = new TweetPopupMenu();

        cell.setMouseListener(new TweetCellMouseAdapter(popupMenu, status, cell));

        popupMenu.setReplyAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (status.isRetweet()) {
                    wrapper.replyPreprocess(status.getRetweetedStatus());
                    return;
                }
                wrapper.replyPreprocess(status);
            }
        });

        popupMenu.setFavoriteAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                favorite(cell, status.getId());
            }
        });

        popupMenu.setRetweetAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                retweet(cell, status.getId());
            }
        });

        popupMenu.setAllURLOpenAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                URLUtil.allURLEntitiesOpen(status.getURLEntities());
                URLUtil.allMediaEntitiesOpen(status.getMediaEntities());
            }
        });

        popupMenu.setHighlightAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cell.isSelected()) {
                    mainViewController.updateTweetCellStatus(new TweetCellUpdater(0, UpdateCategory.SELECTED));
                } else {
                    mainViewController.updateTweetCellStatus(new TweetCellUpdater(status.getUser().getId(), UpdateCategory.SELECTED));
                }
            }
        });


        popupMenu.setSearchAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchString = cell.getSelectedText();
                if (Strings.isNullOrEmpty(searchString)) {
                    return;
                }
                URLUtil.openInBrowser("http://www.google.co.jp/search?q=" + URLUtil.encodeString(cell.getSelectedText()));
            }
        });

        popupMenu.setDeleteAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                wrapper.deleteTweet(status.getId());
            }
        });

        for (final UserMentionEntity entity : status.getUserMentionEntities()) {
            TWMenuItem menuItem = new TWMenuItem(entity.getScreenName() + " の詳細を開く");
            menuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    wrapper.getUserInfomation(entity.getId());
                }
            });
            popupMenu.addMenuItem(menuItem);
        }

        for (final URLEntity entity : status.getURLEntities()) {
            TWMenuItem menuItem = new TWMenuItem(entity.getDisplayURL() + "を開く");
            menuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    URLUtil.openInBrowser(entity.getExpandedURL());
                }
            });
            popupMenu.addMenuItem(menuItem);
        }

        for (final MediaEntity entity : status.getMediaEntities()) {
            TWMenuItem menuItem = new TWMenuItem(entity.getDisplayURL() + "を開く");
            menuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    URLUtil.openInBrowser(entity.getExpandedURL());
                }
            });
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
