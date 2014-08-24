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
package net.nokok.twitduke.base.type;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

public class FXMLResource {

    private final FXMLLoader loader;

    public FXMLResource(URL url) {
        this.loader = new FXMLLoader(url);
    }

    public FXMLResource(Optional<URL> url) {
        this(url.get());
    }

    public <T> Optional<T> getController(Class<T> clazz) {
        return Optional.of(clazz.cast(loader.getController()));
    }

    public <R extends Node> Optional<R> resource(Class<R> clazz) {
        try {
            return Optional.of(clazz.cast(loader.load()));
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    public FXMLLoader loader() {
        return loader;
    }
}
