package net.nokok.twitduke.gui;

import net.nokok.twitduke.util.config.DefaultConfig;

import javax.swing.*;
import java.awt.*;

/**
 * @author noko
 *         TwitDukeのメインウィンドウのクラスです
 */
public class MainWindow extends JFrame {

    private final JTextArea tweetTextArea = new JTextArea();

    private MainWindow() {
        this.setSize(DefaultConfig.WINDOW_SIZE);
        this.setTitle(DefaultConfig.APP_TITLE + DefaultConfig.FORMATTED_APP_VERSION);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initUI();
        this.setVisible(true);
    }

    private void initUI() {
        BorderLayout borderLayout = new BorderLayout();
        this.setLayout(borderLayout);

        JPanel topPanel = new JPanel(new BorderLayout());
        tweetTextArea.setPreferredSize(DefaultConfig.TWEET_TEXTAREA_SIZE);
        tweetTextArea.setLineWrap(true);
        topPanel.add(tweetTextArea, BorderLayout.NORTH);
        topPanel.add(new Toolbar(), BorderLayout.SOUTH);
        this.add(topPanel, BorderLayout.NORTH);
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(TweetList.getInstance(), BorderLayout.CENTER);
        this.add(centerPanel, BorderLayout.CENTER);

    }

    private static class SingletonHolder {
        private static final MainWindow instance = new MainWindow();
    }

    public static MainWindow getInstance() {
        return SingletonHolder.instance;
    }
}
