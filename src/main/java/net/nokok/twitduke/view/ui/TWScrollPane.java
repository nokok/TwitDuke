package net.nokok.twitduke.view.ui;

import javax.swing.JScrollPane;
import net.nokok.twitduke.view.ui.color.DefaultColor;

public class TWScrollPane extends JScrollPane {

    public TWScrollPane(TWPanel panel) {
        super(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.setBackground(DefaultColor.DEFAULT_BACKGROUND);
        this.getVerticalScrollBar().setUnitIncrement(35);
        this.setOpaque(true);
        this.setBorder(null);
    }
}
