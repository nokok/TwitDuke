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
package net.nokok.twitduke.core.view.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import net.nokok.twitduke.components.OKCancelButtonPanel;
import net.nokok.twitduke.components.basics.TWFrame;
import net.nokok.twitduke.components.basics.TWLabel;
import net.nokok.twitduke.components.basics.TWPanel;
import net.nokok.twitduke.components.basics.TWTextField;

public class TextFieldDialog implements Dialog<String> {

    private final JFrame frame;
    private final OKCancelButtonPanel okCancelPanel = new OKCancelButtonPanel();
    private DialogResultListener<String> dialogResultListener;

    public TextFieldDialog(String title) {
        this(title, "");
    }

    public TextFieldDialog(String title, String description) {
        this.frame = new TWFrame(title);
        JTextField textField = new TWTextField();
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setBorder(new LineBorder(new Color(200, 200, 200)));
        frame.setLayout(new BorderLayout());
        frame.add(createNorthPanel(description), BorderLayout.NORTH);
        frame.add(textField, BorderLayout.CENTER);
        frame.add(okCancelPanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setLocationRelativeTo(null);
        okCancelPanel.addOKButtonAction(e -> {
            dialogResultListener.okButtonPushed(textField.getText());
        });
        okCancelPanel.addCancelButtonAction(e
            -> dialogResultListener.cancelButtonPushed());
    }

    private JPanel createNorthPanel(String desc) {
        JPanel panel = new TWPanel();
        panel.add(new TWLabel(desc));
        return panel;
    }

    @Override
    public void dispose() {
        frame.setVisible(false);
        frame.dispose();
    }

    @Override
    public void setDialogResultListener(DialogResultListener<String> dialogResultListener) {
        this.dialogResultListener = dialogResultListener;
    }

    @Override
    public void show() {
        frame.setVisible(true);
    }

}
