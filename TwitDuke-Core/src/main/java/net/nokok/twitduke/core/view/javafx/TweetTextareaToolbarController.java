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

import com.google.common.io.Files;
import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Objects;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;
import net.nokok.twitduke.core.event.Event;
import net.nokok.twitduke.core.type.Retrievable;
import net.nokok.twitduke.core.type.TweetLength;
import net.nokok.twitduke.resources.FXMLResources;
import net.nokok.twitduke.resources.ImageResources;
import net.nokok.twitduke.resources.draft.DraftIO;
import net.nokok.twitduke.resources.draft.DraftIOFactory;

public class TweetTextareaToolbarController implements ComponentAppendable<Node>, Event<TweetLength> {

    @FXML
    private Label tweetTextLengthLabel;

    @FXML
    private ToolBar tweetTextareaToolbar;

    @FXML
    private ImageView draftButtonIcon;

    private Retrievable<String> textAreaStringReceiver;

    private TweetTextareaController tweetTextareaController;

    private final DraftIO draftIO = DraftIOFactory.newInstance();

    @Override
    public void addComponent(Node component) {
        tweetTextareaToolbar.getItems().add(component);
    }

    @Override
    public void onEvent(TweetLength t) {
        tweetTextLengthLabel.setText(String.valueOf(140 - t.length()));
        if ( t.isSendable() ) {
            tweetTextLengthLabel.setStyle("-fx-text-fill: #ecf0f1;"); //default.css normal-text-color
        } else {
            tweetTextLengthLabel.setStyle("-fx-text-fill: #c0392b;"); //default.css error-text-color
        }
    }

    @FXML
    void takeScreenshot(ActionEvent event) throws IOException {
        Stage stage = new Stage(StageStyle.TRANSPARENT);
        FXMLLoader screenShotLoader = FXMLResources.TAKE_SCREENSHOT.loader();
        BorderPane root = screenShotLoader.load();
        ScreenShotAreaSelector controller = screenShotLoader.getController();
        controller.areaSelected((start, end) -> {
            stage.close();
            BufferedImage image = takeScreenShot(start, end);
            saveImage("tmp.png", image);
        });
        Dimension screenSize = getScreenSize();
        Scene scene = new Scene(root, screenSize.width, screenSize.height);
        scene.setFill(null);
        stage.setScene(scene);
        stage.show();
    }

    private BufferedImage takeScreenShot(Point start, Point end) {
        int height = end.x - start.x;
        int width = end.y - start.y;
        if ( height < 0 ) {
            int x = start.x;
            start.x = end.x;
            end.x = x;
        }
        if ( width < 0 ) {
            int y = start.y;
            start.y = end.y;
            end.y = y;
        }
        try {
            Robot robot = new Robot();
            BufferedImage capturedImage = robot.createScreenCapture(new Rectangle(start.x, start.y, end.x - start.x, end.y - start.y));
            return capturedImage;
        } catch (AWTException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void saveImage(String fileName, BufferedImage image) {
        try {
            ImageIO.write(image, "png", new File(fileName));
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    @FXML
    void choosePicture(ActionEvent event) {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("画像を選択してください");
        File file = fileChooser.showOpenDialog(stage);
        try {
            Files.copy(file, new File("tmp.png"));
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    @FXML
    void saveDraft(ActionEvent event) {
        String text = textAreaStringReceiver.get();
        draftIO.saveDraft(text);
        if ( draftIO.draftList().isEmpty() ) {
            draftButtonIcon.setImage(ImageResources.DRAFT_EMPTY);
        } else {
            draftButtonIcon.setImage(ImageResources.DRAFT_FULL);
        }
    }

    public void setSaveDraftButtonListener(Retrievable<String> textAreaStringReceiver) {
        this.textAreaStringReceiver = Objects.requireNonNull(textAreaStringReceiver);
    }

    private Dimension getScreenSize() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }

    public void addTweetTextAreaController(TweetTextareaController controller) {
        this.tweetTextareaController = Objects.requireNonNull(controller);
        tweetTextareaController.onInput(this::onEvent);
    }

}
