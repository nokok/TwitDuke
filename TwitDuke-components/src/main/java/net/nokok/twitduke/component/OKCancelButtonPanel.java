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
package net.nokok.twitduke.component;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import net.nokok.twitduke.component.basic.TWButton;
import net.nokok.twitduke.component.basic.TWPanel;

/**
 * OKボタンとキャンセルボタンが2つ横方向に並んだパネルです。
 * OKが左側、キャンセルが右側にあります。
 * <p>
 * @author noko <nokok.kz at gmail.com>
 */
public class OKCancelButtonPanel extends TWPanel {

    private static final long serialVersionUID = -6417410274474706405L;

    private final JButton okButton = new TWButton("OK");
    private final JButton cancelButton = new TWButton("Cancel");

    public OKCancelButtonPanel() {
        setLayout(new FlowLayout());
        add(okButton);
        add(cancelButton);
    }

    public void addOKButtonAction(ActionListener actionListener) {
        okButton.addActionListener(actionListener);
    }

    public void addCancelButtonAction(ActionListener actionListener) {
        cancelButton.addActionListener(actionListener);
    }
}
