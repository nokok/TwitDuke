package net.nokok.twitduke.wrapper;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;
import net.nokok.twitduke.model.AccessTokenManager;
import net.nokok.twitduke.model.ConsumerKey;
import net.nokok.twitduke.model.factory.TweetCellFactory;
import net.nokok.twitduke.view.MainView;
import twitter4j.AsyncTwitter;
import twitter4j.AsyncTwitterFactory;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.UserStreamAdapter;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class Twitter4jAsyncWrapper {

    private long replyId = 0L;

    private final        Twitter            twitter      = TwitterFactory.getSingleton();
    private static final AsyncTwitter       asynctwitter = AsyncTwitterFactory.getSingleton();
    private final        AccessTokenManager tokenManager = AccessTokenManager.getInstance();
    private final        TweetCellFactory   factory      = new TweetCellFactory(this);
    private MainView          mainView;
    private UserStreamAdapter userStream;

    private static final Twitter4jAsyncWrapper instance = new Twitter4jAsyncWrapper();

    private Twitter4jAsyncWrapper() {
        twitter.setOAuthConsumer(ConsumerKey.TWITTER_CONSUMER_KEY, ConsumerKey.TWITTER_CONSUMER_SECRET);
        asynctwitter.setOAuthConsumer(ConsumerKey.TWITTER_CONSUMER_KEY, ConsumerKey.TWITTER_CONSUMER_SECRET);
        if (tokenManager.isAuthenticated()) {
            twitter.setOAuthAccessToken(tokenManager.readPrimaryAccount());
            asynctwitter.setOAuthAccessToken(tokenManager.readPrimaryAccount());
        } else {
            OAuthDialog dialog = new OAuthDialog();
            dialog.setVisible(true);
            dialog.setAlwaysOnTop(true);
        }
    }

    public static Twitter4jAsyncWrapper getInstance() {
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
        asynctwitter.updateStatus(status.inReplyToStatusId(replyId));
        replyId = 0;
    }

    public void replyPreprocess(Status status) {
        this.replyId = status.getId();
        mainView.setTweetTextField("@" + status.getUser().getScreenName() + " ");
    }

    public void favoriteTweet(long statusId) {
        asynctwitter.createFavorite(statusId);
    }

    public void removeFavoriteTweet(long statusId) {
        asynctwitter.destroyFavorite(statusId);
    }

    public void retweetTweet(long statusId) {
        asynctwitter.retweetStatus(statusId);
    }

    public void deleteTweet(long statusId) {
        asynctwitter.destroyStatus(statusId);
    }

    public void sendTweet(String text) {
        if (replyId != 0) {
            replyTweet(new StatusUpdate(text));
        }
        asynctwitter.updateStatus(text);
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

    class OAuthDialog extends JDialog {

        private final JTextField textField = new JTextField("");
        private final JButton    okButton  = new JButton("表示されたPINを入力後クリック");

        public OAuthDialog() {
            this.setLayout(new BorderLayout());
            this.setTitle("認証してください");
            this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            this.add(textField, BorderLayout.NORTH);
            this.add(okButton, BorderLayout.SOUTH);

            try {
                final RequestToken requestToken = asynctwitter.getOAuthRequestToken();

                try {
                    Desktop.getDesktop().browse(new URI(requestToken.getAuthenticationURL()));
                } catch (IOException | URISyntaxException e) {
                    e.printStackTrace();
                    throw new InternalError("認証URLオープンエラー");
                }

                textField.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {

                    }
                });
                okButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            okButtonClicked(requestToken);
                        } catch (TwitterException ex) {
                            ex.printStackTrace();
                        }
                    }
                });

            } catch (TwitterException e) {
                e.printStackTrace();
            }
            this.pack();
        }

        private void okButtonClicked(RequestToken requestToken) throws TwitterException {
            this.setTitle("認証処理/設定書き込み中");
            AccessToken token = asynctwitter.getOAuthAccessToken(requestToken, textField.getText());
            asynctwitter.setOAuthAccessToken(token);
            tokenManager.writeAccessToken(token);
            this.dispose();
        }
    }
}
