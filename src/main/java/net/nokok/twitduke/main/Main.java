package net.nokok.twitduke.main;

import net.nokok.twitduke.controller.MainViewController;
import net.nokok.twitduke.controller.SettingViewController;
import net.nokok.twitduke.model.ConsumerKey;
import net.nokok.twitduke.model.UserStreamListenerImpl;
import net.nokok.twitduke.model.account.AccessTokenManager;
import net.nokok.twitduke.model.thread.FileCreateWatcher;
import net.nokok.twitduke.model.thread.IFileWatcher;
import net.nokok.twitduke.view.SettingView;
import net.nokok.twitduke.wrapper.Twitter4jAsyncWrapper;
import twitter4j.ConnectionLifeCycleListener;
import twitter4j.RateLimitStatusEvent;
import twitter4j.RateLimitStatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

public class Main implements IFileWatcher {

    private Twitter4jAsyncWrapper wrapper;
    private MainViewController    mainViewController;
    private SettingViewController settingViewController;
    private TwitterStream         twitterStream;

    /**
     * TwitDukeのエントリーポイントです
     * 渡された全てのオプションは無視されます
     *
     * @param args 渡されたオプションが格納された配列
     */
    public static void main(String[] args) {
        new Main().boot();
    }

    /**
     * TwitDukeの起動処理を行います
     * アクセストークンファイルの監視を開始します
     */
    private void boot() {
        readConfigFiles();
        mainViewInitialize();
        settingViewInitialize();
        twitterAPIWrapperInitialize();
        String accessTokenFilePath = AccessTokenManager.getInstance().getTokenFileListPath();
        new FileCreateWatcher(accessTokenFilePath, this).start();
    }

    /**
     * 設定ファイルの読み込みと再設定を行います
     */
    private void readConfigFiles() {/*TODO:設定画面の実装時に追加する*/}

    /**
     * MainViewControllerの初期化を行います
     */
    private void mainViewInitialize() {
        mainViewController = new MainViewController();
    }

    /**
     * SettingViewControllerの初期化を行います
     */
    private void settingViewInitialize() {
        settingViewController = new SettingViewController(new SettingView());
    }

    /**
     * TwitterAPIWrapperの初期化を行います
     */
    private void twitterAPIWrapperInitialize() {
        twitterStream = TwitterStreamFactory.getSingleton();
        connectionLifeCycleListenerInitialize(twitterStream);
        rateLimitListenerInitialize(twitterStream);

        wrapper = Twitter4jAsyncWrapper.getInstance();
        wrapper.setView(mainViewController);
        wrapper.enableTwitterListener();
        twitterStream.setOAuthConsumer(ConsumerKey.TWITTER_CONSUMER_KEY, ConsumerKey.TWITTER_CONSUMER_SECRET);
        twitterStream.addListener(new UserStreamListenerImpl(mainViewController));
    }

    /**
     * TwitterStreamに接続状態を監視するリスナをセットします
     *
     * @param twitterStream リスナをセットするTwitterStream
     */
    private void connectionLifeCycleListenerInitialize(TwitterStream twitterStream) {
        twitterStream.addConnectionLifeCycleListener(new ConnectionLifeCycleListener() {
            @Override
            public void onConnect() {
                mainViewController.userStreamConnected();
            }

            @Override
            public void onDisconnect() {
                mainViewController.userStreamDisconnected();
            }

            @Override
            public void onCleanUp() {
            }
        });
    }

    /**
     * TwitterStreamに残りAPI状態を監視するリスナをセットします
     *
     * @param twitterStream リスナをセットするTwitterStream
     */
    private void rateLimitListenerInitialize(TwitterStream twitterStream) {
        twitterStream.addRateLimitStatusListener(new RateLimitStatusListener() {
            @Override
            public void onRateLimitStatus(RateLimitStatusEvent event) {
                mainViewController.setNotification("onRateLimitStatus:" + event.getRateLimitStatus());
            }

            @Override
            public void onRateLimitReached(RateLimitStatusEvent event) {
                mainViewController.setNotification("onRateLimitReached:" + event.getRateLimitStatus());
            }
        });
    }

    /**
     * 認証ファイルが書き込まれたらFileCreateWatcherによって呼ばれます
     *
     * @see net.nokok.twitduke.model.thread.FileCreateWatcher
     */
    @Override
    public void filesCreated() {
        startUserStream();
        fetchTimelines();
    }

    /**
     * UserStreamの受信を開始します。
     */
    private void startUserStream() {
        mainViewController.start(wrapper, settingViewController);
        twitterStream.setOAuthAccessToken(AccessTokenManager.getInstance().readPrimaryAccount());
        twitterStream.user();
    }

    /**
     * TwitterAPIからタイムラインを非同期で取得します
     */
    private void fetchTimelines() {
        wrapper.getMentions();
        wrapper.getHomeTimeLine();
    }
}
