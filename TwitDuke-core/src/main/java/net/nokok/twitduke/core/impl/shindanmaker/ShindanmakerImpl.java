/*
 * The MIT License
 *
 * Copyright 2014 noko <nokok.kz at gmail.com>.
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
package net.nokok.twitduke.core.impl.shindanmaker;

import java.io.IOException;
import net.nokok.twitduke.core.api.shindanmaker.Shindanmaker;
import net.nokok.twitduke.core.api.shindanmaker.ShindanmakerListener;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * 2014/4/30現在の仕様の診断メーカーで自動診断を実装したクラスです
 * <p>
 * @author noko <nokok.kz at gmail.com>
 */
public class ShindanmakerImpl implements Shindanmaker {

    private ShindanmakerListener listener;

    @Override
    public void setListener(ShindanmakerListener listener) {
        this.listener = listener;
    }

    @Override
    public void sendRequest(String shindanmakerURL, String name) {
        try {
            Document doc = Jsoup.connect(shindanmakerURL).data("u", name).post();
            Elements textArea = doc.select("textarea");
            listener.success(textArea.val());
        } catch (IOException ex) {
            listener.error();
        }
    }
}
