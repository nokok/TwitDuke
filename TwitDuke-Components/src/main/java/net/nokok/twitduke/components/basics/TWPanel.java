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
package net.nokok.twitduke.components.basics;

import java.awt.Color;
import java.awt.LayoutManager;
import javax.swing.JPanel;

/**
 * TwitDuke用のカスタマイズ済みJPanelです
 *
 */
public class TWPanel extends JPanel {

    private static final long serialVersionUID = -2656367163678204098L;

    /**
     * パネルの背景色です。
     */
    public static final Color BACKGROUND_COLOR = new Color(40, 40, 40);
    /**
     * パネルの前景色(フォント色)です。
     */
    public static final Color FOREGROUND_COLOR = new Color(200, 200, 200);

    /**
     * 空のパネルを生成します
     */
    public TWPanel() {
        super();
        setBackground(BACKGROUND_COLOR);
        setForeground(FOREGROUND_COLOR);
    }

    /**
     * 指定したレイアウトマネージャーで空のパネルを生成します
     *
     * @param layoutManager パネルに指定するレイアウトマネージャー
     */
    public TWPanel(LayoutManager layoutManager) {
        this();
        setLayout(layoutManager);
    }
}
