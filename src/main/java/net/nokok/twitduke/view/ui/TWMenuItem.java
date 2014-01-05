package net.nokok.twitduke.view.ui;

import javax.swing.JMenuItem;
import net.nokok.twitduke.view.ui.color.DefaultColor;

public class TWMenuItem extends JMenuItem {

    public TWMenuItem(String text) {
        super(text);
        this.setForeground(DefaultColor.TWButton.DEFAULT_FOREGROUND);
        this.setBackground(DefaultColor.TWButton.DEFAULT_BACKGROUND);
    }
}
