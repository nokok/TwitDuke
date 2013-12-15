package net.nokok.twitduke.view;

import net.nokok.twitduke.util.ui.UIColor;
import twitter4j.Status;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

public class TweetCell extends JPanel {

    private JLabel    icon;
    private JLabel    retweetSmallIcon;
    private JLabel    userNameLabel;
    private JTextArea tweetTextBody;
    private JPanel    contentNorthPanel;
    private JPanel    contentPanel;

    public TweetCell() {
        this.setBackground(UIColor.TweetCell.DEFAULT_BACKGROUND);
        this.setLayout(new BorderLayout());

        icon = new JLabel("");
        retweetSmallIcon = new JLabel("");
        userNameLabel = new JLabel("");
        tweetTextBody = new JTextArea();

        this.setPreferredSize(new Dimension(505, 50));
        icon.setPreferredSize(new Dimension(50, 50));
        retweetSmallIcon.setPreferredSize(new Dimension(15, 15));

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

    }

    public TweetCell(Status status) {
        this();
        String tweetText = status.getText();
        String userName = status.getUser().getScreenName();
        try {
            URL imageURL, smallRetweetUserImageURL;
            if (status.isRetweet()) {
                imageURL = new URL(status.getRetweetedStatus().getUser().getProfileImageURL());
                smallRetweetUserImageURL = new URL(status.getUser().getProfileImageURL());
                Image smallRetweetUserImage = new ImageIcon(smallRetweetUserImageURL).getImage().getScaledInstance(15, 15, Image.SCALE_FAST);
                this.retweetSmallIcon.setIcon(new ImageIcon(smallRetweetUserImage));

                changeCellColor(UIColor.TweetCell.RETWEETED_BACKGROUND);
                tweetText = status.getRetweetedStatus().getText();
                userName = "Retweet:" + status.getRetweetedStatus().getUser().getScreenName() + " by " + status.getUser().getScreenName();
            } else {
                imageURL = new URL(status.getUser().getProfileImageURL());
            }
            ImageIcon image = new ImageIcon(imageURL);
            this.icon.setIcon(image);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        this.setPreferredSize(new Dimension(this.getWidth(), userNameLabel.getHeight() + tweetTextBody.getHeight()));
        this.tweetTextBody.setText(tweetText);
        this.userNameLabel.setText(userName);
    }

    private void changeCellColor(Color newColor) {
        this.setBackground(newColor);
        tweetTextBody.setBackground(newColor);
        userNameLabel.setBackground(newColor);
        contentPanel.setBackground(newColor);
        contentNorthPanel.setBackground(newColor);
    }
}
