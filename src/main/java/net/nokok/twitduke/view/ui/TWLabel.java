package net.nokok.twitduke.view.ui;

import javax.swing.Icon;
import javax.swing.JLabel;
import net.nokok.twitduke.view.ui.color.DefaultColor;

public class TWLabel extends JLabel {

    public TWLabel() {
        this.setForeground(DefaultColor.TweetCell.DEFAULT_FOREGROUND);
        this.setBackground(DefaultColor.DEFAULT_BACKGROUND);
    }

    public TWLabel(String text) {
        this();
        this.setText(text);
    }

    public TWLabel(Icon icon) {
        this();
        this.setIcon(icon);
    }
}
