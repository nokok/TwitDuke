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
package net.nokok.twitduke.components.javafx;

import java.util.Objects;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToolBar;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

public class MainViewController {

    @FXML
    private Button functionTileButton;

    @FXML
    private ToolBar rootToolbar;

    @FXML
    private BorderPane textAreaBorderPane;

    @FXML
    private Button replyButton;

    @FXML
    private Tab primaryAccountTab;

    @FXML
    private Button configButton;

    @FXML
    private TextArea tweetTextarea;

    @FXML
    private TabPane mainView;

    @FXML
    private Button listButton;

    @FXML
    private ListView<?> tweetCellList;

    @FXML
    private Button homeButton;

    @FXML
    void showConfig(ActionEvent event) {

    }

    @FXML
    void showHome(ActionEvent event) {

    }

    @FXML
    void showReply(ActionEvent event) {

    }

    @FXML
    void showFunctionTile(ActionEvent event) {

    }

    @FXML
    void tweetTextAreaOnKeyPressed(KeyEvent keyEvent) {
        applyTweetLength();
    }

    @FXML
    void tweetTextAreaOnTextChanged(InputMethodEvent event) {
        applyTweetLength();
    }

    @FXML
    void tweetTextAreaOnKeyTyped(KeyEvent event) {
        applyTweetLength();
    }

    @FXML
    void tweetTextAreaOnKeyReleased(KeyEvent event) {
        applyTweetLength();
    }

    private void applyTweetLength() {
        //tweetLengthLabel.setText(String.valueOf(140 - tweetTextarea.getText().length()));
    }

    public void setTweetTextAreaToolbar(BorderPane textAreaToolbar) {
        Objects.requireNonNull(textAreaToolbar, "渡されたツールバーがnullです");
        this.textAreaBorderPane.setBottom(textAreaToolbar);
    }

    public void setTweetTextArea(BorderPane textArea) {
        this.textAreaBorderPane.setCenter(textArea);
    }

    @FXML
    void initialize() {
        assert functionTileButton != null : "fx:id=\"functionTileButton\" was not injected: check your FXML file 'main.fxml'.";
        assert replyButton != null : "fx:id=\"replyButton\" was not injected: check your FXML file 'main.fxml'.";
        assert configButton != null : "fx:id=\"configButton\" was not injected: check your FXML file 'main.fxml'.";
        assert homeButton != null : "fx:id=\"homeButton\" was not injected: check your FXML file 'main.fxml'.";
        assert rootToolbar != null : "fx:id=\"rootToolbar\" was not injected: check your FXML file 'main.fxml'.";
        assert textAreaBorderPane != null : "fx:id=\"textAreaToolbar\" was not injected: check your FXML file 'main.fxml'.";
        assert primaryAccountTab != null : "fx:id=\"primaryAccountTab\" was not injected: check your FXML file 'main.fxml'.";
        assert tweetTextarea != null : "fx:id=\"tweetTextarea\" was not injected: check your FXML file 'main.fxml'.";
        assert mainView != null : "fx:id=\"mainView\" was not injected: check your FXML file 'main.fxml'.";
        assert listButton != null : "fx:id=\"listButton\" was not injected: check your FXML file 'main.fxml'.";
        assert tweetCellList != null : "fx:id=\"tweetCellList\" was not injected: check your FXML file 'main.fxml'.";

    }
}
