package net.nokok.twitduke.view;

import net.nokok.twitduke.view.ui.TWPanel;
import net.nokok.twitduke.view.ui.color.DefaultColor;

import javax.swing.*;

class TWScrollPane extends JScrollPane {

    TWScrollPane(TWPanel panel) {
        super(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.setBackground(DefaultColor.DEFAULT_BACKGROUND);
        this.getVerticalScrollBar().setUnitIncrement(35);
        this.setOpaque(true);
        this.setBorder(null);
    }
}
