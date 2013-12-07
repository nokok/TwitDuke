package net.nokok.twitduke.view;

import twitter4j.Status;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

public class TweetCell extends JPanel {

    private JLabel icon;
    private JLabel userName;
    private JTextArea tweetText;

    public TweetCell() {
        this.setBackground(new Color(50, 50, 50));
        this.setLayout(new BorderLayout());

        //TODO:ブランクの画像を用意する
        icon = new JLabel(new ImageIcon(""));
        icon.setPreferredSize(new Dimension(50, 50));
        userName = new JLabel("");
        userName.setForeground(new Color(200, 200, 200));
        tweetText = new JTextArea("");
        tweetText.setForeground(new Color(200, 200, 200));
        tweetText.setBackground(this.getBackground());
        tweetText.setEditable(false);
        tweetText.setLineWrap(true);
        tweetText.setOpaque(true);
        tweetText.setBorder(null);

        this.setPreferredSize(new Dimension(505, 50));

        this.add(icon, BorderLayout.WEST);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(this.getBackground());
        contentPanel.add(userName, BorderLayout.NORTH);
        contentPanel.add(tweetText, BorderLayout.CENTER);
        this.add(contentPanel, BorderLayout.CENTER);

    }

    public TweetCell(Status status) {
        this();
        try {
            URL imageURL = new URL(status.getUser().getProfileImageURL());
            ImageIcon image = new ImageIcon(imageURL);
            this.icon.setIcon(image);
        } catch (MalformedURLException e) {
            //TODO:アイコンの取得に失敗したor取得中はブランク画像を使用する
            e.printStackTrace();
        }
        this.tweetText.setText(status.getText());
        this.userName.setText(status.getUser().getScreenName());
    }
}
