package net.nokok.twitduke.view.ui;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import net.nokok.twitduke.view.ui.color.DefaultColor;

public class TWScrollPane extends JScrollPane {

    private static final int SCROLL_SPEED = 35;

    public TWScrollPane(JComponent panel) {
        super(panel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        setBackground(DefaultColor.DEFAULT_BACKGROUND);
        getVerticalScrollBar().setUnitIncrement(SCROLL_SPEED);
        setOpaque(true);
        setBorder(null);
    }
}
