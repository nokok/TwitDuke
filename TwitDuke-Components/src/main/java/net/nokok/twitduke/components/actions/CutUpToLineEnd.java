/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
