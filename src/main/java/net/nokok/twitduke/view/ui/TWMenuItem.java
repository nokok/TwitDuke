package net.nokok.twitduke.view.ui;

import javax.swing.JMenuItem;
import net.nokok.twitduke.view.ui.color.DefaultColor;

public class TWMenuItem extends JMenuItem {

    public TWMenuItem(String text) {
        super(text);
        setForeground(DefaultColor.TWButton.DEFAULT_FOREGROUND);
        setBackground(DefaultColor.TWButton.DEFAULT_BACKGROUND);
    }
}
