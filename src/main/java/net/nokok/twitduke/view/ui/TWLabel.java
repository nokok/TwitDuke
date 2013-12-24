package net.nokok.twitduke.view.ui;

import net.nokok.twitduke.view.ui.color.DefaultColor;

import javax.swing.*;

public class TWLabel extends JLabel {

    public TWLabel(String text) {
        super(text);
        this.setBackground(DefaultColor.DEFAULT_BACKGROUND);
    }

    public TWLabel(Icon icon) {
        this("");
        this.setIcon(icon);
    }

    public void setIcon(Icon icon) {
        super.setIcon(icon);
    }
}
