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
package net.nokok.twitduke.components.tweetcell;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import net.nokok.twitduke.components.basic.TWButton;

/**
 * リツイートボタンです
 *
 */
public class RetweetButton extends TWButton {

    private static final long serialVersionUID = 2349270506630754751L;
    private boolean isSelected;

    /**
     * リツイートしていないボタンの背景色です
     */
    public static final Color DEFAULT_BACKGROUND_COLOR = new Color(0, 100, 0);

    /**
     * リツイート済みのボタンの背景色です
     */
    public static final Color RETWEETED_BACKGROUND_COLOR = new Color(50, 200, 50);

    /**
     * リツイートボタンを生成します
     */
    public RetweetButton() {
        setBackground(DEFAULT_BACKGROUND_COLOR);
        setPreferredSize(new Dimension(20, 10));
        setMargin(new Insets(0, 0, 0, 0));
    }

    /**
     * ボタンをリツイート済み状態にします
     */
    @Override
    public void select() {
        setBackground(RETWEETED_BACKGROUND_COLOR);
        isSelected = true;
    }

    /**
     * ボタンを通常状態にします
     */
    @Override
    public void unselect() {
        setBackground(DEFAULT_BACKGROUND_COLOR);
        isSelected = false;
    }

    @Override
    public boolean isSelect() {
        return isSelected;
    }

}
