package net.nokok.twitduke.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.SwingUtilities;
import net.nokok.twitduke.controller.tweetcellstatus.TweetCellUpdater;
import net.nokok.twitduke.controller.tweetcellstatus.type.CellStatus;
import net.nokok.twitduke.main.Config;
import net.nokok.twitduke.model.factory.TweetCellFactory;
import net.nokok.twitduke.model.thread.NotificationBarAnimationInvoker;
import net.nokok.twitduke.model.thread.TitleAnimationInvoker;
import net.nokok.twitduke.util.KeyUtil;
import net.nokok.twitduke.view.MainView;
import net.nokok.twitduke.view.TweetCell;
import net.nokok.twitduke.view.ui.TWLabel;
import net.nokok.twitduke.wrapper.Twitter4jAsyncWrapper;
import twitter4j.Status;

public class MainViewController {

    private Twitter4jAsyncWrapper wrapper;
    private TweetCellFactory      tweetCellFactory;
    private MainView              mainView;

    private final HashMap<Long, CellStatus> cellHashMap  = new HashMap<>();
    private       long                      selectedUser = 0;

    /**
     * MainViewControllerの初期化に必要な処理を開始します
     *
     * @param wrapper Twitter4jのラッパクラス
     * @see net.nokok.twitduke.wrapper.Twitter4jAsyncWrapper
     */
    public void start(Twitter4jAsyncWrapper wrapper) {
        mainView = new MainView();
        this.wrapper = wrapper;
        tweetCellFactory = new TweetCellFactory(wrapper, this);
        mainView.setVisible(true);
        bindActionListener();
        setNotification("UserStreamに接続中です");
    }

    /**
     * UserStream接続成功時のタイトルバーのアニメーション処理を実行します
     *
     * @see net.nokok.twitduke.model.thread.TitleAnimationInvoker
     */
    public void launchTitleAnimation() {
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
        mainView.setTweetText('@' + screenName + ' ');
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
    public void insertTweetCell(final Status status) {
        final TweetCell cell = tweetCellFactory.createTweetCell(status);
        if (status.getUser().getId() == selectedUser) {
            cell.setSelectState(true);
        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                mainView.insertTweetCell(cell);
                if (cell.isMention() && !isUnofficialRT(status.getText())) {
                    mainView.insertMentionTweetCell(tweetCellFactory.createTweetCell(status));
                }
                if (!mainView.isScrollbarTop()) {
                    mainView.shiftScrollBar(cell.getHeight() + 1);
                }
            }
        });
        cellHashMap.put(status.getId(), new CellStatus(cell, status));
    }

    private boolean isUnofficialRT(String tweetText) {
        return (tweetText.contains("QT") || tweetText.contains("RT")) && Config.Flags.isMuteUnOfficialRT;
    }

    /**
     * MainViewのツールバーにあるボタンにアクションリスナーを設定します
     */
    private void bindActionListener() {
        mainView.setTextAreaAction(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (KeyUtil.isEnterAndShiftKey(e)) {
                    sendTweet();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                KeyUtil.reset();
            }
        });

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
     * 指定されたユーザーIDのセルをハイライトします
     */
    private void highlightUserCell(long userId) {
        selectedUser = userId;
        for (Map.Entry<Long, CellStatus> cellEntry : cellHashMap.entrySet()) {
            long cellUserId = cellEntry.getValue().status.getUser().getId();
            TweetCell cell = cellEntry.getValue().tweetCell;
            if (cellUserId == userId) {
                cell.setSelectState(true);
            } else {
                cell.setSelectState(false);
            }
        }
    }

    /**
     * 渡されたTweetCellStatusBaseによってセルの状態を変更します
     *
     * @param update
     */
    public void updateTweetCellStatus(TweetCellUpdater update) {
        long id = update.id;
        switch (update.category) {
            case FAVORITED:
                cellHashMap.get(id).tweetCell.setFavoriteState(true);
                break;
            case UNFAVORITED:
                cellHashMap.get(id).tweetCell.setFavoriteState(false);
                break;
            case RETWEETED:
                cellHashMap.get(id).tweetCell.setRetweetState(true);
                break;
            case DELETED:
                cellHashMap.get(id).tweetCell.setDeleted();
                break;
            case SELECTED:
                highlightUserCell(id);
                break;
            default:
                break;
        }
    }
}
