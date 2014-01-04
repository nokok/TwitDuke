package net.nokok.twitduke.view;

import net.nokok.twitduke.model.TweetCellFactory;
import net.nokok.twitduke.view.ui.TWButton;
import net.nokok.twitduke.view.ui.TWLabel;
import net.nokok.twitduke.view.ui.TWPanel;
import net.nokok.twitduke.view.ui.color.DefaultColor;
import net.nokok.twitduke.wrapper.Twitter4jAsyncWrapper;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.User;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.net.MalformedURLException;
import java.net.URL;

public class UserView extends JFrame {

    private final TWPanel  tweetPanel = new TWPanel();
    private final TWButton follow     = new TWButton("フォロー");
    private final TWButton sendReply  = new TWButton("リプライ");
    private final TWButton sendDM     = new TWButton("DM");
    private final TWButton block      = new TWButton("ブロック");
    private final TWButton reportSpam = new TWButton("スパム報告");
    private final TWButton openWeb    = new TWButton("TwitterWeb");

    private final Status status;

    public UserView(Status status) {
        this.status = status;

        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setTitle("読み込み中...");
        this.setSize(new Dimension(200, 100));
        this.setBackground(DefaultColor.DEFAULT_BACKGROUND);
        this.setVisible(true);

        this.setLayout(new BorderLayout());
        User user = status.getUser();

        TWLabel nameLabel = new TWLabel(user.getName());
        TWLabel screenNameLabel = new TWLabel("@" + user.getScreenName() + (user.isProtected() ? "[Lock]" : ""));
        nameLabel.setFont(new Font("", Font.BOLD, 15));
        screenNameLabel.setFont(new Font("", Font.BOLD, 15));
        TWLabel userIcon = new TWLabel();

        try {
            userIcon = new TWLabel(new ImageIcon(new URL(user.getBiggerProfileImageURL())));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        TWLabel tweetCount = new TWLabel("ツイート: " + String.valueOf(user.getStatusesCount()));
        TWLabel followingCount = new TWLabel("フォロー: " + String.valueOf(user.getFriendsCount()));
        TWLabel followerCount = new TWLabel("フォロワー: " + String.valueOf(user.getFollowersCount()));
        TWLabel favoritedCount = new TWLabel("お気に入り: " + String.valueOf(user.getFavouritesCount()));
        TWLabel location = new TWLabel("ロケーション: " + user.getLocation());
        JTextArea bio = new JTextArea(user.getDescription());
        bio.setEditable(false);
        bio.setForeground(DefaultColor.TweetCell.DEFAULT_FOREGROUND);
        bio.setBackground(DefaultColor.TweetCell.DEFAULT_BACKGROUND);
        bio.setLineWrap(true);
        TWPanel userNamePanel = new TWPanel(new FlowLayout());
        userNamePanel.add(nameLabel);
        userNamePanel.add(screenNameLabel);

        TWPanel userCountsPanel = new TWPanel(new BorderLayout());

        TWPanel userFFCounts = new TWPanel(new FlowLayout());
        userFFCounts.add(tweetCount);
        userFFCounts.add(followingCount);
        userFFCounts.add(followerCount);

        TWPanel userOtherInfo = new TWPanel(new FlowLayout());
        userOtherInfo.add(favoritedCount);
        userOtherInfo.add(location);
        userCountsPanel.add(userFFCounts, BorderLayout.NORTH);
        userCountsPanel.add(userOtherInfo, BorderLayout.SOUTH);
        TWPanel userIconPanel = new TWPanel(new FlowLayout());
        userIconPanel.add(userIcon);
        TWPanel userInfoPanel = new TWPanel(new BorderLayout());
        userInfoPanel.add(userIconPanel, BorderLayout.NORTH);
        userInfoPanel.add(userNamePanel, BorderLayout.CENTER);
        userInfoPanel.add(userCountsPanel, BorderLayout.SOUTH);

        TWPanel rootNorthPanel = new TWPanel(new BorderLayout());
        rootNorthPanel.add(userInfoPanel, BorderLayout.NORTH);
        TWPanel functionPanel = new TWPanel(new GridLayout(3, 2));
        if (user.isFollowRequestSent()) {
            follow.setText("保留中");
        }
        functionPanel.add(follow);
        functionPanel.add(sendReply);
        functionPanel.add(sendDM);
        functionPanel.add(block);
        functionPanel.add(reportSpam);
        functionPanel.add(openWeb);

        rootNorthPanel.add(functionPanel, BorderLayout.SOUTH);
        TWPanel userBioPanel = new TWPanel(new BorderLayout());
        userBioPanel.add(bio, BorderLayout.CENTER);
        bio.setMargin(new Insets(5, 10, 5, 10));
        rootNorthPanel.add(userBioPanel, BorderLayout.CENTER);

        tweetPanel.setLayout(new BoxLayout(tweetPanel, BoxLayout.Y_AXIS));

        this.add(rootNorthPanel, BorderLayout.NORTH);
        this.add(tweetPanel, BorderLayout.CENTER);

        Twitter4jAsyncWrapper wrapper = Twitter4jAsyncWrapper.getInstance();
        ResponseList<Status> userTimeLine = wrapper.fetchUserTimeLine(status.getUser().getId());
        TweetCellFactory cellFactory = new TweetCellFactory(wrapper);

        for (int i = 0; i < 5; i++) {
            Status timeLineStatus = userTimeLine.get(i);
            tweetPanel.add(Box.createRigidArea(new Dimension(1, 1)));
            tweetPanel.add(cellFactory.createTweetCell(timeLineStatus));
            tweetPanel.validate();
        }

        this.setTitle(status.getUser().getScreenName() + " の詳細");
        this.setSize(this.getPreferredSize());
    }
}
