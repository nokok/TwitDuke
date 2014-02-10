package net.nokok.twitduke.model.factory;

import com.google.common.collect.Lists;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import javax.swing.ImageIcon;
import net.nokok.twitduke.model.account.AccessTokenManager;
import net.nokok.twitduke.util.DateUtil;
import net.nokok.twitduke.util.ImageSizeChanger;
import net.nokok.twitduke.util.URLUtil;
import net.nokok.twitduke.view.ImageView;
import net.nokok.twitduke.view.TweetCell;
import net.nokok.twitduke.view.ui.TWLabel;
import net.nokok.twitduke.wrapper.Twitter4jAsyncWrapper;
import twitter4j.EntitySupport;
import twitter4j.MediaEntity;
import twitter4j.Status;

public class TweetCellFactory {

    private static final int RETWEET_ICON_WIDTH  = 15;
    private static final int RETWEET_ICON_HEIGHT = 15;
    private final Twitter4jAsyncWrapper wrapper;
    private final PopupMenuFactory      popupMenuFactory;

    public TweetCellFactory(Twitter4jAsyncWrapper twitter) {
        wrapper = twitter;
        popupMenuFactory = new PopupMenuFactory(wrapper);
    }

    /**
     * 渡されたツイートのステータスでツイートセルを作成して返します
     *
     * @param status ツイートのステータス
     * @return 作成されたツイートセル
     */
    public TweetCell createTweetCell(Status status) {

        boolean isMention = isMention(status);

        TweetCell cell;

        if (status.isRetweet()) {
            cell = createRetweetCell(isMention, status);
        } else {
            cell = createNormalCell(isMention, status);
        }
        setCommonActionListener(cell, status);
        popupMenuFactory.createPopupMenu(cell, status);
        setThumbnail(cell, status);
        return cell;
    }

    /**
     * ツイートのステータスに含まれる画像ファイルを取り出してサムネイルとしてセットします
     *
     * @param cell   サムネイルをセットするツイートセル
     * @param status ツイートのステータス
     */
    private void setThumbnail(TweetCell cell, EntitySupport status) {
        if (status.getMediaEntities().length == 0) {
            return;
        }
        if (DateUtil.isMealTerroTime()) {
            TWLabel label = new TWLabel("[クリックで画像を表示]");
            label.setFont(new Font("", Font.BOLD, 10));
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    Lists.newArrayList(status.getMediaEntities()).forEach(ex -> new ImageView(URLUtil.createURL(ex.getMediaURL())).setVisible(true));
                }
            });
            cell.setThumbnail(label);
            return;
        }
        Lists.newArrayList(status.getMediaEntities()).forEach((MediaEntity e) -> addThumbnail(cell, e));
    }

    /**
     * セルのサムネイルを作成してセルに貼り付けます
     *
     * @param cell   サムネイルをはりつけるセル
     * @param entity セルに貼り付ける画像のエンティティ
     */
    private void addThumbnail(TweetCell cell, MediaEntity entity) {
        URL imageURL = URLUtil.createURL(entity.getMediaURL());
        TWLabel image = new TWLabel(ImageSizeChanger.createThumbnail(new ImageIcon(imageURL)));
        image.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ImageView view = new ImageView(imageURL);
                view.setVisible(true);
            }
        });
        cell.setThumbnail(image);
    }

    /**
     * 渡されたツイートのステータスが自分へのリプライが含まれているかどうかを判定します
     *
     * @param status ツイートのステータス
     * @return リプライが含まれている場合にtrue
     */
    private boolean isMention(Status status) {
        return status.getText().contains("@" + AccessTokenManager.getInstance().getUserName()) && !status.isRetweet();
    }

    /**
     * 通常のセルを作成します。isMentionがtrueの場合はリプライ用の色に着色されます
     *
     * @param isMention リプライかどうか
     * @param status    ツイートのステータス
     * @return 生成されたツイートセル
     */
    private TweetCell createNormalCell(boolean isMention, Status status) {
        URL userIconURL = URLUtil.createURL(status.getUser().getProfileImageURLHttps());
        return new TweetCell(isMention,
                             status.getId(),
                             new ImageIcon(userIconURL),
                             status.getUser().getScreenName(),
                             URLUtil.extendURL(status));

    }

    /**
     * リツイート用のセルを作成します。isMentionは今のところ動作せず、リツイート用の色に着色されます
     *
     * @param isMention リプライかどうか
     * @param status    ツイートのステータス
     * @return 生成されたツイートセル
     */
    private TweetCell createRetweetCell(boolean isMention, Status status) {
        URL userIconURL = URLUtil.createURL(status.getRetweetedStatus().getUser().getProfileImageURLHttps());
        URL retweetIconURL = URLUtil.createURL(status.getUser().getProfileImageURLHttps());
        Image retweetUserImage = new ImageIcon(retweetIconURL).getImage().getScaledInstance(RETWEET_ICON_WIDTH, RETWEET_ICON_HEIGHT, Image.SCALE_FAST);
        return new TweetCell(isMention,
                             status.getId(),
                             new ImageIcon(userIconURL),
                             new ImageIcon(retweetUserImage),
                             "Retweet: " + status.getRetweetedStatus().getUser().getScreenName() + " by " + status.getUser().getScreenName(),
                             URLUtil.extendURL(status.getRetweetedStatus()));
    }

    /**
     * ツイートセル共通のアクションリスナを渡されたセルにセットします
     *
     * @param cell   アクションリスナをセットするセル
     * @param status ツイートのステータス
     */
    private void setCommonActionListener(TweetCell cell, Status status) {
        cell.setFavoriteAction(e -> favorite(cell, status.getId()));
        cell.setRetweetAction(e -> retweet(cell, status.getId()));
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
