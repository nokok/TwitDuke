package net.nokok.twitduke.view;

import com.google.common.base.Objects;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import net.nokok.twitduke.view.ui.TWButton;
import net.nokok.twitduke.view.ui.TWLabel;
import net.nokok.twitduke.view.ui.TWPanel;
import net.nokok.twitduke.view.ui.color.DefaultColor;

public class TweetCell extends TWPanel implements Cloneable {

    private long    statusId;
    private boolean isMention;
    private boolean isFavorited;
    private boolean isRetweeted;

    private final JLabel    icon        = new JLabel();
    private final JLabel    retweetIcon = new JLabel();
    private final TWLabel   userName    = new TWLabel();
    private       JTextArea tweetText   = new JTextArea();

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
        this.statusId = statusId;
        this.icon.setIcon(userIcon);
        this.userName.setText(userName);
        this.tweetText.setText(tweetText);

        if (isMention) {
            this.changeColor(DefaultColor.TweetCell.MENTION_BACKGROUND);
        } else {
            this.changeColor(DefaultColor.TweetCell.DEFAULT_BACKGROUND);
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
        this.setLayout(new BorderLayout());
        this.retweetIcon.setPreferredSize(RETWEET_ICON_SIZE);
        this.userName.setFont(new Font("", Font.BOLD, 13));
        this.favoriteButton.setPreferredSize(FUNCTION_BUTTON_SIZE);
        this.favoriteButton.setBackground(DefaultColor.TweetCell.FAVORITE_BUTTON);
        this.retweetButton.setPreferredSize(FUNCTION_BUTTON_SIZE);
        this.retweetButton.setBackground(DefaultColor.TweetCell.RETWEET_BUTTON);
        this.tweetText.setForeground(DefaultColor.TweetCell.DEFAULT_FOREGROUND);
        this.tweetText.setEditable(false);
        this.tweetText.setLineWrap(true);
        this.tweetText.setOpaque(true);
        this.tweetText.setBorder(null);
        this.thumbnailPanel.setBackground(DefaultColor.TweetCell.DEFAULT_BACKGROUND);

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

    }

    public void setThumbnail(TWLabel imageLabel) {
        thumbnailPanel.add(imageLabel);
        enableThumbnail();
    }

    private void enableThumbnail() {
        this.add(thumbnailPanel, BorderLayout.SOUTH);
        thumbnailPanel.validate();
    }

    public boolean isMention() {
        return this.isMention;
    }

    public void changeColor(Color color) {
        this.setBackground(color);
        this.userName.setBackground(color);
        this.contentsNorthPanel.setBackground(color);
        this.tweetText.setBackground(color);
        this.thumbnailPanel.setBackground(color);
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

    public String getSelectedText() {
        return tweetText.getSelectedText();
    }

    public void clearSelectedText() {
        tweetText.select(0, 0);
    }

    @Override
    public TweetCell clone() {
        try {
            TweetCell cell = (TweetCell) super.clone();
            cell.tweetText = new JTextArea(cell.tweetText.getText());
            for (MouseListener listener : this.getMouseListeners()) {
                cell.addMouseListener(listener);
            }
            return cell;
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e.toString());
        }
    }

    @Override
    public int getHeight() {
        return (int) this.getPreferredSize().getHeight();
    }

    @Override
    public String toString() {
        String tweetText = this.tweetText.getText();

        return Objects.toStringHelper(this)
            .add("PrefSize", this.getPreferredSize())
            .add("Size", this.getSize())
            .add("MinimumSize", this.getMinimumSize())
            .add("Tweet", tweetText.length() > 5 ? tweetText.substring(0, 5) : tweetText)
            .add("User", userName.getText())
            .toString();
    }
}