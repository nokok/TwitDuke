package net.nokok.twitduke.view.ui;

import net.nokok.twitduke.view.ui.color.DefaultColor;

import javax.swing.*;
import java.awt.*;

public class TWPanel extends JPanel {

    public TWPanel() {
        super();
        this.setBackground(DefaultColor.DEFAULT_BACKGROUND);
    }

    public TWPanel(LayoutManager layoutManager) {
        this();
        this.setLayout(layoutManager);
    }

    public Component add(Component component, int index) {
        return super.add(component, index);
    }

    public void add(Component component, Object constraints) {
        super.add(component, constraints);
    }
}
