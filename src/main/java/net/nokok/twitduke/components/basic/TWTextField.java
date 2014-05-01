/*
 * The MIT License
 *
 * Copyright 2014 noko <nokok.kz at gmail.com>.
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
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

/**
 * TwitDuke用のカスタマイズ済みJTextFieldです
 * <p>
 * @author noko <nokok.kz at gmail.com>
 */
public class TWTextField extends JTextField {

    private static final long serialVersionUID = 1886443858870774301L;

    /**
     * テキストフィールドの背景色です
     */
    public static final Color BACKGROUND_COLOR = new Color(40, 40, 40);
    /**
     * テキストフィールドの前景色です
     */
    public static final Color FOREGROUND_COLOR = new Color(200, 200, 200);
    /**
     * テキストフィールドを囲む枠の線の色です
     */
    public static final Color BORDER_COLOR = new Color(200, 200, 200);

    /**
     * 空のテキストフィールドを生成します。
     */
    public TWTextField() {
        setBackground(BACKGROUND_COLOR);
        setForeground(FOREGROUND_COLOR);
        setCaretColor(Color.WHITE);
        setBorder(new LineBorder(BORDER_COLOR));
        setOpaque(true);
    }
}
