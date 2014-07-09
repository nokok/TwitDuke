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
package net.nokok.twitduke.core.view.tweetcell;

import java.util.Timer;
import java.util.TimerTask;

/**
 * TweetCell用のタイマーです。enumでシングルトンパターンとして実装されています。
 * このオブジェクトでセル毎にスレットが生成され、パフォーマンスが低下するのを防ぎます。
 */
enum TweetCellTimer {

    INSTANCE,;

    private final Timer timer = new Timer();

    /**
     * 指定したタスクを指定した間隔で実行します
     *
     * @param task  指定した間隔で実行するタスク
     * @param delay 間隔(ms)
     */
    void scheduleAtFixedRate(TimerTask task, long delay) {
        timer.scheduleAtFixedRate(task, 0, delay);
    }
}
