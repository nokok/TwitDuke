package net.nokok.twitduke.wrapper;

import java.util.Random;
import net.nokok.twitduke.controller.MainViewController;
import net.nokok.twitduke.main.Config;
import net.nokok.twitduke.model.TwitterListenerImpl;
import net.nokok.twitduke.model.account.AccessTokenManager;
import net.nokok.twitduke.view.OAuthDialog;
import twitter4j.AsyncTwitter;
import twitter4j.AsyncTwitterFactory;
import twitter4j.Status;
import twitter4j.StatusUpdate;

public class Twitter4jAsyncWrapper {

    private long replyId;

    private static final AsyncTwitter       asyncTwitter = AsyncTwitterFactory.getSingleton();
    private final        AccessTokenManager tokenManager = AccessTokenManager.getInstance();
    private MainViewController mainViewController;

    private static final Twitter4jAsyncWrapper wrapper = new Twitter4jAsyncWrapper();

    private Twitter4jAsyncWrapper() {
        asyncTwitter.setOAuthConsumer(Config.TWITTER_CONSUMER_KEY, Config.TWITTER_CONSUMER_SECRET);
        if (tokenManager.isTokenListExists()) {
            asyncTwitter.setOAuthAccessToken(tokenManager.readPrimaryAccount());
        } else {
            OAuthDialog dialog = new OAuthDialog(asyncTwitter, tokenManager);
            dialog.setVisible(true);
            dialog.setAlwaysOnTop(true);
        }
    }

    public static Twitter4jAsyncWrapper getInstance() {
        return wrapper;
    }

    public void setController(MainViewController mainViewController) {
        this.mainViewController = mainViewController;
    }

    public void enableTwitterListener() {
        asyncTwitter.addListener(new TwitterListenerImpl(mainViewController));
    }

    public void replyTweet(StatusUpdate status) {
        asyncTwitter.updateStatus(status.inReplyToStatusId(replyId));
        replyId = 0;
    }

    public void replyPreprocess(Status status) {
        replyId = status.getId();
        mainViewController.setReply(status.getUser().getScreenName());
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
        if (text.isEmpty()) {
            mainViewController.setNotification("ツイートが空です。JavaJavaするにはツイートボタンを右クリックをして下さい。");
            return;
        }
        asyncTwitter.updateStatus(text);
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
