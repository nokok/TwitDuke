package net.nokok.twitduke.view.ui;

import java.awt.Font;
import javax.swing.JButton;
import net.nokok.twitduke.view.ui.color.DefaultColor;

public class TWButton extends JButton {

    public static final int BUTTON_FONT_SIZE = 14;

    public TWButton() {
        this("");
    }

    public TWButton(String title) {
        super(title);
        setBackground(DefaultColor.TWButton.DEFAULT_BACKGROUND);
        setForeground(DefaultColor.TWButton.DEFAULT_FOREGROUND);
        setOpaque(true);
        setBorderPainted(false);
        setFont(new Font("", Font.PLAIN, BUTTON_FONT_SIZE));
    }
}
