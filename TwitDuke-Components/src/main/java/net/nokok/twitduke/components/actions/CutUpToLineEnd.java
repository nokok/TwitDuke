/*
 * The MIT License
 *
 * Copyright 2014 satanabe1.
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
package net.nokok.twitduke.components.actions;

import java.util.HashMap;
import java.util.Map;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.DataFormat;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author wtnbsts
 */
public class CutUpToLineEnd implements EventHandler<KeyEvent> {

    private String lastText;
    private int lastSelectionStart = -1;

    @Override
    public void handle(KeyEvent event) {
        if ( !(event.getSource() instanceof TextArea) ) {
            return;
        }
        final TextArea src = (TextArea) event.getSource();
        final String txt = src.getText();
        if ( txt.isEmpty() ) {
            return;
        }
        final int cursorPosition = src.getSelection().getStart();
        int lineEndPosition = ActionUtil.getEndOfLine(txt, cursorPosition);
        if ( lineEndPosition < 0 ) {
            lineEndPosition = txt.length();
        }
        String selected = txt.substring(cursorPosition, lineEndPosition);
        if ( selected.length() == 0 ) {
            selected = System.lineSeparator();
            lineEndPosition = cursorPosition + selected.length();
        }
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        if ( txt.equals(lastText)
             && lastSelectionStart == src.getSelection().getStart()
             && lastSelectionStart == src.getSelection().getEnd() ) {
            selected = clipboard.getString() + selected;
        }
        final Map<DataFormat, Object> data = new HashMap<>();
        data.put(DataFormat.PLAIN_TEXT, selected);
        clipboard.setContent(data);

        final StringBuilder sb = new StringBuilder(txt);
        sb.delete(cursorPosition, lineEndPosition);
        src.setText(sb.toString());
        src.selectRange(cursorPosition, cursorPosition);

        lastSelectionStart = cursorPosition;
        lastText = src.getText();
    }
}
