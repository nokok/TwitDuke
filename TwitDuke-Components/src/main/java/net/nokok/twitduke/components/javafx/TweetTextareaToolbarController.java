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

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;
import net.nokok.twitduke.resources.FXMLResources;

public class TweetTextareaToolbarController implements ComponentAppendable<Node> {

    @FXML
    private Button takeScreenshotButton;

    @FXML
    private Label tweetTextLengthLabel;

    @FXML
    private Button choosePictureButton;

    @FXML
    private ToolBar tweetTextareaToolbar;

    @FXML
    private Button saveDraftButton;

    @Override
    public void addComponent(Node component) {

    }

    @FXML
    void takeScreenshot(ActionEvent event) {
        Stage stage = new Stage(StageStyle.TRANSPARENT);
        FXMLLoader loader = new FXMLLoader(FXMLResources.SCREENSHOT.orElseThrow(RuntimeException::new));
        try {
            BorderPane root = loader.load();
            TakeScreenShotController controller = loader.getController();
            controller.areaSelected((start, end) -> {
                try {
                    Robot robot = new Robot();
                    stage.close();
                    BufferedImage capturedImage = robot.createScreenCapture(new Rectangle(start.x, start.y, end.x - start.x, end.y - start.y));
                    ImageIO.write(capturedImage, "png", new File("capture.png"));
                } catch (AWTException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            });
            Dimension screenSize = getScreenSize();
            Scene scene = new Scene(root, screenSize.width, screenSize.height);
            scene.setFill(null);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @FXML
    void choosePicture(ActionEvent event) {

    }

    @FXML
    void saveDraft(ActionEvent event) {

    }

    private Dimension getScreenSize() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }

}
