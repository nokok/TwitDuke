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
import java.awt.LayoutManager;
import net.nokok.twitduke.core.Selectable;

@Deprecated
public class SelectablePanel extends TWPanel implements Selectable {

    private static final long serialVersionUID = -5874949418945445316L;
    public static final Color SELECT_COLOR = new Color(41, 128, 185);

    private boolean isSelected;

    public SelectablePanel() {
    }

    public SelectablePanel(LayoutManager layoutManager) {
        super(layoutManager);
    }

    @Override
    public boolean isSelect() {
        return isSelected;
    }

    @Override
    public void select() {
        setBackground(SELECT_COLOR);
        isSelected = true;
    }

    @Override
    public void unselect() {
        setBackground(BACKGROUND_COLOR);
        isSelected = false;
    }
}
