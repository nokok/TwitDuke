package net.nokok.twitduke.view;

import net.nokok.twitduke.view.ui.TWButton;
import net.nokok.twitduke.view.ui.TWPanel;
import net.nokok.twitduke.view.ui.color.DefaultColor;

import javax.swing.*;
import java.awt.*;

public class TweetCell extends TWPanel {

    private long    statusId;
    private boolean isFavorited;

    private final JLabel    icon        = new JLabel();
    private final JLabel    retweetIcon = new JLabel();
    private final JLabel    userName    = new JLabel();
    private final JTextArea tweetText   = new JTextArea();

    private final Dimension ICON_SIZE            = new Dimension(50, 50);
    private final Dimension RETWEET_ICON_SIZE    = new Dimension(15, 15);
    private final Dimension FUNCTION_BUTTON_SIZE = new Dimension(18, 8);

    private final TWPanel  contentsNorthPanel = new TWPanel(new FlowLayout(FlowLayout.LEFT));
    private final TWButton favoriteButton     = new TWButton();
    private final TWButton retweetButton      = new TWButton();
    private final TWButton otherActionButton  = new TWButton();

    public TweetCell(boolean isMention, long statusId, Icon userIcon, String userName, String tweetText) {
        this.setLayout(new BorderLayout());
        this.icon.setPreferredSize(ICON_SIZE);
        this.retweetIcon.setPreferredSize(RETWEET_ICON_SIZE);
        this.userName.setFont(new Font("", Font.BOLD, 13));
        this.userName.setForeground(DefaultColor.TweetCell.DEFAULT_FOREGROUND);
        this.favoriteButton.setPreferredSize(FUNCTION_BUTTON_SIZE);
        this.favoriteButton.setBackground(DefaultColor.TweetCell.FAVORITE_BUTTON);
        this.retweetButton.setPreferredSize(FUNCTION_BUTTON_SIZE);
        this.retweetButton.setBackground(DefaultColor.TweetCell.RETWEET_BUTTON);
        this.otherActionButton.setPreferredSize(FUNCTION_BUTTON_SIZE);
        this.tweetText.setForeground(DefaultColor.TweetCell.DEFAULT_FOREGROUND);
        this.tweetText.setEditable(false);
        this.tweetText.setLineWrap(true);
        this.tweetText.setOpaque(true);
        this.tweetText.setBorder(null);

        TWPanel contentsPanel = new TWPanel(new BorderLayout());
        contentsNorthPanel.setBackground(DefaultColor.TweetCell.DEFAULT_BACKGROUND);
        contentsNorthPanel.add(this.favoriteButton);
        contentsNorthPanel.add(this.retweetButton);
        contentsNorthPanel.add(this.otherActionButton);
        contentsNorthPanel.add(this.userName);
        contentsNorthPanel.add(this.retweetIcon);
        contentsPanel.add(this.contentsNorthPanel, BorderLayout.NORTH);
        contentsPanel.add(this.tweetText, BorderLayout.CENTER);


        this.add(this.icon, BorderLayout.WEST);
        this.add(contentsPanel, BorderLayout.CENTER);
        this.add(Box.createRigidArea(new Dimension(this.getWidth(), 3)), BorderLayout.SOUTH);

        this.statusId = statusId;
        this.icon.setIcon(userIcon);
        this.userName.setText(userName);
        this.tweetText.setText(tweetText);

        if (isMention) {
            this.changeColor(DefaultColor.TweetCell.MENTION_BACKGROUND);
        } else {
            this.changeColor(DefaultColor.TweetCell.DEFAULT_BACKGROUND);
        }
        this.setMinimumSize(this.getPreferredSize());
    }

    public TweetCell(boolean isMention,
                     long statusId,
                     Icon userIcon,
                     Icon retweetIcon,
                     String userName,
                     String tweetText) {
        this(isMention, statusId, userIcon, userName, tweetText);
        this.retweetIcon.setIcon(retweetIcon);
    }

    public void changeColor(Color color) {
        this.setBackground(color);
        this.userName.setBackground(color);
        this.contentsNorthPanel.setBackground(color);
        this.tweetText.setBackground(color);
    }

    public long getStatusId() {
        return statusId;
    }

    public TWButton getFavoriteButton() {
        return favoriteButton;
    }

    public TWButton getRetweetButton() {
        return retweetButton;
    }

    public TWButton getOtherActionButton() {
        return otherActionButton;
    }

    public boolean isFavorited() {
        return isFavorited;
    }

    public void setFavorited(boolean flag) {
        isFavorited = flag;
    }
}