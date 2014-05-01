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
import javax.swing.JLabel;

/**
 * TwitDuke用のカスタマイズ済みJLabelを定義します
 * <p>
 * @author noko <nokok.kz at gmail.com>
 * @since 0.2
 */
public class TWLabel extends JLabel {

    private static final long serialVersionUID = -4138268682327167202L;

    /**
     * ラベルの背景色です。
     */
    public static final Color BACKGROUND_COLOR = new Color(40, 40, 40);
    /**
     * ラベルの前景色(フォント色)です。
     */
    public static final Color FOREGROUND_COLOR = new Color(200, 200, 200);

    /**
     * 空のラベルを生成します
     */
    public TWLabel() {
        setBackground(BACKGROUND_COLOR);
        setForeground(FOREGROUND_COLOR);
    }

    /**
     * 指定したテキストでラベルを生成します
     *
     * @param text ラベルにセットするテキスト
     */
    public TWLabel(String text) {
        this();
        setText(text);
    }
}
