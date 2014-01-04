package net.nokok.twitduke.view.ui;

import net.nokok.twitduke.view.ui.color.DefaultColor;

import javax.swing.JScrollPane;

public class TWScrollPane extends JScrollPane {

    public TWScrollPane(TWPanel panel) {
        super(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.setBackground(DefaultColor.DEFAULT_BACKGROUND);
        this.getVerticalScrollBar().setUnitIncrement(35);
        this.setOpaque(true);
        this.setBorder(null);
    }
}
