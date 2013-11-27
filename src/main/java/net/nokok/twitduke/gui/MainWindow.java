package net.nokok.twitduke.gui;

import net.nokok.twitduke.util.config.DefaultConfig;

import javax.swing.*;

/**
 * @author noko
 *         TwitDukeのメインウィンドウのクラスです
 */
public class MainWindow extends JFrame {

    private MainWindow() {
        this.setSize(DefaultConfig.WINDOW_SIZE);
        this.setTitle(DefaultConfig.FORMATTED_APP_VERSION);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    private static class SingletonHolder {
        private static final MainWindow instance = new MainWindow();
    }

    public static MainWindow getInstance() {
        return SingletonHolder.instance;
    }

}