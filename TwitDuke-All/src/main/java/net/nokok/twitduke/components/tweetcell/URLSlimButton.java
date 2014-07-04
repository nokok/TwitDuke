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
package net.nokok.twitduke.components.tweetcell;

import java.awt.Color;
import java.awt.Desktop;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import net.nokok.twitduke.components.basics.TWSlimButton;
import twitter4j.URLEntity;

public class URLSlimButton extends TWSlimButton {

    private static final long serialVersionUID = 3697076660756865942L;

    public static final Color URL_BACKGROUND_COLOR = new Color(44, 62, 80);
    public static final Color URL_FOREGROUND_COLOR = new Color(236, 240, 241);

    public URLSlimButton(String text) {
        setText(text);
        setBackground(URL_BACKGROUND_COLOR);
        setForeground(URL_FOREGROUND_COLOR);
        try {
            URL url = new URL(text);
            addActionListener(e -> {
                openInBrowser(url);
                setText("再度開く");
            });
        } catch (MalformedURLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public URLSlimButton(URLEntity entity) {
        this(entity.getExpandedURL());
        setText(entity.getDisplayURL());
    }

    private void openInBrowser(URL url) {
        try {
            Desktop.getDesktop().browse(url.toURI());
        } catch (URISyntaxException | IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
