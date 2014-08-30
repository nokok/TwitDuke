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
package net.nokok.twitduke.core.twitter;

import java.io.IOException;
import java.io.UncheckedIOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import net.nokok.twitduke.components.javafx.ComponentAppendable;
import net.nokok.twitduke.core.type.ComponentInsertable;
import net.nokok.twitduke.core.view.tweetcell.DefaultTweetCell;
import net.nokok.twitduke.resources.FXMLResources;
import twitter4j.StatusListener;
import twitter4j.auth.AccessToken;

public class TwitterStreamRunner implements Runnable {

    private final LambdaTwitterStream lambdaTwitterStream;
    private final AccessToken accessToken;

    public TwitterStreamRunner(AccessToken accessToken) {
        this.accessToken = accessToken;
        lambdaTwitterStream = new LambdaTwitterStream(accessToken);
    }

    public void addTwitterListener(StatusListener statusListener) {
        lambdaTwitterStream.addListener(statusListener);
    }

    public void enableStackTrace() {
        lambdaTwitterStream.onException(e -> e.printStackTrace());
    }

    @Deprecated
    public void addHomeTimelineTweetCellListener(ComponentInsertable componentInsertable) {
        lambdaTwitterStream.onStatus(s -> {
            componentInsertable.insert(new DefaultTweetCell(s, accessToken));
        });
    }

    public void addHomeTimelineTweetCellListener(ComponentAppendable<? super Node> componentAppendable) {
        lambdaTwitterStream.onStatus(s -> {
            try {
                FXMLLoader loader = FXMLResources.TWEETCELL.loader();
                BorderPane pane = loader.load();
                componentAppendable.addComponent(pane);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        });
    }

    @Override
    public void run() {
        lambdaTwitterStream.startStream();
    }
}
