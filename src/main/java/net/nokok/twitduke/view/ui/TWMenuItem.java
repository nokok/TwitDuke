package net.nokok.twitduke.view.ui;

import net.nokok.twitduke.view.ui.color.DefaultColor;

import javax.swing.JMenuItem;

public class TWMenuItem extends JMenuItem {

    public TWMenuItem(String text) {
        super(text);
        this.setForeground(DefaultColor.TWButton.DEFAULT_FOREGROUND);
        this.setBackground(DefaultColor.TWButton.DEFAULT_BACKGROUND);
    }
}
