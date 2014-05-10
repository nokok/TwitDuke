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
package net.nokok.twitduke.tweetcell;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import net.nokok.twitduke.components.basic.TWLabel;
import net.nokok.twitduke.components.basic.TWPanel;
import net.nokok.twitduke.components.basic.TWTextArea;
import net.nokok.twitduke.core.api.view.Selectable;

/**
 * ツイートを表示する標準サイズのセルです
 *
 */
public class DefaultTweetCell extends TWPanel implements Selectable {

    private static final long serialVersionUID = 8642406108943840213L;

    public static final Color SELECTED_COLOR = new Color(41, 128, 185);
    private boolean isSelected;

    JLabel userIcon = new TWLabel();
    JLabel userName = new TWLabel();
    JLabel screenName = new TWLabel();
    JTextArea textArea = new TWTextArea();
    JLabel thumbnail = new TWLabel();
    JPanel toolBarPanel = new TWPanel();

    /**
     * 標準サイズのセルを生成します
     */
    public DefaultTweetCell() {
        setLayout(new BorderLayout());
        JPanel tweetContentPanel = new TWPanel(new BorderLayout());
        tweetContentPanel.add(userIcon, BorderLayout.WEST);
        JPanel tweetTextPanel = new TWPanel(new BorderLayout());
        JPanel userNamePanel = new TWPanel(new FlowLayout(FlowLayout.LEFT));
        userNamePanel.add(userName);
        userNamePanel.add(screenName);
        tweetTextPanel.add(userNamePanel, BorderLayout.NORTH);
        tweetTextPanel.add(textArea, BorderLayout.CENTER);
        tweetTextPanel.add(thumbnail, BorderLayout.SOUTH);
        tweetContentPanel.add(tweetTextPanel, BorderLayout.CENTER);
        add(tweetContentPanel, BorderLayout.CENTER);
        add(toolBarPanel, BorderLayout.SOUTH);
    }

    @Override
    public boolean isSelected() {
        return isSelected;
    }

    @Override
    public void select() {
        if ( isSelected ) {
            return;
        }
        isSelected = true;
        setBackground(SELECTED_COLOR);
    }

    @Override
    public void unselect() {
        if ( !isSelected ) {
            return;
        }
        isSelected = false;
        setBackground(BACKGROUND_COLOR);
    }
}
