/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.nokok.twitduke.components.actions;

import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author wtnbsts
 */
public class MoveToEndOfLine implements EventHandler<KeyEvent> {

    @Override
    public void handle(KeyEvent event) {
        if ( !(event.getSource() instanceof TextArea) ) {
            return;
        }
        final TextArea src = (TextArea) event.getSource();
        src.positionCaret(ActionUtil.getEndOfLine(src.getText(), src.getCaretPosition()));
    }
}
