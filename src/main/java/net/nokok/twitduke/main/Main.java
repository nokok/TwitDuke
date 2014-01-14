package net.nokok.twitduke.main;

import net.nokok.twitduke.controller.MainViewController;
import net.nokok.twitduke.model.ConsumerKey;
import net.nokok.twitduke.model.UserStreamListenerImpl;
import net.nokok.twitduke.model.account.AccessTokenManager;
import net.nokok.twitduke.model.thread.BootFileWatcher;
import net.nokok.twitduke.wrapper.Twitter4jAsyncWrapper;
import twitter4j.ConnectionLifeCycleListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

public class Main {

    Twitter4jAsyncWrapper wrapper;
    MainViewController    mainViewController;
    TwitterStream         twitterStream;

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
        twitterAPIWrapperInitialize();
        new BootFileWatcher(AccessTokenManager.getInstance().getTokenFileListPath(), this).start();
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
     * TwitterAPIWrapperの初期化を行います
     */
    private void twitterAPIWrapperInitialize() {
        twitterStream = TwitterStreamFactory.getSingleton();
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
        wrapper = Twitter4jAsyncWrapper.getInstance();
        wrapper.setView(mainViewController);
        wrapper.enableTwitterListener();
        twitterStream.setOAuthConsumer(ConsumerKey.TWITTER_CONSUMER_KEY, ConsumerKey.TWITTER_CONSUMER_SECRET);
        twitterStream.addListener(new UserStreamListenerImpl(mainViewController));
    }

    /**
     * 認証ファイルが書き込まれたらBootFileWatcherによって呼ばれます
     */
    public void writeAccessTokenFilesFinished() {
        startUserStream();
        fetchTimelines();
    }

    /**
     * UserStreamの受信を開始します。
     */
    private void startUserStream() {
        mainViewController.start(wrapper);
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
