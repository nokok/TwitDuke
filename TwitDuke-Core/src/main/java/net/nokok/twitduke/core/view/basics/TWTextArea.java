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
package net.nokok.twitduke.core.view.basics;

import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

/**
 * TwitDuke用のカスタマイズ済みJTextAreaです
 *
 */
@Deprecated
public class TWTextArea extends JTextArea {

    private static final long serialVersionUID = 3723800077851443280L;

    /**
     * テキストエリアの背景色です。
     */
    public static final Color BACKGROUND_COLOR = new Color(40, 40, 40);
    /**
     * テキストエリアの前景色(フォント色)です。
     */
    public static final Color FOREGROUND_COLOR = new Color(200, 200, 200);

    /**
     * 空のカスタマイズ済みのテキストエリアを生成します
     */
    public TWTextArea() {
        setBackground(BACKGROUND_COLOR);
        setForeground(FOREGROUND_COLOR);
        super.setCaretColor(Color.WHITE);
        setLineWrap(true);
        setWrapStyleWord(true);
        setOpaque(true);
        setBorder(new EmptyBorder(2, 0, 5, 0));
    }

    /**
     * 指定したテキストでカスタマイズ済みのテキストエリアを生成します
     *
     * @param text
     */
    public TWTextArea(String text) {
        this();
        setText(text);
    }

    /**
     * setEditable(false)がセットされたテキストエリアを生成します。
     * ただし、Swingの制約上イミュータブルなテキストエリアではありません。
     *
     * このメソッドを用いてテキストエリアを生成したとしてもsetEditable(true)を呼び出すと
     * 編集可能になります。
     *
     * このメソッドは編集する必要のないテキストエリアを生成する必要がある時、
     * setEditableメソッドの呼び出しを省略する為に使用します。
     *
     * @param text 生成するテキストエリアにセットするテキスト
     *
     * @return テキストがセットされた編集出来ないテキストエリア
     */
    public static JTextArea newNotEditableTextArea(String text) {
        JTextArea textArea = new TWTextArea(text);
        textArea.setEditable(false);
        return textArea;
    }
}
