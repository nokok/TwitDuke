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
package net.nokok.twitduke.core.view.javafx;

import java.awt.Point;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Optional;
import java.util.function.BiConsumer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.nokok.twitduke.resources.FXMLResources;

public class ScreenShotAreaSelector {

    @FXML
    private BorderPane screenShotPane;

    private final Point start = new Point();
    private final Point end = new Point();
    private boolean isStarted = false;
    private Optional<BiConsumer<Point, Point>> selectedAreaReceiver = Optional.empty();
    private Stage stage;

    public ScreenShotAreaSelector() {
        FXMLLoader screenShotLoader = FXMLResources.SCREENSHOT_SELECTING_AREA.loader();
        try {
            BorderPane borderPane = screenShotLoader.load();
            stage = new Stage(StageStyle.TRANSPARENT);
            Scene scene = new Scene(borderPane, 0, 0);
            scene.setFill(null);
            stage.setScene(scene);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
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
        isStarted = false;
        selectedAreaReceiver.ifPresent(s -> {
            s.accept(start, end);
        });
    }

    public void areaSelected(BiConsumer<Point, Point> areaSelectedReceiver) {
        this.selectedAreaReceiver = Optional.of(areaSelectedReceiver);
    }

}
