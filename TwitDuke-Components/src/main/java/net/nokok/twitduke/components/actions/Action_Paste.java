/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.nokok.twitduke.components.actions;

import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author wtnbsts
 */
public class Action_Paste implements EventHandler<KeyEvent> {

    @Override
    public void handle(KeyEvent event) {
        if ( !(event.getSource() instanceof TextArea) ) {
            return;
        }
        String buffer = Clipboard.getSystemClipboard().getString();
        if ( buffer.isEmpty() ) {
            return;
        }
        TextArea src = (TextArea) event.getSource();
        String txt = src.getText();
        int selectionStart = src.getSelection().getStart();
        int selectionEnd = src.getSelection().getEnd();
        StringBuilder sb = new StringBuilder(src.getText());
        sb.delete(selectionStart, selectionEnd);
        sb.insert(selectionStart, buffer);
        src.setText(sb.toString());
        src.selectRange(selectionStart + buffer.length(), selectionStart + buffer.length());
    }
}
