package net.nokok.twitduke.gui;

import net.nokok.twitduke.util.config.DefaultConfig;

import javax.swing.*;

/**
 * @author noko
 *         TweetListやUserListなどに使用されるセルの共通動作を定義します
 */
public class ElementCell extends JPanel {

    public ElementCell() {
        this.setSize(DefaultConfig.CELL_SIZE);
    }
}
