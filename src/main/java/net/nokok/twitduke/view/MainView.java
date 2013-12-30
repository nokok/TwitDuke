package net.nokok.twitduke.view;

import net.nokok.twitduke.view.ui.TWButton;
import net.nokok.twitduke.view.ui.TWPanel;
import net.nokok.twitduke.view.ui.color.DefaultColor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;

public class MainView extends JFrame {

    private final Dimension MINIMUM_SIZE  = new Dimension(530, 600);
    private final TWButton  settingButton = new TWButton("設定");
    private final TWButton  mentionButton = new TWButton("＠");
    private final TWButton  userSwitcher  = new TWButton("ユーザー...");
    private final TWButton  sendButton    = new TWButton("ツイート");

    private JTextField tweetTextField = new JTextField();

    private final Dimension TOP_PANEL_DEFAULT_SIZE = new Dimension(530, 60);
    private final Dimension TEXTFIELD_SIZE         = new Dimension(530, 30);

    private final TWPanel tweetListPanel = new TWPanel();

    public MainView() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("TwitDuke");
        this.setMinimumSize(MINIMUM_SIZE);
        this.setLayout(new BorderLayout());
        this.setBackground(DefaultColor.DEFAULT_BACKGROUND);

        TWPanel toolBar = new TWPanel(new GridLayout());
        toolBar.setBackground(DefaultColor.TWButton.DEFAULT_BACKGROUND);
        toolBar.add(settingButton);
        toolBar.add(mentionButton);
        toolBar.add(userSwitcher);
        toolBar.add(sendButton);

        TWPanel topPanel = new TWPanel(new BorderLayout());
        topPanel.setPreferredSize(TOP_PANEL_DEFAULT_SIZE);
        tweetTextField.setPreferredSize(TEXTFIELD_SIZE);
        tweetTextField.setOpaque(true);
        tweetTextField.setBorder(null);
        tweetTextField.setBackground(DefaultColor.TweetCell.DEFAULT_BACKGROUND);
        tweetTextField.setForeground(DefaultColor.TWButton.DEFAULT_FOREGROUND);
        tweetTextField.setCaretColor(Color.WHITE);

        topPanel.add(tweetTextField, BorderLayout.CENTER);
        topPanel.add(toolBar, BorderLayout.SOUTH);

        JScrollPane scrollPane = new JScrollPane(tweetListPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBackground(DefaultColor.DEFAULT_BACKGROUND);
        scrollPane.getVerticalScrollBar().setUnitIncrement(35);
        scrollPane.setOpaque(true);
        scrollPane.setBorder(null);
        tweetListPanel.setLayout(new BoxLayout(tweetListPanel, BoxLayout.Y_AXIS));

        this.add(topPanel, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    public void insertTweetCell(TweetCell cell) {
        tweetListPanel.add(Box.createRigidArea(new Dimension(cell.getWidth(), 1)), 0);
        tweetListPanel.add(cell, 0);
        tweetListPanel.validate();
    }

    public void setTextFieldAction(MouseAdapter adapter) {
        this.tweetTextField.addMouseListener(adapter);
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
}


