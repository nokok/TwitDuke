package net.nokok.twitduke.view.ui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

public class TimelineLayout implements LayoutManager {

    private static final int SCROLLBAR_WIDTH = 15;

    @Override
    public void addLayoutComponent(String name, Component comp) {

    }

    @Override
    public void removeLayoutComponent(Component comp) {

    }

    @Override
    public synchronized Dimension preferredLayoutSize(Container parent) {
        //MainViewのレイアウトが変更されたらこことminimumLayoutを変更する
        int width = parent.getParent().getWidth() - SCROLLBAR_WIDTH;
        double height = 0;
        for (Component component : parent.getComponents()) {
            height += component.getPreferredSize().getHeight();
        }
        return new Dimension(width, (int) height);
    }

    @Override
    public synchronized Dimension minimumLayoutSize(Container parent) {
        int width = parent.getParent().getWidth() - SCROLLBAR_WIDTH;
        double height = 0;
        for (Component component : parent.getComponents()) {
            height += component.getMinimumSize().getHeight();
        }
        return new Dimension(width, (int) height);
    }

    @Override
    public void layoutContainer(Container parent) {
        Component[] components = parent.getComponents();
        int currentHeight = 0;
        int componentWidth = parent.getWidth();
        for (Component component : components) {
            int componentHeight = (int) component.getPreferredSize().getHeight();
            component.setBounds(0, currentHeight + 1, componentWidth, componentHeight);
            currentHeight += componentHeight;
        }
    }
}
