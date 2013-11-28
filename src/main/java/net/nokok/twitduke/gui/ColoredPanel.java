package net.nokok.twitduke.gui;

import net.nokok.twitduke.util.config.DefaultConfig;

import javax.swing.*;

/**
 * @author noko
 *         着色されたパネルです
 */
public class ColoredPanel extends JPanel {

    public ColoredPanel() {
        this.setBackground(DefaultConfig.BACKGROUND_BUTTON_COLOR);
    }
}
