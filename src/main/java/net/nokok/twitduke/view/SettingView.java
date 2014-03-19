package net.nokok.twitduke.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import javax.swing.JTextArea;
import net.nokok.twitduke.view.ui.TWButton;
import net.nokok.twitduke.view.ui.TWDialog;
import net.nokok.twitduke.view.ui.TWLabel;
import net.nokok.twitduke.view.ui.TWPanel;
import net.nokok.twitduke.view.ui.TWSlimButton;
import net.nokok.twitduke.view.ui.TWSmallButton;
import net.nokok.twitduke.view.ui.color.DefaultColor;

class SettingView extends TWDialog {

    private CardLayout cardLayout;
    private TWPanel    rootCenterPanel;

    private TWButton accountBarButton;
    private TWButton uiBarButton;
    private TWButton tweetBarButton;
    private TWButton aboutBarButton;

    public SettingView() {
        initializeComponents();
        setLocationRelativeTo(getParent());
        pack();
    }

    private void initializeComponents() {
        setLayout(new BorderLayout());
        setTitle("設定");

        //---設定画面上部のボタン---
        TWPanel selectBarPanel = new TWPanel();
        accountBarButton = new TWSlimButton("アカウント");
        uiBarButton = new TWSlimButton("UI");
        tweetBarButton = new TWSlimButton("ツイート");
        aboutBarButton = new TWSlimButton("TwitDuke");
        selectBarPanel.add(accountBarButton);
        selectBarPanel.add(uiBarButton);
        selectBarPanel.add(tweetBarButton);
        selectBarPanel.add(aboutBarButton);

        //---設定画面中央部---
        cardLayout = new CardLayout();
        rootCenterPanel = new TWPanel(cardLayout);

        //---アカウント設定---
        GridBagLayout layout = new GridBagLayout();
        TWPanel accountRootPanel = new TWPanel(layout);
        GridBagConstraints constraint = new GridBagConstraints();
        constraint.gridx = 0;
        constraint.gridy = 0;
        constraint.insets = new Insets(2, 2, 2, 2);
        TWLabel accountTitleLabel = new TWLabel("アカウント");
        layout.setConstraints(accountTitleLabel, constraint);
        accountRootPanel.add(accountTitleLabel);
        constraint.gridx++;
        TWSmallButton addAccountButton = new TWSmallButton("追加");
        layout.setConstraints(addAccountButton, constraint);
        accountRootPanel.add(addAccountButton);
        constraint.gridx++;
        TWSmallButton removeAccountButton = new TWSmallButton("削除");
        layout.setConstraints(removeAccountButton, constraint);
        accountRootPanel.add(removeAccountButton);
        TWLabel notificationTitleLabel = new TWLabel("通知");
        constraint.gridx = 0;
        constraint.gridy = 1;
        layout.setConstraints(notificationTitleLabel, constraint);
        accountRootPanel.add(notificationTitleLabel);
        TWLabel enableUserStreamNotification = new TWLabel("UserStreamの通知を有効化する");
        constraint.gridx++;
        layout.setConstraints(enableUserStreamNotification, constraint);
        accountRootPanel.add(enableUserStreamNotification);

        //---UI設定---
        TWPanel uiRootPanel = new TWPanel();
        uiRootPanel.add(new TWLabel("UI"));

        //---ツイート設定---
        TWPanel tweetRootPanel = new TWPanel();
        tweetRootPanel.add(new TWLabel("Tweet"));

        //---TwitDukeについての画面---
        TWPanel aboutRootPanel = new TWPanel();
        JTextArea textArea = new JTextArea("TwitDukeはMITライセンスで配布されています。\n開発者は一切の責任や義務を負いません");
        textArea.setOpaque(true);
        textArea.setBackground(DefaultColor.DEFAULT_BACKGROUND);
        textArea.setForeground(DefaultColor.TWButton.DEFAULT_FOREGROUND);
        aboutRootPanel.add(textArea);
        //---作成したパネルをメインに追加---
        rootCenterPanel.add(accountRootPanel, SettingPanelType.ACCOUNT.toString());
        rootCenterPanel.add(uiRootPanel, SettingPanelType.UI.toString());
        rootCenterPanel.add(tweetRootPanel, SettingPanelType.TWEET.toString());
        rootCenterPanel.add(aboutRootPanel, SettingPanelType.ABOUT.toString());
        add(selectBarPanel, BorderLayout.NORTH);
        add(rootCenterPanel, BorderLayout.CENTER);
    }

    public void setAccountButtonAction(ActionListener listener) {
        accountBarButton.addActionListener(listener);
    }

    public void selectAccount() {
        cardLayout.show(rootCenterPanel, SettingPanelType.ACCOUNT.toString());
        accountBarButton.setBackground(DefaultColor.DEFAULT_BACKGROUND);
        uiBarButton.setBackground(DefaultColor.TWButton.DEFAULT_BACKGROUND);
        tweetBarButton.setBackground(DefaultColor.TWButton.DEFAULT_BACKGROUND);
        aboutBarButton.setBackground(DefaultColor.TWButton.DEFAULT_BACKGROUND);
    }

    public void setUIButtonAction(ActionListener listener) {
        uiBarButton.addActionListener(listener);
    }

    public void selectUI() {
        cardLayout.show(rootCenterPanel, SettingPanelType.UI.toString());
        accountBarButton.setBackground(DefaultColor.TWButton.DEFAULT_BACKGROUND);
        uiBarButton.setBackground(DefaultColor.DEFAULT_BACKGROUND);
        tweetBarButton.setBackground(DefaultColor.TWButton.DEFAULT_BACKGROUND);
        aboutBarButton.setBackground(DefaultColor.TWButton.DEFAULT_BACKGROUND);
    }

    public void setTweetBarButtonAction(ActionListener listener) {
        tweetBarButton.addActionListener(listener);
    }

    public void selectTweet() {
        cardLayout.show(rootCenterPanel, SettingPanelType.TWEET.toString());
        accountBarButton.setBackground(DefaultColor.TWButton.DEFAULT_BACKGROUND);
        uiBarButton.setBackground(DefaultColor.TWButton.DEFAULT_BACKGROUND);
        tweetBarButton.setBackground(DefaultColor.DEFAULT_BACKGROUND);
        aboutBarButton.setBackground(DefaultColor.TWButton.DEFAULT_BACKGROUND);
    }

    public void setAboutButtonAction(ActionListener listener) {
        aboutBarButton.addActionListener(listener);
    }

    public void selectAbout() {
        cardLayout.show(rootCenterPanel, SettingPanelType.ABOUT.toString());
        accountBarButton.setBackground(DefaultColor.TWButton.DEFAULT_BACKGROUND);
        uiBarButton.setBackground(DefaultColor.TWButton.DEFAULT_BACKGROUND);
        tweetBarButton.setBackground(DefaultColor.TWButton.DEFAULT_BACKGROUND);
        aboutBarButton.setBackground(DefaultColor.DEFAULT_BACKGROUND);
    }
}

