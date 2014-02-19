package net.nokok.twitduke.view.ui;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import net.nokok.twitduke.main.Config;
import net.nokok.twitduke.view.ui.color.DefaultColor;

public class TWScrollPane extends JScrollPane {

    public TWScrollPane(JComponent panel) {
        super(panel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        setBackground(DefaultColor.DEFAULT_BACKGROUND);
        getVerticalScrollBar().setUnitIncrement(Config.SCROLL_SPEED);
        setOpaque(true);
        setBorder(null);
    }
}
