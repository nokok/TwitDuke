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
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import net.nokok.twitduke.view.ui.TWButton;
import net.nokok.twitduke.view.ui.TWLabel;
import net.nokok.twitduke.view.ui.TWPanel;
import net.nokok.twitduke.view.ui.TWScrollPane;
import net.nokok.twitduke.view.ui.color.DefaultColor;

public class MainView extends JFrame {

    private boolean isMentionVisible;

    private final TWButton settingButton = new TWButton("設定");
    private final TWButton mentionButton = new TWButton("＠");
    private final TWButton userSwitcher  = new TWButton("ユーザー...");
    private final TWButton sendButton    = new TWButton("ツイート");

    private JTextField tweetTextField = new JTextField();

    private final Dimension DEFAULT_SIZE        = new Dimension(530, 600);
    private final Dimension TEXTFIELD_SIZE      = new Dimension(530, 30);
    private final Dimension STATUS_BAR_SIZE     = new Dimension(530, 25);
    private final Dimension TWEETCELL_SEPARATOR = new Dimension(1, 1); //幅は1px固定 (width,height)
    private final int       SCROLLBAR_WIDTH     = 15;

    private final CardLayout layout = new CardLayout();

    private final TWPanel rootScrollPanel = new TWPanel(layout);
    private final TWPanel tweetListPanel  = new TWPanel();
    private final TWPanel replyListPanel  = new TWPanel();

    private int totalTweetListHeight = 0;
    private int totalReplyListHeight = 0;

    private final TWLabel statusLabel = new TWLabel();

    public MainView() {
        this.initializeComponent();
    }

    private void initializeComponent() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
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
        tweetListPanel.setLayout(new BoxLayout(tweetListPanel, BoxLayout.Y_AXIS));
        replyListPanel.setLayout(new BoxLayout(replyListPanel, BoxLayout.Y_AXIS));

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
        this.setActionListener();
    }

    private void setActionListener() {
        rootScrollPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                //スクロールバーと被るのでスクロールバーの幅15pxを引く
                resizeComponents(e.getComponent().getWidth() - SCROLLBAR_WIDTH);
            }
        });
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
            totalReplyListHeight += cell.getHeight();
        }
        insertTweetCellToPanel(tweetListPanel, cell);
        totalTweetListHeight += cell.getHeight();
        resizeComponents(this.getWidth() - SCROLLBAR_WIDTH);
    }

    private void resizeComponents(int currentWidth) {
        tweetListPanel.setPreferredSize(new Dimension(currentWidth, totalTweetListHeight));
        replyListPanel.setPreferredSize(new Dimension(currentWidth, totalReplyListHeight));
        rootScrollPanel.setPreferredSize(tweetListPanel.getPreferredSize());
    }

    /**
     * 指定されたパネルにセルを挿入します
     *
     * @param panel セルを挿入するパネル
     * @param cell  挿入するセル
     */
    private void insertTweetCellToPanel(TWPanel panel, TweetCell cell) {
        panel.add(cell, 0);
        panel.validate();
    }

    public void setTweetTextField(String text) {
        this.tweetTextField.setText(text);
    }

    public void setTextFieldAction(ActionListener listener) {
        this.tweetTextField.addActionListener(listener);
    }

    public void setSettingButtonAction(MouseAdapter adapter) {
        this.settingButton.addMouseListener(adapter);
    }

    public void setMentionButtonAction(MouseAdapter adapter) {
        this.mentionButton.addMouseListener(adapter);
    }

    public void setUserSwitcherAction(MouseAdapter adapter) {
        this.userSwitcher.addMouseListener(adapter);
    }

    public void setSendButtonAction(MouseAdapter adapter) {
        this.sendButton.addMouseListener(adapter);
    }

    public void setStatus(String text) {
        this.statusLabel.setText(text);
    }

    public TWLabel getStatusLabel() {
        return statusLabel;
    }

    public String getTweetText() {
        return this.tweetTextField.getText();
    }

    public void clearTextField() {
        tweetTextField.setText("");
    }

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
