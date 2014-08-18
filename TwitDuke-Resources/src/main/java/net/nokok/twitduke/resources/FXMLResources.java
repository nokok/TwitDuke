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
package net.nokok.twitduke.resources;

import com.google.common.io.Resources;
import java.io.File;
import java.net.URL;
import java.util.Optional;

public class FXMLResources {

    public static final String MAIN_FXML_FILE_NAME = "main.fxml";
    public static final Optional<URL> MAIN_FXML = findFXMLResources(MAIN_FXML_FILE_NAME);

    public static final String TWEETCELL_FXML_FILE_NAME = "tweetcell.fxml";
    public static final Optional<URL> TWEETCELL_FXML = findFXMLResources(TWEETCELL_FXML_FILE_NAME);

    public static final String TWEET_TEXTAREA_FILE_NAME = "tweetTextArea.fxml";
    public static final Optional<URL> TWEET_TEXTAREA = findFXMLResources(TWEET_TEXTAREA_FILE_NAME);

    public static final String TWEET_TEXTAREA_TOOLBAR_FILE_NAME = "tweetTextAreaToolbar.fxml";
    public static final Optional<URL> TWEET_TEXTAREA_TOOLBAR = findFXMLResources(TWEET_TEXTAREA_TOOLBAR_FILE_NAME);

    public static final String TAKE_SCREENSHOT_FILE_NAME = "screenShot.fxml";
    public static final Optional<URL> TAKE_SCREENSHOT = findFXMLResources(TAKE_SCREENSHOT_FILE_NAME);

    public static final String SCREENSHOT_SELECTING_AREA_FILE_NAME = "screenShotSelectingArea.fxml";
    public static final Optional<URL> SCREENSHOT_SELECTING_AREA = findFXMLResources(SCREENSHOT_SELECTING_AREA_FILE_NAME);

    private static Optional<URL> findFXMLResources(String resource) {
        return Optional.ofNullable(Resources.getResource(String.join(File.separator, "fxml", resource)));
    }
}
