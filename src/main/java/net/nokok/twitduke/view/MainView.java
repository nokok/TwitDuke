package net.nokok.twitduke.view;

import net.nokok.twitduke.view.ui.TWButton;
import net.nokok.twitduke.view.ui.TWPanel;
import net.nokok.twitduke.view.ui.TWScrollPane;
import net.nokok.twitduke.view.ui.color.DefaultColor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

public class MainView extends JFrame {

    private boolean isMentionVisible;

    private final Dimension MINIMUM_SIZE  = new Dimension(100, 0);
    private final Dimension DEFAULT_SIZE  = new Dimension(530, 600);
    private final TWButton  settingButton = new TWButton("設定");
    private final TWButton  mentionButton = new TWButton("＠");
    private final TWButton  userSwitcher  = new TWButton("ユーザー...");
    private final TWButton  sendButton    = new TWButton("ツイート");

    private JTextField tweetTextField = new JTextField();

    private final Dimension TEXTFIELD_SIZE = new Dimension(530, 30);

    private final CardLayout layout = new CardLayout();

    private final TWPanel rootScrollPanel = new TWPanel(layout);
    private final TWPanel tweetListPanel  = new TWPanel();
    private final TWPanel replyListPanel  = new TWPanel();


    public MainView() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("TwitDuke");
        this.setMinimumSize(MINIMUM_SIZE);
        this.setSize(DEFAULT_SIZE);
        this.setLayout(new BorderLayout());
        this.setBackground(DefaultColor.DEFAULT_BACKGROUND);

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

        this.add(topPanel, BorderLayout.NORTH);
        this.add(rootScrollPanel, BorderLayout.CENTER);
    }

    public void insertTweetCell(TweetCell cell) {
        if (cell.isMention()) {
            insertTweetCellToPanel(replyListPanel, TweetCell.copy(cell));
        }
        insertTweetCellToPanel(tweetListPanel, cell);
    }

    private void insertTweetCellToPanel(TWPanel panel, TweetCell cell) {
        panel.add(Box.createRigidArea(new Dimension(1, 1)), 0);
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

    public String getTweetText() {
        return this.tweetTextField.getText();
    }

    public void clearTextField() {
        tweetTextField.setText("");
    }

    public void swapTweetList() {
        if (isMentionVisible) {
            layout.first(rootScrollPanel);
            this.mentionButton.setText("＠");
        } else {
            layout.last(rootScrollPanel);
            this.mentionButton.setText("戻る");
            replyListPanel.validate();
        }
        isMentionVisible = !isMentionVisible;
    }
}
