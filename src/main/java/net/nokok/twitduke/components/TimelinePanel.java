/*
 * The MIT License
 *
 * Copyright 2014 noko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package net.nokok.twitduke.components;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.swing.Scrollable;
import javax.swing.SwingUtilities;
import net.nokok.twitduke.components.basic.TWPanel;

/**
 * タイムラインを表示するパネルです。このパネルは独自に実装されたレイアウトマネージャーを持ちます。
 * レイアウトマネージャーを置き換える場合は通常のパネルと同様setLayoutメソッドで設定可能です。
 *
 */
public class TimelinePanel extends TWPanel implements Scrollable {

    private static final long serialVersionUID = 166135210622484665L;

    /**
     * 空のタイムラインパネルを生成します。
     */
    public TimelinePanel() {
        setLayout(new TimelineLayout());
    }

    @Override
    public Dimension getPreferredScrollableViewportSize() {
        Component[] components = getComponents();
        int width = this.getWidth();
        Double height = Stream.of(components).collect(Collectors.summingDouble(p -> p.getPreferredSize().getHeight()));
        return new Dimension(width, height.intValue());
    }

    @Override
    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 10;
    }

    @Override
    public boolean getScrollableTracksViewportHeight() {
        return false;
    }

    @Override
    public boolean getScrollableTracksViewportWidth() {
        return true;
    }

    @Override
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        return visibleRect.height / 2;
    }

    private class TimelineLayout implements LayoutManager {

        @Override
        public void addLayoutComponent(String name, Component comp) {

        }

        @Override
        public void layoutContainer(Container parent) {
            List<Component> components = Stream.of(parent.getComponents()).collect(Collectors.toList());
            SwingUtilities.invokeLater(() -> {
                if ( components.size() > 1000 ) {
                    for ( int i = 0; i < 500; i++ ) {
                        components.remove(i);
                    }
                    parent.validate();
                }
            });
            int currentY = 0;
            for ( Component component : components ) {
                component.setBounds(0, currentY, parent.getWidth(), component.getSize().height);
                component.setPreferredSize(new Dimension(parent.getWidth(), component.getPreferredSize().height));
                currentY += component.getPreferredSize().height + 1;
            }
        }

        @Override
        public Dimension minimumLayoutSize(Container parent) {
            Component[] components = parent.getComponents();
            int height = Stream.of(components).collect(Collectors.summingInt(c -> c.getMinimumSize().height));
            int width = parent.getWidth();
            return new Dimension(width, height);
        }

        @Override
        public Dimension preferredLayoutSize(Container parent) {
            Component[] components = parent.getComponents();
            int height = Stream.of(components).collect(Collectors.summingInt(c -> c.getPreferredSize().height));
            int width = parent.getWidth();
            return new Dimension(width, height);
        }

        @Override
        public void removeLayoutComponent(Component comp) {

        }

    }
}
