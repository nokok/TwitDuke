package net.nokok.twitduke.controller;

import javax.swing.SwingUtilities;
import net.nokok.twitduke.main.Config;
import net.nokok.twitduke.model.CommandParser;
import net.nokok.twitduke.model.IParser;
import net.nokok.twitduke.model.ParsingResultListener;
import net.nokok.twitduke.model.factory.TweetCellFactory;
import net.nokok.twitduke.model.impl.CommandKeyListenerImpl;
import net.nokok.twitduke.model.impl.ParserStateListenerImpl;
import net.nokok.twitduke.model.impl.ParsingResultListenerImpl;
import net.nokok.twitduke.model.impl.SendTweetKeyListenerImpl;
import net.nokok.twitduke.model.listener.CellInsertionListener;
import net.nokok.twitduke.model.listener.NotificationListener;
import net.nokok.twitduke.model.listener.ReplyListener;
import net.nokok.twitduke.model.listener.SendTweetListener;
import net.nokok.twitduke.model.listener.TweetCellUpdateListener;
import net.nokok.twitduke.model.thread.TitleAnimationInvoker;
import net.nokok.twitduke.view.MainView;
import net.nokok.twitduke.view.tweetcell.TweetCell;
import net.nokok.twitduke.view.ui.TWLabel;
import net.nokok.twitduke.wrapper.Twitter4jAsyncWrapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import twitter4j.Status;

public class MainViewController implements
    ReplyListener,
    CellInsertionListener,
    SendTweetListener {

    private final Log logger = LogFactory.getLog(MainViewController.class);

    private Twitter4jAsyncWrapper   wrapper;
    private TweetCellFactory        tweetCellFactory;
    private MainView                mainView;
    private IParser                 parser;
    private TweetCellUpdateListener updateListener;
    private ParsingResultListener   parsingResultListener;

    /**
     * MainViewControllerの初期化に必要な処理を開始します
     *
     * @param wrapper Twitter4jのラッパクラス
     * @see net.nokok.twitduke.wrapper.Twitter4jAsyncWrapper
     */
    public void start(Twitter4jAsyncWrapper wrapper,
                      NotificationListener notificationListener,
                      TweetCellUpdateListener updateListener) {
        logger.info("MainViewControllerの初期化を開始します");

        mainView = new MainView();
        this.wrapper = wrapper;
        parsingResultListener = new ParsingResultListenerImpl();
        parser = new CommandParser(parsingResultListener, new ParserStateListenerImpl(mainView));
        this.updateListener = updateListener;
        tweetCellFactory = new TweetCellFactory(wrapper, updateListener);
        mainView.setVisible(true);
        bindActionListener();
        notificationListener.setNotification("UserStreamに接続中です");
    }

    /**
     * UserStream接続成功時のタイトルバーのアニメーション処理を実行します
     *
     * @see net.nokok.twitduke.model.thread.TitleAnimationInvoker
     */
    public void launchTitleAnimation() {
        logger.trace("タイトルバーのアニメーションスレッドを開始します");

        new TitleAnimationInvoker(mainView).start();
    }

    /**
     * 通知を表示するラベルをデフォルトの位置に戻します
     */
    public void notificationLabelMoveToDefault() {
        mainView.moveStatusLabel(5, 5);
    }

    /**
     * MainViewの横幅が通知を表示するラベルの横幅より大きいかを返します
     *
     * @return MainViewの幅 < 通知ラベルの場合 true
     */
    public boolean isLargerThanNotificationLabel() {
        return mainView.getWidth() < mainView.getNotificationLabelWidth();
    }

    /**
     * リプライ用のメソッドです
     * 渡されたスクリーンネームに@マークと半角スペースを付けてテキストフィールドにセットします
     *
     * @param screenName リプライを送信するユーザーのスクリーンネーム
     */
    @Override
    public void setReply(String screenName) {
        logger.debug(screenName + "がセットされました");

        mainView.setTweetText('@' + screenName + ' ');
    }

    private boolean isUnofficialRT(String tweetText) {
        return (tweetText.contains("QT") || tweetText.contains("RT")) && Config.Flags.isMuteUnOfficialRT;
    }

    /**
     * MainViewのツールバーにあるボタンにアクションリスナーを設定します
     */
    private void bindActionListener() {
        logger.trace("アクションリスナーをセットします");

        mainView.addTextAreaAction(new CommandKeyListenerImpl(parsingResultListener, mainView.getTweetTextArea(), parser));
        mainView.addTextAreaAction(new SendTweetKeyListenerImpl(this));
    }

    /**
     * ツイート送信時のView側の処理を行います
     * ラッパクラスに入力されたツイートを渡した後、テキストフィールドのテキストをクリアします
     */
    @Override
    public void sendTweet() {
        logger.info("ツイートを送信します");

        wrapper.sendTweet(mainView.getTweetText());
        mainView.clearTextField();
    }

    /**
     * @return 通知を表示するラベルを返します
     */
    public TWLabel getNotificationLabel() {
        return mainView.getNotificationLabel();
    }

    /**
     * ツイートセルをビューに挿入します
     * もしスクロールバーが一番上に無ければセルの高さ分スクロールバーを移動し、
     * セル挿入によって発生するずれを防止します。セルがもし自分へのリプライのツイートだった場合、
     * 同じセルをメンションリストに挿入するとメインリストのセルがメンションリストに移動してしまうため
     * セルを再生成してメンションリストへ挿入します。
     * ただし、ツイートにRTやQTが含まれる非公式RTの場合はこのメンションリストへの挿入処理はスキップされます。
     *
     * @param status TweetCellを生成するステータス
     */
    @Override
    public void insertCell(Status status) {

        if (logger.isTraceEnabled()) {
            logger.trace("セルを挿入します");
        }

        TweetCell cell = tweetCellFactory.createTweetCell(status);
        SwingUtilities.invokeLater(() -> {
            mainView.insertTweetCell(cell);
            if (cell.isMention() && !isUnofficialRT(status.getText())) {
                mainView.insertMentionTweetCell(tweetCellFactory.createTweetCell(status));
            }
            if (!mainView.isScrollbarTop()) {
                mainView.shiftScrollBar(cell.getHeight() + 1);
            }

            if (logger.isTraceEnabled()) {
                logger.trace("セルが挿入されました");
            }

        });
        updateListener.set(cell, status);

        logger.trace("セルがリスナーにセットされました");
    }
}
