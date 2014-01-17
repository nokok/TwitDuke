package net.nokok.twitduke.controller;

import net.nokok.twitduke.model.factory.TweetCellFactory;
import net.nokok.twitduke.model.thread.NotificationBarAnimationInvoker;
import net.nokok.twitduke.model.thread.TitleAnimationInvoker;
import net.nokok.twitduke.view.MainView;
import net.nokok.twitduke.wrapper.Twitter4jAsyncWrapper;
import twitter4j.Status;

public class MainViewController {

    private Twitter4jAsyncWrapper wrapper;
    private TweetCellFactory      tweetCellFactory;
    private MainView              mainView;

    /**
     * MainViewControllerの初期化に必要な処理を開始します
     *
     * @param wrapper Twitter4jのラッパクラス
     * @see net.nokok.twitduke.wrapper.Twitter4jAsyncWrapper
     */
    public void start(Twitter4jAsyncWrapper wrapper) {
        mainView = new MainView();
        this.wrapper = wrapper;
        this.tweetCellFactory = new TweetCellFactory(wrapper);
        mainView.setVisible(true);
        bindActionListener();
        this.setNotification("UserStreamに接続中です");
    }

    /**
     * UserStreamに接続された時に呼び出されます
     *
     * @see net.nokok.twitduke.main.Main#boot()
     * @see net.nokok.twitduke.main.Main#twitterAPIWrapperInitialize()
     */
    public void userStreamConnected() {
        this.setNotification("UserStreamに接続しました");
        launchTitleAnimation();
    }

    /**
     * UserStreamとの接続が切れた時、切断された時に呼び出されます
     *
     * @see net.nokok.twitduke.main.Main#boot()
     * @see net.nokok.twitduke.main.Main#twitterAPIWrapperInitialize()
     */
    public void userStreamDisconnected() {
        this.setNotification("UserStreamとの接続が切れています");
    }


    /**
     * UserStream接続成功時のタイトルバーのアニメーション処理を実行します
     *
     * @see net.nokok.twitduke.model.thread.TitleAnimationInvoker
     */
    private void launchTitleAnimation() {
        new TitleAnimationInvoker(mainView).start();
    }

    /**
     * MainViewのステータスバーに通知を表示します
     * 通知は設定した秒数後(規定値:5秒)に消えるようアニメーション処理が実行されます
     * また、通知が消える前に新たな通知が発生した場合、表示している通知は消え、新しい通知が表示されます
     *
     * @param text 表示する通知のテキスト
     * @see net.nokok.twitduke.model.thread.NotificationBarAnimationInvoker
     */
    public void setNotification(String text) {
        new NotificationBarAnimationInvoker(mainView, text).start();
    }

    /**
     * リプライ用のメソッドです
     * 渡されたスクリーンネームに@マークと半角スペースを付けてテキストフィールドにセットします
     *
     * @param screenName リプライを送信するユーザーのスクリーンネーム
     */
    public void setReply(String screenName) {
        mainView.setTweetTextField("@" + screenName + " ");
    }

    /**
     * ツイートセルを挿入します
     * TweetCellFactoryを呼び出しTweetCellを作成した後MainViewに挿入します
     *
     * @param status TweetCellを生成するステータス
     */
    public void insertTweetCell(Status status) {
        mainView.insertTweetCell(tweetCellFactory.createTweetCell(status));
    }

    /**
     * MainViewのツールバーにあるボタンにアクションリスナーを設定します
     */
    private void bindActionListener() {
        mainView.setSendButtonAction(e -> sendTweet());
        mainView.setMentionButtonAction(e -> mainView.swapTweetList());
        mainView.setTextFieldAction(e -> sendTweet());
    }

    /**
     * ツイート送信時のView側の処理を行います
     * ラッパクラスに入力されたツイートを渡した後、テキストフィールドのテキストをクリアします
     */
    private void sendTweet() {
        wrapper.sendTweet(mainView.getTweetText());
        mainView.clearTextField();
    }
}
