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
import java.util.function.BiConsumer;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class TakeScreenShotController {

    @FXML
    private BorderPane screenShotPane;

    private Point start;
    private Point end;
    private boolean isStarted = false;
    private BiConsumer<Point, Point> selectedAreaReceiver;

    @FXML
    void onMouseDragged(MouseEvent event) {
        if ( !isStarted ) {
            System.out.println("started" + event.getScreenX() + "," + event.getScreenY());
            start = new Point((int) event.getScreenX(), (int) event.getScreenY());
            isStarted = true;
        }
    }

    @FXML
    void onMouseReleased(MouseEvent event) {
        System.out.println("released" + event.getScreenX() + "," + event.getScreenY());
        end = new Point((int) event.getScreenX(), (int) event.getScreenY());
        selectedAreaReceiver.accept(start, end);
    }

    public void areaSelected(BiConsumer<Point, Point> areaSelectedReceiver) {
        this.selectedAreaReceiver = areaSelectedReceiver;
    }

}
