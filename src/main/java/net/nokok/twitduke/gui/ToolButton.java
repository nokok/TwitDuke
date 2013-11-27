package net.nokok.twitduke.gui;

import net.nokok.twitduke.util.config.DefaultConfig;

import javax.swing.*;

/**
 * @author noko
 *         ツールバーで使用されるボタンのクラスです
 */
public class ToolButton extends JButton {

    public ToolButton(){
        this.setMinimumSize(DefaultConfig.TOOLBAR_BUTTON_SIZE);
    }
}
