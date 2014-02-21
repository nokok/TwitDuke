package net.nokok.twitduke.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import net.nokok.twitduke.main.Config;
import net.nokok.twitduke.view.ui.TWButton;
import net.nokok.twitduke.view.ui.TWLabel;
import net.nokok.twitduke.view.ui.TWPanel;
import net.nokok.twitduke.view.ui.TWScrollPane;
import net.nokok.twitduke.view.ui.TimelineLayout;
import net.nokok.twitduke.view.ui.color.DefaultColor;

public class MainView extends JFrame {

    private boolean isMentionVisible;

    private final TWButton settingButton = new TWButton("設定");
    private final TWButton mentionButton = new TWButton("＠");
    private final TWButton userSwitcher  = new TWButton("ユーザー...");
    private final TWButton sendButton    = new TWButton("ツイート");

    private final JTextField tweetTextField = new JTextField();

    private final LayoutManager tweetListLayout = new TimelineLayout();
    private final LayoutManager replyListLayout = new TimelineLayout();

    private final CardLayout layout = new CardLayout();

    private final TWPanel rootScrollPanel = new TWPanel(layout);
    private final TWPanel tweetListPanel  = new TWPanel();
    private final TWPanel replyListPanel  = new TWPanel();
    private JScrollBar verticalScrollbar;

    private final TWLabel notificationLabel = new TWLabel();

    public MainView() {
        initializeComponent();
    }

    /**
     * Viewのコンポーネントを配置します
     */
    private void initializeComponent() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("UserStreamに接続中です");
        setSize(Config.ComponentSize.MAINVIEW_DEFAULT_SIZE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        TWPanel topPanel = new TWPanel(new BorderLayout());
        tweetTextField.setPreferredSize(Config.ComponentSize.TEXTFIELD_SIZE);
        tweetTextField.setOpaque(true);
        tweetTextField.setBorder(null);
        tweetTextField.setBackground(DefaultColor.TweetCell.DEFAULT_BACKGROUND);
        tweetTextField.setForeground(DefaultColor.TWButton.DEFAULT_FOREGROUND);
        tweetTextField.setCaretColor(Color.WHITE);
        topPanel.add(tweetTextField, BorderLayout.CENTER);

        TWPanel toolBar = new TWPanel(new GridLayout());
        toolBar.setBackground(DefaultColor.TWButton.DEFAULT_BACKGROUND);
        toolBar.add(settingButton);
        toolBar.add(mentionButton);
        toolBar.add(userSwitcher);
        toolBar.add(sendButton);
        topPanel.add(toolBar, BorderLayout.SOUTH);

        JScrollPane scrollPane = new TWScrollPane(tweetListPanel);
        final JScrollPane replyScrollPane = new TWScrollPane(replyListPanel);
        tweetListPanel.setLayout(tweetListLayout);
        replyListPanel.setLayout(replyListLayout);
        verticalScrollbar = scrollPane.getVerticalScrollBar();

        rootScrollPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                tweetListPanel.setPreferredSize(tweetListLayout.minimumLayoutSize(tweetListPanel));
                replyListPanel.setPreferredSize(replyListLayout.minimumLayoutSize(replyScrollPane));
            }
        });

        rootScrollPanel.add(scrollPane, "DEFAULT");
        rootScrollPanel.add(replyScrollPane, "REPLY");

        TWPanel notificationBar = new TWPanel(new FlowLayout(FlowLayout.LEFT));
        notificationBar.setPreferredSize(Config.ComponentSize.STATUS_BAR_SIZE);
        notificationLabel.setFont(Config.FontConfig.NOTIFICATION_FONT);
        notificationBar.add(notificationLabel);

        add(topPanel, BorderLayout.NORTH);
        add(rootScrollPanel, BorderLayout.CENTER);
        add(notificationBar, BorderLayout.SOUTH);
    }

    /**
     * セルをメインのツイートパネルに挿入します。
     *
     * @param cell 挿入するセル
     */
    public void insertTweetCell(TweetCell cell) {
        insertTweetCellToPanel(tweetListPanel, cell);
    }

    /**
     * セルをリプライ用のツイートパネルに挿入します。
     *
     * @param cell 挿入するセル
     */
    public void insertMentionTweetCell(TweetCell cell) {
        insertTweetCellToPanel(replyListPanel, cell);
    }

    /**
     * 指定されたパネルにセルを挿入します
     *
     * @param panel セルを挿入するパネル
     * @param cell  挿入するセル
     */
    private void insertTweetCellToPanel(JComponent panel, TweetCell cell) {
        panel.add(Box.createRigidArea(new Dimension(1, Config.ComponentSize.CELL_SEPARATOR_HEIGHT)), 0);
        panel.add(cell, 0);
        panel.validate();
        panel.setPreferredSize(panel.getLayout().minimumLayoutSize(panel));
    }

    /**
     * テキストフィールドに文字列をセットします
     *
     * @param text セットする文字列
     */
    public void setTweetTextField(String text) {
        tweetTextField.setText(text);
    }

    /**
     * テキストフィールドにアクションリスナをセットします
     *
     * @param listener セットするアクションリスナ
     */
    public void setTextFieldAction(ActionListener listener) {
        tweetTextField.addActionListener(listener);
    }

    /**
     * 設定ボタンにアクションリスナをセットします
     *
     * @param listener セットするアクションリスナ
     */
    public void setSettingButtonAction(ActionListener listener) {
        settingButton.addActionListener(listener);
    }

    /**
     * メンションボタンにアクションリスナをセットします
     *
     * @param listener セットするアクションリスナ
     */
    public void setMentionButtonAction(ActionListener listener) {
        mentionButton.addActionListener(listener);
    }

    /**
     * ユーザースイッチャーにマウスアダプターをセットします
     *
     * @param adapter セットするマウスアダプター
     */
    public void setUserSwitcherAction(MouseAdapter adapter) {
        userSwitcher.addMouseListener(adapter);
    }

    /**
     * ツイート送信ボタンにアクションリスナをセットします
     *
     * @param listener セットするアクションリスナー
     */
    public void setSendButtonAction(ActionListener listener) {
        sendButton.addActionListener(listener);
    }

    /**
     * ツイート送信ボタンにマウスリスナをセットします
     */
    public void setSendButtonMouseAdapter(MouseListener listener) {
        sendButton.addMouseListener(listener);
    }

    /**
     * 通知を表示するラベルを移動します。
     *
     * @param x 横方向の座標
     * @param y 縦方向の座標
     */
    public void moveStatusLabel(int x, int y) {
        notificationLabel.setLocation(new Point(x, y));
    }

    /**
     * @return 通知を表示するラベルの横幅
     */
    public int getNotificationLabelWidth() {
        return (int) notificationLabel.getPreferredSize().getWidth();
    }

    /**
     * @return このメソッドが呼ばれた時点でテキストフィールドに入力されていたテキスト
     */
    public String getTweetText() {
        return tweetTextField.getText();
    }

    /**
     * テキストフィールドに入力されたテキストをすべて消去します
     */
    public void clearTextField() {
        tweetTextField.setText("");
    }

    /**
     * メインのタイムラインのパネルとメンションのパネルを入れ替えます
     */
    public void swapTweetList() {
        isMentionVisible = !isMentionVisible;
        if (isMentionVisible) {
            layout.first(rootScrollPanel);
            mentionButton.setText("＠");
            return;
        }
        layout.last(rootScrollPanel);
        mentionButton.setText("戻る");
        replyListPanel.validate();
    }

    /**
     * @return 通知を表示するラベル
     */
    public TWLabel getNotificationLabel() {
        return notificationLabel;
    }

    public boolean isScrollbarTop() {
        return verticalScrollbar.getValue() == 0;
    }

    public void shiftScrollBar(int value) {
        verticalScrollbar.setValue(verticalScrollbar.getValue() + value);
    }
}
