package net.nokok.twitduke.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyListener;
import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import net.nokok.twitduke.main.Config;
import net.nokok.twitduke.view.ui.TWLabel;
import net.nokok.twitduke.view.ui.TWPanel;
import net.nokok.twitduke.view.ui.TWScrollPane;
import net.nokok.twitduke.view.ui.TimelineLayout;
import net.nokok.twitduke.view.ui.color.DefaultColor;

public class MainView extends JFrame {

    private boolean isMentionVisible;

    private final JTextArea tweetTextArea = new JTextArea();

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
        setSize(Config.ComponentSize.MAINVIEW_DEFAULT);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        TWPanel topPanel = new TWPanel(new BorderLayout());
        tweetTextArea.setPreferredSize(Config.ComponentSize.TEXTFIELD);
        tweetTextArea.setOpaque(true);
        tweetTextArea.setBackground(DefaultColor.TweetCell.DEFAULT_BACKGROUND);
        tweetTextArea.setForeground(DefaultColor.TWButton.DEFAULT_FOREGROUND);
        tweetTextArea.setCaretColor(Color.WHITE);
        tweetTextArea.setAutoscrolls(true);
        tweetTextArea.setMargin(new Insets(3, 3, 3, 3));

        topPanel.add(tweetTextArea, BorderLayout.CENTER);

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
        notificationBar.setPreferredSize(Config.ComponentSize.STATUS_BAR);
        notificationLabel.setFont(Config.FontConfig.NOTIFICATION);
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
    public void setTweetText(String text) {
        tweetTextArea.setText(text);
    }

    /**
     * テキストエリアにキーリスナをセットします
     *
     * @param listener セットするキーリスナ
     */
    public void setTextAreaAction(KeyListener listener) {
        tweetTextArea.addKeyListener(listener);
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
        return tweetTextArea.getText();
    }

    /**
     * テキストフィールドに入力されたテキストをすべて消去します
     */
    public void clearTextField() {
        tweetTextArea.setText("");
    }

    /**
     * メインのタイムラインのパネルとメンションのパネルを入れ替えます
     */
    public void swapTweetList() {
        isMentionVisible = !isMentionVisible;
        if (isMentionVisible) {
            layout.first(rootScrollPanel);
            return;
        }
        layout.last(rootScrollPanel);
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
