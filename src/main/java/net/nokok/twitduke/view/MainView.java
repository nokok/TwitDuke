package net.nokok.twitduke.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
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

    private JTextField tweetTextField = new JTextField();

    private final Dimension DEFAULT_SIZE    = new Dimension(530, 600);
    private final Dimension TEXTFIELD_SIZE  = new Dimension(530, 30);
    private final Dimension STATUS_BAR_SIZE = new Dimension(530, 25);

    private final TimelineLayout tweetListLayout = new TimelineLayout();
    private final TimelineLayout replyListLayout = new TimelineLayout();

    private final CardLayout layout = new CardLayout();

    private final TWPanel rootScrollPanel = new TWPanel(layout);
    private final TWPanel tweetListPanel  = new TWPanel();
    private final TWPanel replyListPanel  = new TWPanel();

    private final TWLabel statusLabel = new TWLabel();

    public MainView() {
        this.initializeComponent();
    }

    /**
     * Viewのコンポーネントを配置します
     */
    private void initializeComponent() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("UserStreamに接続中です");
        this.setSize(DEFAULT_SIZE);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);

        TWPanel topPanel = new TWPanel(new BorderLayout());
        tweetTextField.setPreferredSize(TEXTFIELD_SIZE);
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
        JScrollPane replyScrollPane = new TWScrollPane(replyListPanel);
        tweetListPanel.setLayout(tweetListLayout);
        replyListPanel.setLayout(replyListLayout);

        rootScrollPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                tweetListPanel.setPreferredSize(tweetListLayout.minimumLayoutSize(tweetListPanel));
                replyListPanel.setPreferredSize(replyListLayout.minimumLayoutSize(replyScrollPane));
            }
        });

        rootScrollPanel.add(scrollPane, "DEFAULT");
        rootScrollPanel.add(replyScrollPane, "REPLY");

        TWPanel statusBar = new TWPanel(new FlowLayout(FlowLayout.LEFT));
        Font statusFont = new Font("", Font.BOLD, 12);
        statusBar.setPreferredSize(STATUS_BAR_SIZE);
        statusLabel.setFont(statusFont);
        statusBar.add(statusLabel);

        this.add(topPanel, BorderLayout.NORTH);
        this.add(rootScrollPanel, BorderLayout.CENTER);
        this.add(statusBar, BorderLayout.SOUTH);
    }

    /**
     * セルをツイートパネルに挿入します。セルが現在選択中のユーザーへのメンションであった場合、
     * リプライリストパネルにセルのコピーを挿入します。
     *
     * @param cell 挿入するセル
     */
    public void insertTweetCell(TweetCell cell) {
        if (cell.isMention()) {
            insertTweetCellToPanel(replyListPanel, cell.clone());
        }
        insertTweetCellToPanel(tweetListPanel, cell);
    }

    /**
     * 指定されたパネルにセルを挿入します
     *
     * @param panel セルを挿入するパネル
     * @param cell  挿入するセル
     */
    private void insertTweetCellToPanel(JComponent panel, TweetCell cell) {
        panel.add(Box.createRigidArea(new Dimension(1, 1)), 0);
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
        this.tweetTextField.setText(text);
    }

    /**
     * テキストフィールドにアクションリスナをセットします
     *
     * @param listener セットするアクションリスナ
     */
    public void setTextFieldAction(ActionListener listener) {
        this.tweetTextField.addActionListener(listener);
    }

    /**
     * 設定ボタンにをセットします
     *
     * @param listener セットするアクションリスナ
     */
    public void setSettingButtonAction(ActionListener listener) {
        this.settingButton.addActionListener(listener);
    }

    /**
     * メンションボタンにアクションリスナをセットします
     *
     * @param listener セットするアクションリスナ
     */
    public void setMentionButtonAction(ActionListener listener) {
        this.mentionButton.addActionListener(listener);
    }

    /**
     * ユーザースイッチャーにマウスアダプターをセットします
     *
     * @param adapter セットするマウスアダプター
     */
    public void setUserSwitcherAction(MouseAdapter adapter) {
        this.userSwitcher.addMouseListener(adapter);
    }

    /**
     * ツイート送信ボタンにアクションリスナをセットします
     *
     * @param listener セットするアクションリスナー
     */
    public void setSendButtonAction(ActionListener listener) {
        this.sendButton.addActionListener(listener);
    }

    /**
     * @return 通知表示用のラベル
     */
    public TWLabel getStatusLabel() {
        return statusLabel;
    }

    /**
     * @return このメソッドが呼ばれた時点でテキストフィールドに入力されていたテキスト
     */
    public String getTweetText() {
        return this.tweetTextField.getText();
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
            this.mentionButton.setText("＠");
            return;
        }
        layout.last(rootScrollPanel);
        this.mentionButton.setText("戻る");
        replyListPanel.validate();
    }
}
