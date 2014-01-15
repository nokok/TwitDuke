package net.nokok.twitduke.model.factory;

import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import javax.swing.ImageIcon;
import net.nokok.twitduke.model.account.AccessTokenManager;
import net.nokok.twitduke.util.ImageSizeChanger;
import net.nokok.twitduke.util.URLUtil;
import net.nokok.twitduke.view.ImageView;
import net.nokok.twitduke.view.TweetCell;
import net.nokok.twitduke.view.ui.TWLabel;
import net.nokok.twitduke.wrapper.Twitter4jAsyncWrapper;
import twitter4j.MediaEntity;
import twitter4j.Status;

public class TweetCellFactory {

    private final Twitter4jAsyncWrapper wrapper;
    private final PopupMenuFactory      popupMenuFactory;

    private final String ICON_INTERNAL_ERROR_MESSAGE = "ユーザーのアイコン取得中にエラーが発生しました";

    public TweetCellFactory(Twitter4jAsyncWrapper twitter) {
        this.wrapper = twitter;
        this.popupMenuFactory = new PopupMenuFactory(wrapper);
    }

    /**
     * 渡されたツイートのステータスでツイートセルを作成して返します
     *
     * @param status ツイートのステータス
     * @return 作成されたツイートセル
     */
    public TweetCell createTweetCell(final Status status) {

        boolean isMention = isMention(status);

        final TweetCell cell;

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
    private void setThumbnail(TweetCell cell, Status status) {
        for (MediaEntity entity : status.getMediaEntities()) {
            final URL thumbnailURL = URLUtil.createURL(entity.getMediaURL());
            TWLabel image = new TWLabel(ImageSizeChanger.createThumbnail(new ImageIcon(thumbnailURL)));
            image.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        ImageView view = new ImageView(thumbnailURL);
                        view.setVisible(true);
                    }
                }
            });
            cell.setThumbnail(image);
        }
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
        URL userIconURL, retweetIconURL;
        userIconURL = URLUtil.createURL(status.getRetweetedStatus().getUser().getProfileImageURLHttps());
        retweetIconURL = URLUtil.createURL(status.getUser().getProfileImageURLHttps());
        Image retweetUserImage = new ImageIcon(retweetIconURL).getImage().getScaledInstance(15, 15, Image.SCALE_FAST);
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
    private void setCommonActionListener(final TweetCell cell, final Status status) {
        cell.setFavoriteAction(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                favorite(cell, status.getId());
            }
        });
        cell.setRetweetAction(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                retweet(cell, status.getId());
            }
        });
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
