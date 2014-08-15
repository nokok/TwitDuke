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

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author wtnbsts
 */
public class Hey implements EventHandler<KeyEvent> {

    private long lastCommandTime = System.currentTimeMillis();

    @Override
    public void handle(KeyEvent event) {
        if ( !(event.getSource() instanceof TextArea) ) {
            return;
        }
        if ( System.currentTimeMillis() - lastCommandTime < 200 ) {
            return;
        }
        final Stage dialog = new Stage();
        final StackPane pane = new StackPane();
        dialog.setScene(new Scene(pane));
        final VBox vbox = new VBox();
        pane.getChildren().add(vbox);

        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().add(new Label());
        vbox.getChildren().add(new Label("   hey! ﾂﾗﾀﾝ...   "));
        vbox.getChildren().add(new Label());
        final Button bottom = new Button();
        bottom.setText("OK");
        bottom.setOnAction(boxEvent -> {
            dialog.close();
        });
        vbox.getChildren().add(bottom);
        vbox.getChildren().add(new Label(""));
        dialog.showAndWait();
        lastCommandTime = System.currentTimeMillis();
    }
}
