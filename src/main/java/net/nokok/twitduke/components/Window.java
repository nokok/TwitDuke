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

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import javax.swing.JFrame;
import net.nokok.twitduke.components.basic.TWFrame;
import net.nokok.twitduke.core.event.WindowEventListener;

/**
 * TwitDukeのメインウィンドウです
 */
public class Window implements WindowSize, Visible, Disposable {

    private final JFrame frame = new TWFrame("TwitDuke");

    public void addListener(WindowStateListener listener) {
        frame.addWindowStateListener(listener);
    }

    public void addListener(WindowEventListener listener) {
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                listener.closing();
            }

            @Override
            public void windowClosed(WindowEvent e) {
                listener.closed();
            }

            @Override
            public void windowOpened(WindowEvent e) {
                listener.opened();
            }

            @Override
            public void windowActivated(WindowEvent e) {
                listener.opening();
            }

            @Override
            public void windowStateChanged(WindowEvent e) {
                if ( e.getID() == WindowEvent.COMPONENT_RESIZED ) {
                    listener.sizeChanged(e.getWindow().getPreferredSize());
                }
            }
        });
    }

    @Override
    public void dispose() {
        frame.dispose();
    }

    @Override
    public int height() {
        return frame.getHeight();
    }

    @Override
    public void show() {
        frame.setVisible(true);
    }

    @Override
    public int width() {
        return frame.getWidth();
    }

    public String title() {
        return frame.getTitle();
    }
}
