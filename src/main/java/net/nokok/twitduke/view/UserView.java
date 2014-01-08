package net.nokok.twitduke.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import net.nokok.twitduke.view.ui.TWButton;
import net.nokok.twitduke.view.ui.TWLabel;
import net.nokok.twitduke.view.ui.TWPanel;
import net.nokok.twitduke.view.ui.color.DefaultColor;

public class UserView extends JFrame {

    private final TWPanel  tweetPanel = new TWPanel();
    private final TWButton follow     = new TWButton("フォロー");
    private final TWButton sendReply  = new TWButton("リプライ");
    private final TWButton sendDM     = new TWButton("DM");
    private final TWButton block      = new TWButton("ブロック");
    private final TWButton reportSpam = new TWButton("スパム報告");
    private final TWButton openWeb    = new TWButton("TwitterWeb");

    private final TWLabel   nameLabel       = new TWLabel();
    private final TWLabel   screenNameLabel = new TWLabel();
    private final TWLabel   userIcon        = new TWLabel();
    private final TWLabel   tweetCount      = new TWLabel();
    private final TWLabel   followingCount  = new TWLabel();
    private final TWLabel   followerCount   = new TWLabel();
    private final TWLabel   favoritedCount  = new TWLabel();
    private final TWLabel   location        = new TWLabel();
    private final JTextArea bio             = new JTextArea();

    private UserView() {
        initializeComponent();
    }

    public UserView(Icon userIcon,
                    String name,
                    String screenName,
                    int tweetCount,
                    int following,
                    int follower,
                    int favorited,
                    String location,
                    String bio) {
        this();
        this.userIcon.setIcon(userIcon);
        this.nameLabel.setText(name);
        this.screenNameLabel.setText("@" + screenName);
        this.tweetCount.setText("ツイート: " + String.valueOf(tweetCount));
        this.followingCount.setText("フォロー: " + String.valueOf(following));
        this.followerCount.setText("フォロワー: " + String.valueOf(follower));
        this.favoritedCount.setText("お気に入り: " + String.valueOf(favorited));
        this.location.setText("場所: " + location);
        this.bio.setText(bio);
        this.pack();
    }

    private void initializeComponent() {
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setTitle("読み込み中...");
        this.setBackground(DefaultColor.DEFAULT_BACKGROUND);
        this.setVisible(true);
        this.setLayout(new BorderLayout());

        Font nameFont = new Font("", Font.BOLD, 15);
        nameLabel.setFont(nameFont);
        screenNameLabel.setFont(nameFont);

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

    }
}
