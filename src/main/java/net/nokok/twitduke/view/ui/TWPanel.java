package net.nokok.twitduke.view.ui;

import java.awt.Component;
import java.awt.LayoutManager;
import javax.swing.JPanel;
import net.nokok.twitduke.view.ui.color.DefaultColor;

public class TWPanel extends JPanel {

    public TWPanel() {
        setBackground(DefaultColor.DEFAULT_BACKGROUND);
    }

    public TWPanel(LayoutManager layoutManager) {
        this();
        setLayout(layoutManager);
    }

    @Override
    public Component add(Component comp, int index) {
        return super.add(comp, index);
    }

    @Override
    public void add(Component comp, Object constraints) {
        super.add(comp, constraints);
    }
}
