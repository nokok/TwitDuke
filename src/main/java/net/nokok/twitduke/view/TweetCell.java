package net.nokok.twitduke.view;

import net.nokok.twitduke.util.ui.UIColor;
import net.nokok.twitduke.util.ui.UISize;
import org.jetbrains.annotations.NotNull;
import twitter4j.Status;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

public class TweetCell extends JPanel {

    @NotNull
    private final long statusId;

    private final JLabel    userNameLabel;
    @NotNull
    private final JTextArea tweetTextBody;
    @NotNull
    private final JPanel    contentNorthPanel;
    @NotNull
    private final JPanel    contentPanel;

    public TweetCell(@NotNull Status status) {
        this.statusId = status.getId();
        this.setBackground(UIColor.TweetCell.DEFAULT_BACKGROUND);
        this.setLayout(new BorderLayout());

        JLabel icon = new JLabel("");
        JLabel retweetSmallIcon = new JLabel("");
        userNameLabel = new JLabel("");
        tweetTextBody = new JTextArea();
        icon.setPreferredSize(UISize.TweetCell.DEFAULT_ICON_SIZE);
        retweetSmallIcon.setPreferredSize(UISize.TweetCell.DEFAULT_RETWEET_USER_ICON_SIZE);

        userNameLabel.setFont(new Font("", Font.BOLD, 13));
        userNameLabel.setForeground(UIColor.TweetCell.DEFAULT_FOREGROUND);
        tweetTextBody.setForeground(UIColor.TweetCell.DEFAULT_FOREGROUND);
        tweetTextBody.setBackground(UIColor.TweetCell.DEFAULT_BACKGROUND);
        tweetTextBody.setEditable(false);
        tweetTextBody.setLineWrap(true);
        tweetTextBody.setOpaque(true);
        tweetTextBody.setBorder(null);

        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(this.getBackground());
        contentNorthPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        contentNorthPanel.setBackground(UIColor.TweetCell.DEFAULT_BACKGROUND);
        contentNorthPanel.add(userNameLabel);
        contentNorthPanel.add(retweetSmallIcon);
        contentPanel.add(contentNorthPanel, BorderLayout.NORTH);
        contentPanel.add(tweetTextBody, BorderLayout.CENTER);

        this.add(icon, BorderLayout.WEST);
        this.add(contentPanel, BorderLayout.CENTER);

        String tweetText = status.getText();
        String userName = status.getUser().getScreenName();
        try {
            URL imageURL, smallRetweetUserImageURL;
            if (status.isRetweet()) {
                imageURL = new URL(status.getRetweetedStatus().getUser().getProfileImageURL());
                smallRetweetUserImageURL = new URL(status.getUser().getProfileImageURL());
                Image smallRetweetUserImage = new ImageIcon(smallRetweetUserImageURL).getImage().getScaledInstance(15, 15, Image.SCALE_FAST);
                retweetSmallIcon.setIcon(new ImageIcon(smallRetweetUserImage));

                changeCellColor(UIColor.TweetCell.RETWEETED_BACKGROUND);
                tweetText = status.getRetweetedStatus().getText();
                userName = "Retweet: " + status.getRetweetedStatus().getUser().getScreenName() + " by " + status.getUser().getScreenName();
            } else {
                imageURL = new URL(status.getUser().getProfileImageURL());
            }
            ImageIcon image = new ImageIcon(imageURL);
            icon.setIcon(image);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        this.tweetTextBody.setText(tweetText);
        this.userNameLabel.setText(userName);
        this.setMinimumSize(this.getPreferredSize());
    }

    private void changeCellColor(Color newColor) {
        this.setBackground(newColor);
        tweetTextBody.setBackground(newColor);
        userNameLabel.setBackground(newColor);
        contentPanel.setBackground(newColor);
        contentNorthPanel.setBackground(newColor);
    }

    private long getStatusId() {
        return statusId;
    }
}
