package net.nokok.twitduke.model.factory;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import javax.swing.ImageIcon;
import net.nokok.twitduke.model.account.AccessTokenManager;
import net.nokok.twitduke.model.thread.AsyncImageLoader;
import net.nokok.twitduke.util.DateUtil;
import net.nokok.twitduke.util.URLUtil;
import net.nokok.twitduke.view.TweetCell;
import net.nokok.twitduke.view.ui.TWLabel;
import net.nokok.twitduke.wrapper.Twitter4jAsyncWrapper;
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
    private void setThumbnail(TweetCell cell, final Status status) {
        if (status.getMediaEntities().length == 0) {
            return;
        }
        if (DateUtil.isMealTerroTime()) {
            TWLabel label = new TWLabel("[クリックで画像を表示]");
            label.setFont(new Font("", Font.BOLD, 10));
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    addAllThumbnail(cell, status);
                    label.setText("");
                }
            });
            cell.enableThumbnail(label);
            return;
        }
        addAllThumbnail(cell, status);
    }

    /**
     * ステータスに含まれる全てのサムネイルをセルに貼り付けます。
     * 画像の読み込みはAsyncImageLoaderスレッドで非同期でセルに反映されます
     *
     * @param cell   サムネイルを貼り付けるセル
     * @param status セルに貼り付ける画像のステータス
     */
    private void addAllThumbnail(TweetCell cell, Status status) {
        for (MediaEntity entity : status.getMediaEntities()) {
            new AsyncImageLoader(entity.getMediaURL(), cell).start();
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
    private void setCommonActionListener(final TweetCell cell, final Status status) {
        cell.setFavoriteAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                favorite(cell, status.getId());
            }
        });
        cell.setRetweetAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
