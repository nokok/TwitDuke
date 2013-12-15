package net.nokok.twitduke.view;

import net.nokok.twitduke.util.ui.UIColor;
import twitter4j.Status;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TweetCell extends JPanel {

    private final JLabel    userNameLabel;
    private final JTextArea tweetTextBody;
    private final JPanel    contentNorthPanel;
    private final JPanel    contentPanel;

    public TweetCell(Status status) {
        this.setBackground(UIColor.TweetCell.DEFAULT_BACKGROUND);
        this.setLayout(new BorderLayout());

        JLabel icon = new JLabel("");
        JLabel retweetSmallIcon = new JLabel("");
        userNameLabel = new JLabel("");
        tweetTextBody = new JTextArea();
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
                userName = "Retweet:" + status.getRetweetedStatus().getUser().getScreenName() + " by " + status.getUser().getScreenName();
            } else {
                imageURL = new URL(status.getUser().getProfileImageURL());
            }
            ImageIcon image = new ImageIcon(imageURL);
            icon.setIcon(image);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Pattern p = Pattern.compile("\n");
        Matcher m = p.matcher(tweetText);
        int count = 0;
        int index = 0;
        while (m.find(index)) {
            count++;
            index = m.end();
        }

        this.setPreferredSize(new Dimension(505, 50));
        int calcedHeight = this.getPreferredSize().height + count * 15;
        this.tweetTextBody.setText(tweetText);
        this.userNameLabel.setText(userName);
        this.setMinimumSize(new Dimension(505, 50));
        this.setPreferredSize(new Dimension(this.getPreferredSize().width, calcedHeight));
    }

    private void changeCellColor(Color newColor) {
        this.setBackground(newColor);
        tweetTextBody.setBackground(newColor);
        userNameLabel.setBackground(newColor);
        contentPanel.setBackground(newColor);
        contentNorthPanel.setBackground(newColor);
    }
}
