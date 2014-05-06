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
package net.nokok.twitduke.stream;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;

/**
 * StatusListenerで受信した内容を標準出力へ書き込みます。
 * 主にデバッグ用で使用します
 * <p>
 */
public class PrintStatusListener implements StatusListener {

    @Override
    public void onDeletionNotice(StatusDeletionNotice sdn) {
        System.out.println("onDeletionNotice: " + sdn);
    }

    @Override
    public void onException(Exception excptn) {
        System.out.println("onException: " + excptn);
    }

    @Override
    public void onScrubGeo(long l, long l1) {
        System.out.println("onScrubGeo: " + l + ":" + l1);
    }

    @Override
    public void onStallWarning(StallWarning sw) {
        System.out.println("onStallWarning: " + sw);
    }

    @Override
    public void onStatus(Status status) {
        System.out.println("onStatus: " + status.getText());
    }

    @Override
    public void onTrackLimitationNotice(int i) {
        System.out.println("onTrackLimitationNotice: " + i);
    }
}
