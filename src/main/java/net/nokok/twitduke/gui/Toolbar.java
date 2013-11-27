package net.nokok.twitduke.gui;

import net.nokok.twitduke.util.config.DefaultConfig;

import javax.swing.*;

/**
 * @author noko
 *         ツールバーを構成するパネルのクラスです
 */
public class Toolbar extends JPanel {

    public Toolbar() {
        this.setPreferredSize(DefaultConfig.TOOLBAR_SIZE);
    }
}
