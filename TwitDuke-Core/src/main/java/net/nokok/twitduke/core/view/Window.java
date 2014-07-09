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
package net.nokok.twitduke.core.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import javax.swing.JComponent;
import javax.swing.JFrame;
import net.nokok.twitduke.base.event.WindowEventListener;
import net.nokok.twitduke.components.Disposable;
import net.nokok.twitduke.components.Visible;
import net.nokok.twitduke.components.WindowSize;
import net.nokok.twitduke.components.basics.TWFrame;
import net.nokok.twitduke.core.type.ComponentInsertable;
import twitter4j.auth.AccessToken;

/**
 * TwitDukeのメインウィンドウです
 */
public class Window implements WindowSize, Visible, Disposable, ComponentInsertable {

    private final JFrame frame = new TWFrame("TwitDuke");
    private JComponent contents;

    public Window() {
        frame.setLayout(new BorderLayout());
    }

    /**
     * ウィンドウ状態イベントを受け取るリスナーを追加します
     *
     * @param listener
     */
    public void addListener(WindowStateListener listener) {
        frame.addWindowStateListener(listener);
    }

    /**
     *
     * @param listener
     */
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

    public void addHeader(JComponent component) {
        frame.add(component, BorderLayout.NORTH);
        frame.validate();
    }

    public void addContents(JComponent component) {
        if ( component instanceof ScrollableTimelinePanel ) {
            frame.add(((ScrollableTimelinePanel) component).getScrollPane(), BorderLayout.CENTER);
        } else {
            frame.add(component, BorderLayout.CENTER);
        }
        this.contents = component;
        frame.validate();
    }

    public void addFooter(JComponent component) {
        frame.add(component, BorderLayout.SOUTH);
        frame.validate();
    }

    public void addRightPanel(JComponent component) {
        frame.add(component, BorderLayout.EAST);
        frame.validate();
    }

    @Override
    public void insert(Component component) {
        this.contents.add(component);
        frame.validate();
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

    /**
     * @return ウィンドウタイトル
     */
    public String title() {
        return frame.getTitle();
    }

    public static Window createNewWindow(AccessToken accessToken) {
        Window window = new Window();
        window.addContents(new ScrollableTimelinePanel());
        TweetTextArea textArea = new TweetTextArea(accessToken);
        textArea.setPreferredSize(new Dimension(-1, 50));
        window.addHeader(textArea);
        window.show();
        return window;
    }
}
