package net.nokok.twitduke.view;

import net.nokok.twitduke.view.ui.TWButton;
import net.nokok.twitduke.view.ui.TWLabel;
import net.nokok.twitduke.view.ui.TWPanel;
import net.nokok.twitduke.view.ui.color.DefaultColor;

import javax.swing.*;
import java.awt.*;

public class TweetCell extends TWPanel {

    private long statusId;
    private final JLabel    icon        = new JLabel();
    private final JLabel    retweetIcon = new JLabel();
    private final JLabel    userName    = new JLabel();
    private final JTextArea tweetText   = new JTextArea();

    private final Dimension ICON_SIZE         = new Dimension(50, 50);
    private final Dimension RETWEET_ICON_SIZE = new Dimension(15, 15);

    private final TWPanel contentsNorthPanel = new TWPanel(new FlowLayout(FlowLayout.LEFT));

    public TweetCell(long statusId, Icon userIcon, String userName, String tweetText) {
        this.setLayout(new BorderLayout());
        this.icon.setPreferredSize(ICON_SIZE);
        this.retweetIcon.setPreferredSize(RETWEET_ICON_SIZE);
        this.userName.setFont(new Font("", Font.BOLD, 13));
        this.userName.setForeground(DefaultColor.TweetCell.DEFAULT_FOREGROUND);
        this.tweetText.setForeground(DefaultColor.TweetCell.DEFAULT_FOREGROUND);
        this.tweetText.setEditable(false);
        this.tweetText.setLineWrap(true);
        this.tweetText.setOpaque(true);
        this.tweetText.setBorder(null);

        TWPanel contentsPanel = new TWPanel(new BorderLayout());
        contentsNorthPanel.setBackground(DefaultColor.TweetCell.DEFAULT_BACKGROUND);
        contentsNorthPanel.add(this.userName);
        contentsNorthPanel.add(this.retweetIcon);
        contentsPanel.add(contentsNorthPanel, BorderLayout.NORTH);
        contentsPanel.add(this.tweetText, BorderLayout.CENTER);

        this.add(this.icon, BorderLayout.WEST);
        this.add(contentsPanel, BorderLayout.CENTER);

        this.statusId = statusId;
        this.icon.setIcon(userIcon);
        this.userName.setText(userName);
        this.tweetText.setText(tweetText);

        this.changeColor(DefaultColor.TweetCell.DEFAULT_BACKGROUND);
        this.setMinimumSize(this.getPreferredSize());
    }

    public TweetCell(long statusId,
                     Icon userIcon,
                     Icon retweetIcon,
                     String userName,
                     String tweetText) {
        this(statusId, userIcon, userName, tweetText);
        this.changeColor(DefaultColor.TweetCell.RETWEET_BACKGROUND);
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
}


class TweetContentPanel extends TWPanel {

    private final TWPanel    contentTopPanel = new TWPanel(new FlowLayout(FlowLayout.LEFT));
    private final JTextField tweetTextField  = new JTextField();

    TweetContentPanel(String userName, String tweetText) {
        this.setLayout(new BorderLayout());
        tweetTextField.setText(tweetText);
        contentTopPanel.add(new TWLabel(userName));
        this.add(contentTopPanel, BorderLayout.NORTH);
        this.add(tweetTextField, BorderLayout.CENTER);
    }

    TweetContentPanel(Icon retweetIcon, String userName, String tweetText) {
        this(userName, tweetText);
        contentTopPanel.add(new TWLabel(retweetIcon));
    }
}

class UserIcon extends JLabel {

    private final Dimension ICON_SIZE = new Dimension(50, 50);

    UserIcon(Icon icon) {
        this.setPreferredSize(ICON_SIZE);
        this.setIcon(icon);
    }
}

class ContextMenuPanel extends TWPanel {

    private final TWButton showUserProfileButton = new TWButton("ユーザーを表示");
    private final TWButton favoriteButton        = new TWButton("お気に入り");
    private final TWButton retweetButton         = new TWButton("リツイート");

    public ContextMenuPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(favoriteButton);
        this.add(retweetButton);
    }
}