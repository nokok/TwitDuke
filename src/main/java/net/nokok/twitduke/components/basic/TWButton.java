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
package net.nokok.twitduke.components.basic;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

/**
 * TwitDuke用のカスタマイズ済みJButtonを定義します
 * 
 */
public class TWButton extends JButton {

    private static final long serialVersionUID = -6950374784491086356L;

    /**
     * ボタンの背景色です。
     */
    public static final Color BACKGROUND_COLOR = new Color(189, 195, 199);
    /**
     * ボタンの前景色(フォント色)です。
     */
    public static final Color FOREGROUND_COLOR = new Color(40, 40, 40);

    /**
     * 空のボタンを生成します
     */
    public TWButton() {
        setBackground(BACKGROUND_COLOR);
        setForeground(FOREGROUND_COLOR);
        setOpaque(true);
        setBorderPainted(false);
        addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                setBackground(getBackground().darker());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setBackground(getBackground().brighter());
            }
        });
    }

    /**
     * テキスト付きのボタンを生成します
     *
     * @param text ボタンにセットするテキスト
     */
    public TWButton(String text) {
        this();
        setText(text);
    }
}
