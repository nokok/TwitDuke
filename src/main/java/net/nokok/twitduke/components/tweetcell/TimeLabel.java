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

import java.awt.Dimension;
import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;
import net.nokok.twitduke.components.basic.TWLabel;

/**
 * ツイートされた時刻から現在時刻までの相対時間をカウントアップするラベルです。
 *
 * @author noko
 */
public class TimeLabel extends TWLabel {

    private static final long serialVersionUID = 4056849570457615755L;

    private final LocalDateTime statusTime;

    /**
     * 指定された時刻から相対時間を計算してカウントアップするラベルを生成します
     *
     * @param statusTime ツイートが投稿された時刻
     */
    public TimeLabel(LocalDateTime statusTime) {
        this.statusTime = statusTime;
        setPreferredSize(new Dimension(50, 30));
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                setText(calcDifference());
            }
        }, 0, 5000);
    }

    private String calcDifference() {
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear() - statusTime.getYear();
        if ( year == 0 ) {
            //現在日時と同じ年につぶやかれたツイート
            int dayOfYear = now.getDayOfYear() - statusTime.getDayOfYear();
            if ( dayOfYear == 0 ) {
                //今日つぶやかれたツイート
                int hour = now.getHour() - statusTime.getHour();
                if ( hour == 0 ) {
                    //現在時刻と同じ時間につぶやかれたツイート
                    int minue = now.getMinute() - statusTime.getMinute();
                    if ( minue == 0 ) {
                        //現在時刻と同じ分につぶやかれたツイート
                        return (now.getSecond() - statusTime.getSecond()) + "秒前";
                    } else {
                        return minue + "分前";
                    }
                } else {
                    return hour + "時間前";
                }
            } else {
                return dayOfYear + "日前";
            }
        } else {
            return year + "年前";
        }
    }
}
