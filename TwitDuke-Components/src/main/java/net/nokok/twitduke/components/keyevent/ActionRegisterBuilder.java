/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.nokok.twitduke.components.keyevent;

import java.util.Objects;
import javafx.scene.Node;

/**
 *
 * @author wtnbsts
 */
public class ActionRegisterBuilder {

    private final Node rootNode;

    public ActionRegisterBuilder(final Node rootNode) {
        this.rootNode = Objects.requireNonNull(rootNode);
    }

    public ActionRegister build() {
        return new JavaFXActionRegister(rootNode);
    }
}
