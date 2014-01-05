package net.nokok.twitduke.wrapper;

import net.nokok.twitduke.model.AccountManager;
import net.nokok.twitduke.model.TweetCellFactory;
import net.nokok.twitduke.model.TwitterInstanceManager;
import net.nokok.twitduke.view.MainView;
import net.nokok.twitduke.view.OAuthDialog;
import twitter4j.AsyncTwitter;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.UserStreamAdapter;

public class Twitter4jWrapper {

    private long replyId = 0L;

    private final TweetCellFactory factory = new TweetCellFactory(this);

    private MainView          mainView;
    private UserStreamAdapter userStream;
    private Twitter           twitter;
    private AsyncTwitter      asyncTwitter;

    private static final Twitter4jWrapper instance = new Twitter4jWrapper();

    private Twitter4jWrapper() {
        AccountManager accountManager = AccountManager.getInstance();
        if (accountManager.accountCount() != 0) {
            TwitterInstanceManager instanceManager = TwitterInstanceManager.getInstance();
            twitter = instanceManager.getCurrentTwitterInstance();
            asyncTwitter = instanceManager.getCurrentAsyncTwitter();
        } else {
            OAuthDialog dialog = new OAuthDialog();
            dialog.setVisible(true);
            dialog.setAlwaysOnTop(true);
        }
    }

    public static Twitter4jWrapper getInstance() {
        return instance;
    }

    public UserStreamAdapter getUserStream() {
        return userStream;
    }

    public void setView(MainView mainView) {
        this.mainView = mainView;
        if (userStream == null) {
            userStream = new MyUserStreamAdapter(mainView);
        }
    }

    public void replyTweet(StatusUpdate status) {
        asyncTwitter.updateStatus(status.inReplyToStatusId(replyId));
        replyId = 0;
    }

    public void replyPreprocess(Status status) {
        this.replyId = status.getId();
        mainView.setTweetTextField("@" + status.getUser().getScreenName() + " ");
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
        }
        asyncTwitter.updateStatus(text);
    }

    public ResponseList<Status> fetchHomeTimeLine() {
        try {
            return twitter.getHomeTimeline();
        } catch (TwitterException e) {
            e.printStackTrace();
            throw new InternalError("タイムラインの取得中にエラーが発生しました。Twitter側のエラーです");
        }
    }

    public ResponseList<Status> fetchMentionsTimeLine() {
        try {
            return twitter.getMentionsTimeline();
        } catch (TwitterException e) {
            e.printStackTrace();
            throw new InternalError("メンションの取得中にエラーが発生しました。Twitter側のエラーです");
        }
    }

    public ResponseList<Status> fetchUserTimeLine(long userId) {
        try {
            return twitter.getUserTimeline(userId);
        } catch (TwitterException e) {
            e.printStackTrace();
            throw new InternalError("ユーザーのタイムライン取得中にエラーが発生しました。Twitter側のエラーです");
        }
    }


    class MyUserStreamAdapter extends UserStreamAdapter {

        private MainView view;

        public MyUserStreamAdapter(MainView view) {
            this.view = view;
        }

        @Override
        public void onStatus(Status status) {
            view.insertTweetCell(factory.createTweetCell(status));
        }
    }

}
