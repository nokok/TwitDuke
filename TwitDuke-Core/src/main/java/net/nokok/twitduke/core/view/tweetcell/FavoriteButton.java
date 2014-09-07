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
package net.nokok.twitduke.core.view.tweetcell;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import net.nokok.twitduke.core.view.basics.TWButton;

/**
 * お気に入りボタンです
 *
 */
public class FavoriteButton extends TWButton {

    private static final long serialVersionUID = 2609656566714323682L;
    private boolean isSelected;

    /**
     * まだお気に入りにしていないボタンの背景色です
     */
    public static final Color DEFAULT_BACKGROUND_COLOR = new Color(180, 180, 0);

    /**
     * お気に入り済みのボタンの背景色です
     */
    public static final Color FAVORITED_BACKGROUND_COLOR = Color.YELLOW;

    /**
     * お気に入りボタンを生成します
     */
    public FavoriteButton() {
        setBackground(DEFAULT_BACKGROUND_COLOR);
        setPreferredSize(new Dimension(20, 10));
        setMargin(new Insets(0, 0, 0, 0));
    }

    /**
     * ボタンをお気に入り済み状態にします
     */
    @Override
    public void select() {
        setBackground(FAVORITED_BACKGROUND_COLOR);
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
