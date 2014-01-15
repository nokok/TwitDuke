package net.nokok.twitduke.model;

import com.google.common.collect.Lists;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.util.stream.Collectors;

public class TimelineLayout implements LayoutManager {

    @Override
    public void addLayoutComponent(String name, Component comp) {

    }

    @Override
    public void removeLayoutComponent(Component comp) {

    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        int width = parent.getWidth();
        double height = Lists.newArrayList(parent.getComponents())
            .stream()
            .collect(
                Collectors.summingDouble(e -> e.getPreferredSize().getHeight())
            );
        height += parent.getComponentCount();
        return new Dimension(width, (int) height);
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        int width = parent.getWidth();
        int height = 0;

        for (Component component : parent.getComponents()) {
            height += component.getMinimumSize().getHeight();
        }
        return new Dimension(width, height);
    }

    @Override
    public void layoutContainer(Container parent) {
        Component[] components = parent.getComponents();
        int currentHeight = 0;
        for (Component component : components) {
            int componentWidth = parent.getWidth();
            int componentHeight = (int) component.getMinimumSize().getHeight();
            component.setBounds(0, currentHeight + 1, componentWidth, componentHeight);
            currentHeight += componentHeight;
        }
    }
}
