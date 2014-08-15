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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.DataFormat;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author wtnbsts
 */
public class CheckKeyEvent implements EventHandler<KeyEvent> {

    private final static String WITH = "+";
    private long lastCommandTime = System.currentTimeMillis();

    @SuppressWarnings("unused")
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

        vbox.setMinSize(200, 100);
        vbox.setMaxSize(200, 100);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().add(new Label());
        vbox.getChildren().add(new Label());

        final TextArea textarea = new TextArea("input any key...");
        vbox.getChildren().add(1, textarea);
        textarea.setEditable(false);
        textarea.selectForward();
        textarea.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            List<KeyCode> keys = new ArrayList<>();
            if ( keyEvent.isAltDown() ) {
                keys.add(KeyCode.ALT);
            }
            if ( keyEvent.isControlDown() ) {
                keys.add(KeyCode.CONTROL);
            }
            if ( keyEvent.isMetaDown() ) {
                keys.add(KeyCode.META);
            }
            if ( keyEvent.isShiftDown() ) {
                keys.add(KeyCode.SHIFT);
            }
            if ( keyEvent.isShortcutDown() ) {
                keys.add(KeyCode.SHORTCUT);
            }
            if ( !keys.contains(keyEvent.getCode()) ) {
                keys.add(keyEvent.getCode());
            }
            StringBuilder sb = new StringBuilder();
            keys.forEach(code -> {
                if ( sb.length() > 0 ) {
                    sb.append(WITH);
                }
                sb.append(code.getName());
            });
            textarea.setText(sb.toString());
        });

        final Button copyButton = new Button();
        copyButton.setText("Copy");
        copyButton.setOnAction(boxEvent -> {
            Map<DataFormat, Object> data = new HashMap<>();
            data.put(DataFormat.PLAIN_TEXT, textarea.getText());
            Clipboard.getSystemClipboard().setContent(data);
        });
        final Button closeButton = new Button();
        closeButton.setText("Close");
        closeButton.setOnAction(boxEvent -> {
            dialog.close();
        });

        final HBox buttonPane = new HBox();
        buttonPane.setAlignment(Pos.CENTER);
        buttonPane.getChildren().add(copyButton);
        buttonPane.getChildren().add(new Label("     "));
        buttonPane.getChildren().add(closeButton);

        vbox.getChildren().add(buttonPane);
        vbox.getChildren().add(new Label(""));
        dialog.showAndWait();
        lastCommandTime = System.currentTimeMillis();
    }
}
