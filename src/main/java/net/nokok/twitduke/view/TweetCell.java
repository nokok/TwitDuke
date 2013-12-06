package net.nokok.twitduke.view;

import javax.swing.*;
import java.awt.*;

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

        this.setPreferredSize(new Dimension(505, tweetText.getPreferredSize().height + 70));

        this.add(icon, BorderLayout.WEST);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(this.getBackground());
        contentPanel.add(userName, BorderLayout.NORTH);
        contentPanel.add(tweetText, BorderLayout.CENTER);
        this.add(contentPanel, BorderLayout.CENTER);

    }

    public TweetCell(ImageIcon icon, String userName, String tweetText) {
        this();
        this.icon.setIcon(icon);
        this.userName.setText(userName);
        this.tweetText.setText(tweetText);
    }
}
