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

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import net.nokok.twitduke.components.basic.TWScrollPane;

public class ScrollableTimelinePanel {

    private final JPanel timelinePanel;
    private final TWScrollPane scrollPane;

    public ScrollableTimelinePanel() {
        timelinePanel = new TimelinePanel();
        scrollPane = new TWScrollPane(timelinePanel);
    }

    public void addComponent(JComponent component) {
        SwingUtilities.invokeLater(() -> {
            if ( !scrollPane.isScrollbarTop() ) {
                scrollPane.shiftScrollBar(component.getHeight() + 1);
            }
            timelinePanel.add(component);
        });
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

}
