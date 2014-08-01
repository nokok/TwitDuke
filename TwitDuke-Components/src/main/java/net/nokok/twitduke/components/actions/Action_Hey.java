/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author wtnbsts
 */
public class Action_Hey implements EventHandler<KeyEvent> {

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

        dialog.setResizable(false);
        dialog.initModality(Modality.WINDOW_MODAL);

        final StackPane pane = new StackPane();
        dialog.setScene(new Scene(pane));

        {
            final VBox vbox = new VBox();
            pane.getChildren().add(vbox);

            vbox.setAlignment(Pos.CENTER);
            vbox.setMinWidth(300);

            vbox.getChildren().add(new Label(""));// 空行
            vbox.getChildren().add(new Label("hey! ﾂﾗﾀﾝ..."));
            vbox.getChildren().add(new Label(""));// 空行

            final Button btnOk = new Button();
            btnOk.setText("OK");
            btnOk.setOnAction(boxEvent -> {
                dialog.close();
            });
            vbox.getChildren().add(btnOk);
            vbox.getChildren().add(new Label(""));// 空行

        }

        dialog.showAndWait();
        lastCommandTime = System.currentTimeMillis();
    }
}
