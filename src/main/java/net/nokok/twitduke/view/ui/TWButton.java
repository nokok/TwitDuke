package net.nokok.twitduke.view.ui;

import javax.swing.JButton;
import net.nokok.twitduke.main.Config;
import net.nokok.twitduke.view.ui.color.DefaultColor;

public class TWButton extends JButton {

    public TWButton() {
        this("");
    }

    public TWButton(String title) {
        super(title);
        setBackground(DefaultColor.TWButton.DEFAULT_BACKGROUND);
        setForeground(DefaultColor.TWButton.DEFAULT_FOREGROUND);
        setOpaque(true);
        setBorderPainted(false);
        setFont(Config.FontConfig.BUTTON_FONT);
    }
}
