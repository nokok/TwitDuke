package net.nokok.twitduke.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {

    private final JTextField textField = new JTextField();
    private final JPanel tweetListPanel = new JPanel();
    private final JScrollPane parentTweetListPanel = new JScrollPane(tweetListPanel,
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    private final CommonButton settingButton = new CommonButton("Settings");
    private final CommonButton userSwitcher = new CommonButton("User...");
    private final CommonButton sendTweetButton = new CommonButton("Tweet");

    public MainWindow() {
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.textField.setColumns(5);
        this.textField.setPreferredSize(new Dimension(530, 30));
        this.textField.setOpaque(true);
        this.textField.setBorder(null);
        this.textField.setFont(new Font("ヒラギノ角ゴ", Font.PLAIN, 15));

        this.setTitle("TwitDuke");
        this.setMinimumSize(new Dimension(530, 600));
        this.setLayout(new BorderLayout());

        JPanel rootNorthPanel = new JPanel(new BorderLayout());
        rootNorthPanel.setMinimumSize(new Dimension(530, 30));
        rootNorthPanel.add(textField, BorderLayout.NORTH);

        JPanel toolBarPanel = new JPanel(new GridLayout());
        toolBarPanel.setPreferredSize(new Dimension(530, 30));
        toolBarPanel.add(settingButton, BorderLayout.WEST);
        toolBarPanel.add(new CommonButton(""));  //バランスが悪いため
        toolBarPanel.add(userSwitcher);
        toolBarPanel.setBackground(sendTweetButton.getBackground());
        toolBarPanel.add(sendTweetButton);
        rootNorthPanel.add(toolBarPanel, BorderLayout.SOUTH);
        this.add(rootNorthPanel, BorderLayout.NORTH);

        JPanel rootCenterPanel = new JPanel();
        rootCenterPanel.add(parentTweetListPanel);
        parentTweetListPanel.setBackground(new Color(30, 30, 30));
        tweetListPanel.setLayout(new BoxLayout(tweetListPanel, BoxLayout.Y_AXIS));
        tweetListPanel.setBackground(new Color(30, 30, 30));
        tweetListPanel.setMinimumSize(new Dimension(530, 200));
        this.add(parentTweetListPanel, BorderLayout.CENTER);

        //TODO:ツイートセルが少ない時にセルのサイズがおかしいバグを直す
        //セルが少ない時、Boxレイアウトによってセルサイズが自動で変更されるのを防ぐため適当にセルを挿入する
        for (int i = 0; i < 10; i++) {
            this.insertTweetCell(new TweetCell());

        }
    }

    public void insertTweetCell(TweetCell tweetCell) {
        tweetListPanel.add(Box.createRigidArea(new Dimension(530, 5)));
        tweetListPanel.add(tweetCell);
    }

    public void bindActionListenerSettingButton(ActionListener actionListener) {
        settingButton.addActionListener(actionListener);
    }

    public void bindActionListenerSendTweetButton(ActionListener actionListener) {
        sendTweetButton.addActionListener(actionListener);
    }

    public void bindActionListenerUserSwitcher(ActionListener actionListener) {
        userSwitcher.addActionListener(actionListener);
    }
}
