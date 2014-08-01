/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.nokok.twitduke.components.actions;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author wtnbsts
 */
public class Action_Paste implements EventHandler<KeyEvent> {

    @Override
    public void handle(KeyEvent event) {
        System.out.println("Event:" + this.getClass().getCanonicalName() + "[" + event.getCode() + "]");
    }
}
