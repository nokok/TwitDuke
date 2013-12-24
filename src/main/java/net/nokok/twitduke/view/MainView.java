package net.nokok.twitduke.view;

import net.nokok.twitduke.view.ui.TWButton;
import net.nokok.twitduke.view.ui.TWPanel;
import net.nokok.twitduke.view.ui.color.DefaultColor;

import javax.swing.*;
import java.awt.*;

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
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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

        topPanel.add(tweetTextField, BorderLayout.NORTH);
        topPanel.add(toolBar, BorderLayout.SOUTH);

        JScrollPane scrollPane = new JScrollPane(tweetListPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBackground(DefaultColor.DEFAULT_BACKGROUND);
        scrollPane.getVerticalScrollBar().setUnitIncrement(35);
        tweetListPanel.setLayout(new BoxLayout(tweetListPanel, BoxLayout.Y_AXIS));

        this.add(topPanel, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    public void insertTweetCell(TweetCell cell) {
        tweetListPanel.add(Box.createRigidArea(new Dimension(cell.getWidth(), 1)), 0);
        tweetListPanel.add(cell, 0);
    }

    public JTextField getTweetTextField() {
        return tweetTextField;
    }

    public void clearTextField() {
        tweetTextField.setText("");
    }
}


