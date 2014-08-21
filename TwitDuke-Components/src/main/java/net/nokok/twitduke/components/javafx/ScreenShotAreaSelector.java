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

import java.awt.Point;
import java.util.Objects;
import java.util.function.BiConsumer;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.nokok.twitduke.base.type.FXMLResource;
import net.nokok.twitduke.resources.FXMLResources;

public class ScreenShotAreaSelector {

    @FXML
    private BorderPane screenShotPane;

    private final Point start = new Point(0, 0);
    private final Point end = new Point();
    private boolean isStarted = false;
    private BiConsumer<Point, Point> selectedAreaReceiver;
    private Stage stage;

    public ScreenShotAreaSelector() {
        FXMLResource resource = FXMLResources.SCREENSHOT_SELECTING_AREA;
        BorderPane borderPane = resource.resource(BorderPane.class).get();
        stage = new Stage(StageStyle.TRANSPARENT);
        Scene scene = new Scene(borderPane, 0, 0);
        scene.setFill(null);
        stage.setScene(scene);
    }

    @FXML
    void onMouseDragged(MouseEvent event) {
        stage.setWidth(event.getX() - stage.getX());
        stage.setHeight(event.getScreenY() - stage.getY());
        if ( isStarted ) {
            return;
        }
        start.setLocation(event.getScreenX(), event.getScreenY());
        stage.setX(start.x);
        stage.setY(start.y);
        stage.show();
        isStarted = true;
    }

    @FXML
    void onMouseReleased(MouseEvent event) {
        end.setLocation(event.getScreenX(), event.getScreenY());
        stage.close();
        if ( selectedAreaReceiver == null ) {
            throw new NullPointerException("レシーバが指定されていません。areaSelectedでレシーバを指定してください");
        }
        selectedAreaReceiver.accept(start, end);
    }

    public void areaSelected(BiConsumer<Point, Point> areaSelectedReceiver) {
        this.selectedAreaReceiver = Objects.requireNonNull(areaSelectedReceiver);
    }

}
