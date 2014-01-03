package net.nokok.twitduke.view;

import net.nokok.twitduke.view.ui.TWButton;
import net.nokok.twitduke.view.ui.TWPanel;
import net.nokok.twitduke.view.ui.color.DefaultColor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;

public class TweetCell extends TWPanel {

    private long    statusId;
    private boolean isMention;
    private boolean isFavorited;
    private boolean isRetweeted;

    private final JLabel    icon        = new JLabel();
    private final JLabel    retweetIcon = new JLabel();
    private final JLabel    userName    = new JLabel();
    private final JTextArea tweetText   = new JTextArea();

    private final Dimension ICON_SIZE            = new Dimension(50, 50);
    private final Dimension RETWEET_ICON_SIZE    = new Dimension(15, 15);
    private final Dimension FUNCTION_BUTTON_SIZE = new Dimension(15, 13);

    private final TWPanel  contentsNorthPanel = new TWPanel(new FlowLayout(FlowLayout.LEFT));
    private final TWButton favoriteButton     = new TWButton();
    private final TWButton retweetButton      = new TWButton();

    public TweetCell(boolean isMention, long statusId, Icon userIcon, String userName, String tweetText) {
        this.isMention = isMention;
        this.setLayout(new BorderLayout());
        this.icon.setPreferredSize(ICON_SIZE);
        this.retweetIcon.setPreferredSize(RETWEET_ICON_SIZE);
        this.userName.setFont(new Font("", Font.BOLD, 13));
        this.userName.setForeground(DefaultColor.TweetCell.DEFAULT_FOREGROUND);
        this.favoriteButton.setPreferredSize(FUNCTION_BUTTON_SIZE);
        this.favoriteButton.setBackground(DefaultColor.TweetCell.FAVORITE_BUTTON);
        this.retweetButton.setPreferredSize(FUNCTION_BUTTON_SIZE);
        this.retweetButton.setBackground(DefaultColor.TweetCell.RETWEET_BUTTON);
        this.tweetText.setForeground(DefaultColor.TweetCell.DEFAULT_FOREGROUND);
        this.tweetText.setEditable(false);
        this.tweetText.setLineWrap(true);
        this.tweetText.setOpaque(true);
        this.tweetText.setBorder(null);

        TWPanel contentsPanel = new TWPanel(new BorderLayout());
        contentsNorthPanel.setBackground(DefaultColor.TweetCell.DEFAULT_BACKGROUND);
        contentsNorthPanel.add(this.favoriteButton);
        contentsNorthPanel.add(this.retweetButton);
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

    public boolean isMention() {
        return this.isMention;
    }

    public void changeColor(Color color) {
        this.setBackground(color);
        this.userName.setBackground(color);
        this.contentsNorthPanel.setBackground(color);
        this.tweetText.setBackground(color);
    }

    public static TweetCell copy(TweetCell oldCell) {
        return new TweetCell(oldCell.isMention, oldCell.statusId, oldCell.icon.getIcon(), oldCell.userName.getText(), oldCell.tweetText.getText());
    }

    public void setFavoriteAction(MouseAdapter adapter) {
        this.favoriteButton.addMouseListener(adapter);
    }

    public void setRetweetAction(MouseAdapter adapter) {
        this.retweetButton.addMouseListener(adapter);
    }

    public void setTextAreaAction(MouseAdapter adapter) {
        this.tweetText.addMouseListener(adapter);
    }

    public boolean toggleFavoriteState() {
        if (isFavorited) {
            this.favoriteButton.setBackground(DefaultColor.TweetCell.FAVORITE_BUTTON);
        } else {
            this.favoriteButton.setBackground(DefaultColor.TweetCell.FAVORITED_BACKGROUND);
        }
        isFavorited = !isFavorited;
        return isFavorited;
    }

    public boolean toggleRetweetState() {
        if (isRetweeted) {
            this.retweetButton.setBackground(DefaultColor.TweetCell.RETWEET_BUTTON);
        } else {
            this.retweetButton.setBackground(DefaultColor.TweetCell.RETWEETED_BACKGROUND);
        }
        isRetweeted = !isRetweeted;
        return isRetweeted;
    }

    public long getStatusId() {
        return statusId;
    }

    public String getSelectedText() {
        return tweetText.getSelectedText();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("Icon:");
        builder.append(icon.toString()).append("\n");
        builder.append("User:");
        builder.append(userName.getText()).append("\n");
        builder.append("Tweet:");
        builder.append(tweetText.getText()).append("\n");
        return builder.toString();
    }
}