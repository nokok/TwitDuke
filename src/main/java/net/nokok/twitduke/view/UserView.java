package net.nokok.twitduke.view;

import net.nokok.twitduke.view.ui.TWLabel;
import net.nokok.twitduke.view.ui.TWPanel;
import net.nokok.twitduke.view.ui.color.DefaultColor;
import twitter4j.Status;

import javax.swing.*;
import java.awt.*;

public class UserView extends JFrame {

    private final Dimension DEFAULT_SIZE        = new Dimension(300, 600);
    private final TWLabel    userIcon            = new TWLabel("ラベル");
    private final TWLabel    userIdLabel         = new TWLabel();
    private final TWLabel    screenNameLabel     = new TWLabel();
    private final TWLabel    followingCountLabel = new TWLabel();
    private final TWLabel    followerCountLabel  = new TWLabel();
    private final TWLabel    locationLabel       = new TWLabel();
    private final TWLabel    webURLLabel         = new TWLabel();
    private final JTextArea bioArea             = new JTextArea();

    public UserView(Status status) {
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setTitle(status.getUser().getScreenName() + " の詳細");
        this.setSize(DEFAULT_SIZE);
        this.setLayout(new BorderLayout());
        this.setBackground(DefaultColor.DEFAULT_BACKGROUND);


        this.userIcon.setIcon(new ImageIcon(status.getUser().getProfileImageURL()));
        this.userIdLabel.setText("@" + status.getUser().getScreenName());

        TWPanel northPanel = new TWPanel(new BorderLayout());
        northPanel.setPreferredSize(new Dimension(300, 300));
        northPanel.add(userIcon, BorderLayout.CENTER);
        northPanel.add(new TWPanel(new FlowLayout(FlowLayout.CENTER)).add(userIdLabel), BorderLayout.NORTH);
        TWPanel centerPanel = new TWPanel(new BorderLayout());
        this.add(northPanel, BorderLayout.NORTH);
        this.add(centerPanel, BorderLayout.CENTER);
    }
}
