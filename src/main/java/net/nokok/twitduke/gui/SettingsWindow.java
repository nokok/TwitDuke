package net.nokok.twitduke.gui;

import net.nokok.twitduke.util.config.DefaultConfig;

import javax.swing.*;

/**
 * @author noko
 *         設定画面のクラスです
 */
public class SettingsWindow extends JFrame {
    private static final SettingsWindow instance = new SettingsWindow();

    private SettingsWindow() {
        this.setSize(DefaultConfig.WINDOW_SIZE);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public static SettingsWindow getInstance() {
        return instance;
    }
}
