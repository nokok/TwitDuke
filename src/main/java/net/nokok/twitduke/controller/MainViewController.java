package net.nokok.twitduke.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import net.nokok.twitduke.main.Config;
import net.nokok.twitduke.model.factory.TweetCellFactory;
import net.nokok.twitduke.model.thread.NotificationBarAnimationInvoker;
import net.nokok.twitduke.model.thread.TitleAnimationInvoker;
import net.nokok.twitduke.util.MouseUtil;
import net.nokok.twitduke.view.MainView;
import net.nokok.twitduke.view.TweetCell;
import net.nokok.twitduke.view.ui.TWLabel;
import net.nokok.twitduke.view.ui.color.DefaultColor;
import net.nokok.twitduke.wrapper.Twitter4jAsyncWrapper;
import twitter4j.Status;

public class MainViewController {

    private Twitter4jAsyncWrapper wrapper;
    private TweetCellFactory      tweetCellFactory;
    private MainView              mainView;
    private SettingViewController settingViewController;

    private final HashMap<Status, TweetCell> cellHashMap  = new HashMap<>();
    private       long                       selectedUser = 0;

    /**
     * MainViewControllerの初期化に必要な処理を開始します
     *
     * @param wrapper Twitter4jのラッパクラス
     * @see net.nokok.twitduke.wrapper.Twitter4jAsyncWrapper
     */
    public void start(Twitter4jAsyncWrapper wrapper, SettingViewController settingViewController) {
        mainView = new MainView();
        this.wrapper = wrapper;
        this.settingViewController = settingViewController;
        tweetCellFactory = new TweetCellFactory(wrapper, this);
        mainView.setVisible(true);
        bindActionListener();
        setNotification("UserStreamに接続中です");
    }

    /**
     * UserStreamに接続された時に呼び出されます
     *
     * @see net.nokok.twitduke.main.Main#boot()
     * @see net.nokok.twitduke.main.Main#twitterAPIWrapperInitialize()
     */
    public void userStreamConnected() {
        setNotification("UserStreamに接続しました");
        launchTitleAnimation();
    }

    /**
     * UserStreamとの接続が切れた時、切断された時に呼び出されます
     *
     * @see net.nokok.twitduke.main.Main#boot()
     * @see net.nokok.twitduke.main.Main#twitterAPIWrapperInitialize()
     */
    public void userStreamDisconnected() {
        setNotification("UserStreamとの接続が切れています");
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
     * 通知は設定した秒数後(規定値:3秒)に消えるようアニメーション処理が実行されます
     * また、通知が消える前に新たな通知が発生した場合、今表示中の通知の処理が終わり次第、次の通知が表示される。
     *
     * @param text 表示する通知のテキスト
     * @see net.nokok.twitduke.model.thread.NotificationBarAnimationInvoker
     */
    public void setNotification(String text) {
        new NotificationBarAnimationInvoker(this, text).start();
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
    public void setReply(String screenName) {
        mainView.setTweetTextField('@' + screenName + ' ');
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
    public void insertTweetCell(Status status) {
        TweetCell cell = tweetCellFactory.createTweetCell(status);
        if (status.getUser().getId() == selectedUser) {
            cell.changeColor(DefaultColor.TweetCell.SELECTED_BACKGROUND);
        }
        if (!mainView.isScrollbarTop()) {
            mainView.shiftScrollBar((int) cell.getPreferredSize().getHeight());
        }
        mainView.insertTweetCell(cell);
        if (cell.isMention()) {
            String tweetText = status.getText();
            if ((tweetText.contains("QT") || tweetText.contains("RT")) && Config.IS_MUTE_UNOFFICIAL_RT) {
                return;
            }
            mainView.insertMentionTweetCell(tweetCellFactory.createTweetCell(status));
        }
        cellHashMap.put(status, cell);
    }

    /**
     * MainViewのツールバーにあるボタンにアクションリスナーを設定します
     */
    private void bindActionListener() {
        mainView.setSendButtonAction(e -> sendTweet());
        mainView.setSendButtonMouseAdapter(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (MouseUtil.isRightButtonClicked(e)) {
                    wrapper.sendJavaJava();
                }
            }
        });
        mainView.setMentionButtonAction(e -> mainView.swapTweetList());
        mainView.setTextFieldAction(e -> sendTweet());
        mainView.setSettingButtonAction(e -> settingViewController.show());
    }

    /**
     * ツイート送信時のView側の処理を行います
     * ラッパクラスに入力されたツイートを渡した後、テキストフィールドのテキストをクリアします
     */
    private void sendTweet() {
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
     *
     */
    public void highlightUserCell(long userId) {
        selectedUser = userId;
        cellHashMap.forEach((status, cell) -> {
            if (status.getUser().getId() == userId) {
                cell.changeColor(DefaultColor.TweetCell.SELECTED_BACKGROUND);
                cell.selectCell();
            } else {
                cell.unSelectCell();
                if (cell.isMention()) {
                    cell.changeColor(DefaultColor.TweetCell.MENTION_BACKGROUND);
                } else {
                    cell.changeColor(DefaultColor.TweetCell.DEFAULT_BACKGROUND);
                }
            }
        });
    }
}
