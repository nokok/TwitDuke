package net.nokok.twitduke.wrapper;

import java.util.Random;
import net.nokok.twitduke.main.Config;
import net.nokok.twitduke.model.account.AccessTokenManager;
import net.nokok.twitduke.model.impl.TwitterListenerImpl;
import net.nokok.twitduke.model.listener.CellInsertionListener;
import net.nokok.twitduke.model.listener.NotificationListener;
import net.nokok.twitduke.model.listener.ReplyListener;
import net.nokok.twitduke.view.OAuthDialog;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import twitter4j.AsyncTwitter;
import twitter4j.AsyncTwitterFactory;
import twitter4j.Status;
import twitter4j.StatusUpdate;

public class Twitter4jAsyncWrapper {

    private long replyId;

    private final Log logger = LogFactory.getLog(Twitter4jAsyncWrapper.class);

    private static final AsyncTwitter          asyncTwitter = AsyncTwitterFactory.getSingleton();
    private static final Twitter4jAsyncWrapper wrapper      = new Twitter4jAsyncWrapper();

    private NotificationListener  notificationListener;
    private ReplyListener         replyListener;
    private CellInsertionListener cellInsertionListener;

    private Twitter4jAsyncWrapper() {
        asyncTwitter.setOAuthConsumer(Config.TWITTER_CONSUMER_KEY, Config.TWITTER_CONSUMER_SECRET);
        AccessTokenManager tokenManager = AccessTokenManager.getInstance();
        if (tokenManager.isTokenListExists()) {
            asyncTwitter.setOAuthAccessToken(tokenManager.readPrimaryAccount());
        } else {
            logger.info("認証ダイアログを表示します");
            OAuthDialog dialog = new OAuthDialog(asyncTwitter, tokenManager);
            dialog.setVisible(true);
            dialog.setAlwaysOnTop(true);
        }
    }

    public static Twitter4jAsyncWrapper getInstance() {
        return wrapper;
    }

    public void setCellInsertionListener(CellInsertionListener cellInsertionListener) {

        this.cellInsertionListener = cellInsertionListener;
    }

    public void setNotificationListener(NotificationListener notificationListener) {
        this.notificationListener = notificationListener;
    }

    public void setReplyListener(ReplyListener replyListener) {
        this.replyListener = replyListener;
    }

    public void enableTwitterListener() {
        TwitterListenerImpl twitterListenerImpl = new TwitterListenerImpl();
        twitterListenerImpl.setCellInsertionListener(cellInsertionListener);
        twitterListenerImpl.setNotificationListener(notificationListener);
        asyncTwitter.addListener(twitterListenerImpl);
    }

    void replyTweet(StatusUpdate status) {
        asyncTwitter.updateStatus(status.inReplyToStatusId(replyId));
        replyId = 0;
    }

    public void replyPreprocess(Status status) {
        replyId = status.getId();
        replyListener.setReply(status.getUser().getScreenName());
    }

    public void favoriteTweet(long statusId) {
        asyncTwitter.createFavorite(statusId);
    }

    public void removeFavoriteTweet(long statusId) {
        asyncTwitter.destroyFavorite(statusId);
    }

    public void retweetTweet(long statusId) {
        asyncTwitter.retweetStatus(statusId);
    }

    public void deleteTweet(long statusId) {
        asyncTwitter.destroyStatus(statusId);
    }

    public void sendTweet(String text) {
        if (replyId != 0) {
            replyTweet(new StatusUpdate(text));
            return;
        }
        if (text.startsWith("D ")) {
            String screenName = text.split(" ")[1];
            sendDM(screenName, text.replace(screenName, "").substring(2));
            return;
        }
        if (text.isEmpty()) {
            notificationListener.setNotification("ツイートが空です。JavaJavaするにはツイートボタンを右クリックをして下さい。");
            return;
        }
        asyncTwitter.updateStatus(text);
    }

    void sendDM(String screenName, String text) {
        asyncTwitter.sendDirectMessage(screenName, text);
    }

    public void getHomeTimeLine() {
        asyncTwitter.getHomeTimeline();
    }

    public void getMentions() {
        asyncTwitter.getMentions();
    }

    public void getUserInfomation(long userId) {
        long[] users = {userId};
        asyncTwitter.lookupUsers(users);
    }

    public void sendJavaJava() {
        int wCount = new Random().nextInt(Config.JAVAJAVA_MAX_W_COUNT);
        StringBuilder stringBuilder = new StringBuilder("JavaJavaJava〜〜〜");
        for (int i = 0; i < wCount; i++) {
            stringBuilder.append('ｗ');
        }
        asyncTwitter.updateStatus(stringBuilder.toString());
    }
}
