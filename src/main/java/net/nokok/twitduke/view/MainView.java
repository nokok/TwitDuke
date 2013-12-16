package net.nokok.twitduke.view;

import net.nokok.twitduke.util.ui.CommonButton;
import net.nokok.twitduke.util.ui.UIColor;
import net.nokok.twitduke.util.ui.UISize;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainView extends JFrame {

    private final JTextField   textField       = new JTextField();
    private final JPanel       tweetListPanel  = new JPanel();
    private final CommonButton settingButton   = new CommonButton("Settings");
    private final CommonButton userSwitcher    = new CommonButton("User...");
    private final CommonButton sendTweetButton = new CommonButton("Tweet");

    public MainView() {
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.textField.setColumns(5);
        this.textField.setPreferredSize(UISize.MainWindow.TEXTFIELD_SIZE);
        this.textField.setOpaque(true);
        this.textField.setBorder(null);
        this.textField.setFont(new Font("", Font.PLAIN, 15));

        this.setTitle("TwitDuke");
        this.setMinimumSize(UISize.MainWindow.MINIMUM_SIZE);
        this.setLayout(new BorderLayout());

        JPanel rootNorthPanel = new JPanel(new BorderLayout());
        rootNorthPanel.setMinimumSize(new Dimension(530, 30));
        rootNorthPanel.add(textField, BorderLayout.NORTH);

        JPanel toolBarPanel = new JPanel(new GridLayout());
        toolBarPanel.setPreferredSize(rootNorthPanel.getPreferredSize());
        toolBarPanel.add(settingButton, BorderLayout.WEST);
        toolBarPanel.add(new CommonButton(""));
        toolBarPanel.add(userSwitcher);
        toolBarPanel.add(sendTweetButton);
        toolBarPanel.setBackground(UIColor.Toolbar.DEFAULT_BACKGROUND);
        rootNorthPanel.add(toolBarPanel, BorderLayout.SOUTH);
        this.add(rootNorthPanel, BorderLayout.NORTH);

        JPanel rootCenterPanel = new JPanel();
        JScrollPane parentTweetListPanel = new JScrollPane(tweetListPanel,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        rootCenterPanel.add(parentTweetListPanel);
        parentTweetListPanel.getVerticalScrollBar().setUnitIncrement(30);
        parentTweetListPanel.setBackground(UIColor.TweetPanel.DEFAULT_BACKGROUND);
        tweetListPanel.setLayout(new BoxLayout(tweetListPanel, BoxLayout.Y_AXIS));
        tweetListPanel.setBackground(UIColor.TweetPanel.DEFAULT_BACKGROUND);
        tweetListPanel.setMinimumSize(new Dimension(530, 200));
        this.add(parentTweetListPanel, BorderLayout.CENTER);

    }

    public void insertTweetCell(TweetCell tweetCell) {
        tweetListPanel.add(Box.createRigidArea(new Dimension(tweetCell.getWidth(), 1)), 0);
        tweetListPanel.add(tweetCell, 0);
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

    public void bindActionListenerTextField(ActionListener actionListener) {
        textField.addActionListener(actionListener);
    }

    public String getTextFieldText() {
        return this.textField.getText();
    }

    public void clearTextField() {
        this.textField.setText("");
    }
}
