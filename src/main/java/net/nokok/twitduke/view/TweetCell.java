package net.nokok.twitduke.view;

import com.google.common.base.Objects;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import net.nokok.twitduke.main.Config;
import net.nokok.twitduke.model.thread.IAsyncImageLoader;
import net.nokok.twitduke.view.ui.TWButton;
import net.nokok.twitduke.view.ui.TWLabel;
import net.nokok.twitduke.view.ui.TWPanel;
import net.nokok.twitduke.view.ui.color.DefaultColor;

public class TweetCell extends TWPanel implements IAsyncImageLoader {

    private final boolean isMention;
    private       boolean isFavorited;
    private       boolean isRetweeted;
    private       boolean isSelected;

    private final JLabel    icon        = new JLabel();
    private final JLabel    retweetIcon = new JLabel();
    private final TWLabel   userName    = new TWLabel();
    private final TWLabel   screenName  = new TWLabel();
    private final JTextArea tweetText   = new JTextArea();

    private final TWPanel  contentsNorthPanel = new TWPanel(new FlowLayout(FlowLayout.LEFT));
    private final TWButton favoriteButton     = new TWButton();
    private final TWButton retweetButton      = new TWButton();

    private final TWPanel thumbnailPanel = new TWPanel(new FlowLayout(FlowLayout.CENTER));

    public TweetCell(boolean isMention,
                     long statusId,
                     Icon userIcon,
                     String userName,
                     String screenName,
                     String tweetText) {
        initializeComponent();

        this.isMention = isMention;
        icon.setIcon(userIcon);
        this.userName.setText(userName);
        this.screenName.setText(screenName);
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
        this(isMention, statusId, userIcon, userName, "", tweetText);
        this.retweetIcon.setIcon(retweetIcon);
    }

    private void initializeComponent() {
        setLayout(new BorderLayout());

        icon.setBorder(new EmptyBorder(2, 0, 2, 5));

        retweetIcon.setPreferredSize(Config.ComponentSize.RETWEET_USER_ICON_SIZE);
        userName.setFont(Config.FontConfig.USER_NAME_FONT);
        screenName.setFont(Config.FontConfig.SCREEN_NAME_FONT);
        screenName.setForeground(DefaultColor.TweetCell.DEFAULT_FOREGROUND.darker());
        favoriteButton.setPreferredSize(Config.ComponentSize.FUNCTION_BUTTON_SIZE);
        favoriteButton.setBackground(DefaultColor.TweetCell.FAVORITE_BUTTON);
        retweetButton.setPreferredSize(Config.ComponentSize.FUNCTION_BUTTON_SIZE);
        retweetButton.setBackground(DefaultColor.TweetCell.RETWEET_BUTTON);
        tweetText.setForeground(DefaultColor.TweetCell.DEFAULT_FOREGROUND);
        tweetText.setEditable(false);
        tweetText.setLineWrap(true);
        tweetText.setOpaque(true);
        tweetText.setBorder(new EmptyBorder(2, 0, 5, 0));
        thumbnailPanel.setBackground(DefaultColor.TweetCell.DEFAULT_BACKGROUND);

        TWPanel contentsPanel = new TWPanel(new BorderLayout());
        contentsNorthPanel.setBackground(DefaultColor.TweetCell.DEFAULT_BACKGROUND);
        contentsNorthPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 3));
        contentsNorthPanel.add(favoriteButton);
        contentsNorthPanel.add(Box.createRigidArea(new Dimension(Config.ComponentSize.FUNCTION_BUTTON_MARGIN, 0)));
        contentsNorthPanel.add(retweetButton);
        contentsNorthPanel.add(Box.createRigidArea(new Dimension(Config.ComponentSize.BEFORE_USERNAME_MARGIN, 0)));
        contentsNorthPanel.add(userName);
        contentsNorthPanel.add(Box.createRigidArea(new Dimension(Config.ComponentSize.BEFORE_SCREENNAME_MARGIN, 0)));
        contentsNorthPanel.add(screenName);
        contentsNorthPanel.add(retweetIcon);

        contentsPanel.add(contentsNorthPanel, BorderLayout.NORTH);
        contentsPanel.add(tweetText, BorderLayout.CENTER);

        add(icon, BorderLayout.WEST);
        add(contentsPanel, BorderLayout.CENTER);

    }

    public void enableThumbnail(TWLabel imageLabel) {
        thumbnailPanel.add(imageLabel);
        add(thumbnailPanel, BorderLayout.SOUTH);
        getParent().validate();
    }

    public boolean isMention() {
        return isMention;
    }

    public void selectCell() {
        isSelected = true;
        changeColor(DefaultColor.TweetCell.SELECTED_BACKGROUND);
    }

    public void unSelectCell() {
        isSelected = false;
        changeColor(DefaultColor.TweetCell.DEFAULT_BACKGROUND);
    }

    public void unSelectMentionCell() {
        isSelected = false;
        changeColor(DefaultColor.TweetCell.MENTION_BACKGROUND);
    }

    public boolean isSelected() {
        return isSelected;
    }

    private void changeColor(Color color) {
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

    public void setMouseAction(MouseListener listener) {
        addMouseListener(listener);
        tweetText.addMouseListener(listener);
    }

    public boolean isFavorited() {
        return isFavorited;
    }

    public void favorite() {
        isFavorited = true;
        favoriteButton.setBackground(DefaultColor.TweetCell.FAVORITED_BACKGROUND);
    }

    public void unFavorite() {
        isFavorited = false;
        favoriteButton.setBackground(DefaultColor.TweetCell.FAVORITE_BUTTON);
    }

    public boolean isRetweeted() {
        return isRetweeted;
    }

    public void retweet() {
        isRetweeted = true;
        retweetButton.setBackground(DefaultColor.TweetCell.RETWEETED_BACKGROUND);
    }

    public void unRetweet() {
        isRetweeted = false;
        retweetButton.setBackground(DefaultColor.TweetCell.RETWEET_BUTTON);
    }

    public String getSelectedText() {
        return tweetText.getSelectedText();
    }

    public void clearSelectedText() {
        tweetText.select(0, 0);
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

    @Override
    public void imageLoading() {
        TWLabel loadingLabel = new TWLabel("[サムネイルの読み込み中...]");
        loadingLabel.setFont(new Font("", Font.BOLD, 10));
        thumbnailPanel.add(loadingLabel);
        add(thumbnailPanel, BorderLayout.SOUTH);
    }

    @Override
    public void imageLoaded(TWLabel label) {
        thumbnailPanel.removeAll();
        enableThumbnail(label);
    }
}
