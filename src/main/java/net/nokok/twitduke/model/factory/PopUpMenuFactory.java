package net.nokok.twitduke.model.factory;

import com.google.common.base.Strings;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import net.nokok.twitduke.view.TweetCell;
import net.nokok.twitduke.view.TweetPopupMenu;
import net.nokok.twitduke.view.UserView;
import net.nokok.twitduke.view.ui.TWMenuItem;
import net.nokok.twitduke.wrapper.Twitter4jAsyncWrapper;
import twitter4j.MediaEntity;
import twitter4j.Status;
import twitter4j.URLEntity;
import twitter4j.UserMentionEntity;

public class PopupMenuFactory {

    private final UserViewFactory userViewFactory = new UserViewFactory();
    private final Twitter4jAsyncWrapper wrapper;

    /**
     * PopupMenuFactoryに必要なラッパーをセットします
     *
     * @param wrapper セットするラッパ
     */
    public PopupMenuFactory(Twitter4jAsyncWrapper wrapper) {
        this.wrapper = wrapper;
    }

    /**
     * 渡されたステータスを用いて渡されたセルにPopupMenuをセットします。
     *
     * @param cell   PopupMenuをセットするセル
     * @param status PopupMenuを生成する元となるステータス
     * @return 生成されたPopupMenu
     */
    public TweetPopupMenu createPopupMenu(final TweetCell cell, final Status status) {
        final TweetPopupMenu popupMenu = new TweetPopupMenu();
        MouseAdapter functionPanelMouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        };

        MouseAdapter userViewMouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() >= 2) {
                    UserView view = userViewFactory.createUserView(status.isRetweet() ? status.getRetweetedStatus().getUser() : status.getUser());
                    view.setLocation(e.getLocationOnScreen());
                    view.setVisible(true);
                    cell.clearSelectedText();
                }
            }
        };

        cell.addMouseListener(functionPanelMouseAdapter);
        cell.addMouseListener(userViewMouseAdapter);
        cell.setTextAreaAction(functionPanelMouseAdapter);
        cell.setTextAreaAction(userViewMouseAdapter);
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
            allURLEntitiesOpen(status.getURLEntities());
            allMediaEntitiesOpen(status.getMediaEntities());
        });

        popupMenu.setSearchAction(e -> {
            String searchString = cell.getSelectedText();
            if (Strings.isNullOrEmpty(searchString)) {
                return;
            }
            try {
                Desktop.getDesktop().browse(new URI("http://www.google.co.jp/search?q=" + URLEncoder.encode(cell.getSelectedText(), "utf-8")));
            } catch (IOException | URISyntaxException ex) {
                ex.printStackTrace();
            }
        });

        popupMenu.setDeleteAction(e -> wrapper.deleteTweet(status.getId()));

        for (final UserMentionEntity entity : status.getUserMentionEntities()) {
            TWMenuItem menuItem = new TWMenuItem(entity.getScreenName() + " の詳細を開く");
            menuItem.addActionListener(e -> wrapper.getUserInfomation(entity.getId()));
            popupMenu.addMenuItem(menuItem);
        }

        for (final URLEntity entity : status.getURLEntities()) {
            TWMenuItem menuItem = new TWMenuItem(entity.getDisplayURL() + "を開く");
            menuItem.addActionListener(e -> {
                try {
                    Desktop.getDesktop().browse(new URI(entity.getExpandedURL()));
                } catch (IOException | URISyntaxException ex) {
                    ex.printStackTrace();
                }
            });
            popupMenu.addMenuItem(menuItem);
        }

        for (final MediaEntity entity : status.getMediaEntities()) {
            TWMenuItem menuItem = new TWMenuItem(entity.getDisplayURL() + "を開く");
            menuItem.addActionListener(e -> {
                try {
                    Desktop.getDesktop().browse(new URI(entity.getExpandedURL()));
                } catch (IOException | URISyntaxException ex) {
                    ex.printStackTrace();
                }
            });
            popupMenu.addMenuItem(menuItem);
        }
        return popupMenu;
    }

    /**
     * 全てのURLエンティティを既定のブラウザで開きます
     *
     * @param entities URLエンティティの配列
     */
    private void allURLEntitiesOpen(URLEntity[] entities) {
        for (URLEntity entity : entities) {
            try {
                Desktop.getDesktop().browse(new URI(entity.getExpandedURL()));
            } catch (IOException | URISyntaxException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 全てのメディアエンティティを既定のブラウザで開きます
     *
     * @param entities メディアエンティティの配列
     */
    private void allMediaEntitiesOpen(MediaEntity[] entities) {
        for (URLEntity entity : entities) {
            try {
                Desktop.getDesktop().browse(new URI(entity.getExpandedURL()));
            } catch (IOException | URISyntaxException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * お気に入りのセル側の処理です。
     *
     * @param cell     お気に入りのアクションが発生したセル
     * @param statusId お気に入り登録/登録解除するステータスのID
     */
    private void favorite(TweetCell cell, long statusId) {
        if (cell.toggleFavoriteState()) {
            wrapper.favoriteTweet(statusId);
        } else {
            wrapper.removeFavoriteTweet(statusId);
        }
    }

    /**
     * リツイートのセル側の処理です
     *
     * @param cell     リツイートのアクションが発生したセル
     * @param statusId リツイートするステータスのID
     */
    private void retweet(TweetCell cell, long statusId) {
        wrapper.retweetTweet(statusId);
        cell.toggleRetweetState();
    }
}
