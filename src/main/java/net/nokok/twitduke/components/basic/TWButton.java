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
import javax.swing.JButton;
import net.nokok.twitduke.components.Selectable;

/**
 * TwitDuke用のカスタマイズ済みJButtonを定義します
 *
 */
public class TWButton extends JButton implements Selectable {

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
     * デフォルトのボタン選択色です
     */
    public static final Color DEFAULT_SELECT_COLOR = new Color(189, 195, 199).darker();

    private boolean isSelected;

    /**
     * 空のボタンを生成します
     */
    public TWButton() {
        setBackground(BACKGROUND_COLOR);
        setForeground(FOREGROUND_COLOR);
        setOpaque(true);
        setBorderPainted(false);
        addMouseListener(new CommonSelectMouseAction());
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

    @Override
    public boolean isSelect() {
        return isSelected;
    }

    @Override
    public void select() {
        setBackground(DEFAULT_SELECT_COLOR);
    }

    @Override
    public void unselect() {
        setBackground(BACKGROUND_COLOR);
    }
}
