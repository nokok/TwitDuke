package net.nokok.twitduke.view;

import com.google.common.base.Objects;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.Serializable;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import net.nokok.twitduke.util.ObjectCloner;
import net.nokok.twitduke.view.ui.TWButton;
import net.nokok.twitduke.view.ui.TWLabel;
import net.nokok.twitduke.view.ui.TWPanel;
import net.nokok.twitduke.view.ui.color.DefaultColor;

public class TweetCell extends TWPanel implements Cloneable, Serializable {

    public static final int USERNAME_FONT_SIZE = 13;
    private final boolean isMention;
    private       boolean isFavorited;
    private       boolean isRetweeted;

    private final JLabel    icon        = new JLabel();
    private final JLabel    retweetIcon = new JLabel();
    private final TWLabel   userName    = new TWLabel();
    private final JTextArea tweetText   = new JTextArea();

    private final Dimension RETWEET_ICON_SIZE    = new Dimension(15, 15);
    private final Dimension FUNCTION_BUTTON_SIZE = new Dimension(15, 13);

    private final TWPanel  contentsNorthPanel = new TWPanel(new FlowLayout(FlowLayout.LEFT));
    private final TWButton favoriteButton     = new TWButton();
    private final TWButton retweetButton      = new TWButton();

    private final TWPanel thumbnailPanel = new TWPanel(new FlowLayout(FlowLayout.CENTER));

    public TweetCell(boolean isMention,
                     long statusId,
                     Icon userIcon,
                     String userName,
                     String tweetText) {
        initializeComponent();

        this.isMention = isMention;
        icon.setIcon(userIcon);
        this.userName.setText(userName);
        this.tweetText.setText(tweetText);

        if (isMention) {
            changeColor(DefaultColor.TweetCell.MENTION_BACKGROUND);
        } else {
            changeColor(DefaultColor.TweetCell.DEFAULT_BACKGROUND);
        }
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

    private void initializeComponent() {
        setLayout(new BorderLayout());
        retweetIcon.setPreferredSize(RETWEET_ICON_SIZE);
        userName.setFont(new Font("", Font.BOLD, USERNAME_FONT_SIZE));
        favoriteButton.setPreferredSize(FUNCTION_BUTTON_SIZE);
        favoriteButton.setBackground(DefaultColor.TweetCell.FAVORITE_BUTTON);
        retweetButton.setPreferredSize(FUNCTION_BUTTON_SIZE);
        retweetButton.setBackground(DefaultColor.TweetCell.RETWEET_BUTTON);
        tweetText.setForeground(DefaultColor.TweetCell.DEFAULT_FOREGROUND);
        tweetText.setEditable(false);
        tweetText.setLineWrap(true);
        tweetText.setOpaque(true);
        tweetText.setBorder(null);
        thumbnailPanel.setBackground(DefaultColor.TweetCell.DEFAULT_BACKGROUND);

        TWPanel contentsPanel = new TWPanel(new BorderLayout());
        contentsNorthPanel.setBackground(DefaultColor.TweetCell.DEFAULT_BACKGROUND);
        contentsNorthPanel.add(favoriteButton);
        contentsNorthPanel.add(retweetButton);
        contentsNorthPanel.add(userName);
        contentsNorthPanel.add(retweetIcon);
        contentsPanel.add(contentsNorthPanel, BorderLayout.NORTH);
        contentsPanel.add(tweetText, BorderLayout.CENTER);

        add(icon, BorderLayout.WEST);
        add(contentsPanel, BorderLayout.CENTER);

    }

    public void setThumbnail(TWLabel imageLabel) {
        thumbnailPanel.add(imageLabel);
        enableThumbnail();
    }

    private void enableThumbnail() {
        add(thumbnailPanel, BorderLayout.SOUTH);
        thumbnailPanel.validate();
    }

    public boolean isMention() {
        return isMention;
    }

    public void changeColor(Color color) {
        setBackground(color);
        userName.setBackground(color);
        contentsNorthPanel.setBackground(color);
        tweetText.setBackground(color);
        thumbnailPanel.setBackground(color);
    }

    public void setFavoriteAction(ActionListener listener) {
        favoriteButton.addActionListener(listener);
    }

    public void setRetweetAction(ActionListener listener) {
        retweetButton.addActionListener(listener);
    }

    public void setTextAreaAction(MouseListener adapter) {
        tweetText.addMouseListener(adapter);
    }

    public boolean toggleFavoriteState() {
        if (isFavorited) {
            favoriteButton.setBackground(DefaultColor.TweetCell.FAVORITE_BUTTON);
        } else {
            favoriteButton.setBackground(DefaultColor.TweetCell.FAVORITED_BACKGROUND);
        }
        isFavorited = !isFavorited;
        return isFavorited;
    }

    public boolean toggleRetweetState() {
        if (isRetweeted) {
            retweetButton.setBackground(DefaultColor.TweetCell.RETWEET_BUTTON);
        } else {
            retweetButton.setBackground(DefaultColor.TweetCell.RETWEETED_BACKGROUND);
        }
        isRetweeted = !isRetweeted;
        return isRetweeted;
    }

    public String getSelectedText() {
        return tweetText.getSelectedText();
    }

    public void clearSelectedText() {
        tweetText.select(0, 0);
    }

    @Override
    public TweetCell clone() {
        TweetCell cell = (TweetCell) ObjectCloner.cloneObject(this);
        for (ActionListener listener : favoriteButton.getActionListeners()) {
            cell.favoriteButton.addActionListener(listener);
        }
        for (ActionListener listener : retweetButton.getActionListeners()) {
            cell.retweetButton.addActionListener(listener);
        }
        for (MouseListener listener : tweetText.getMouseListeners()) {
            cell.tweetText.addMouseListener(listener);
        }
        return cell;
    }

    @Override
    public int getHeight() {
        return (int) getPreferredSize().getHeight();
    }

    @Override
    public String toString() {
        String tweetText = this.tweetText.getText();

        String displayTweetText;
        if (tweetText.length() > 5) {
            displayTweetText = tweetText.substring(0, 5);
        } else {
            displayTweetText = tweetText;
        }

        return Objects.toStringHelper(this)
            .add("PrefSize", getPreferredSize())
            .add("Size", getSize())
            .add("MinimumSize", getMinimumSize())
            .add("Tweet", displayTweetText)
            .add("User", userName.getText())
            .toString();
    }
}
