package net.nokok.twitduke.view.ui;

import net.nokok.twitduke.view.ui.color.DefaultColor;

import javax.swing.*;
import java.awt.*;

public class TWButton extends JButton {

    public TWButton() {
        this("");
    }

    public TWButton(String title) {
        super(title);
        this.setBackground(DefaultColor.TWButton.DEFAULT_BACKGROUND);
        this.setForeground(DefaultColor.TWButton.DEFAULT_FOREGROUND);
        this.setOpaque(true);
        this.setBorderPainted(false);
        this.setFont(new Font("", Font.PLAIN, 14));
    }
}
