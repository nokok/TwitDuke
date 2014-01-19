package net.nokok.twitduke.view.ui;

import javax.swing.Icon;
import javax.swing.JLabel;
import net.nokok.twitduke.view.ui.color.DefaultColor;

public class TWLabel extends JLabel {

    public TWLabel() {
        setForeground(DefaultColor.TweetCell.DEFAULT_FOREGROUND);
        setBackground(DefaultColor.DEFAULT_BACKGROUND);
    }

    public TWLabel(String text) {
        this();
        setText(text);
    }

    public TWLabel(Icon icon) {
        this();
        setIcon(icon);
    }
}
