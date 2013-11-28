package net.nokok.twitduke.gui;

import net.nokok.twitduke.util.config.DefaultConfig;

import javax.swing.*;

/**
 * @author noko
 *         ツールバーで使用されるボタンのクラスです
 */
public class ToolButton extends JButton {

    public ToolButton() {
        this.setPreferredSize(DefaultConfig.TOOLBAR_BUTTON_SIZE);
        this.setForeground(DefaultConfig.FOREGROUND_BUTTON_COLOR);
        this.setBackground(DefaultConfig.BACKGROUND_BUTTON_COLOR);
        this.setOpaque(true);
        this.setBorderPainted(false);
    }

    public ToolButton(String title) {
        this();
        this.setText(title);
    }
}
