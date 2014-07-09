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
package net.nokok.twitduke.core.view.async;

import java.awt.Dimension;
import java.awt.Image;
import java.util.Optional;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.OverlayLayout;
import net.nokok.twitduke.components.basics.TWPanel;
import twitter4j.Status;

/**
 * RTしたユーザーとされたユーザーのアイコンを2つ表示するコンポーネントです
 *
 */
public class OverlayUserIcon extends TWPanel {

    private static final long serialVersionUID = 2484721633191391187L;

    private volatile Image smallIcon;

    public OverlayUserIcon(Status status) {
        setPreferredSize(new Dimension(50, 50));
        Status retweetedStatus = Optional.ofNullable(status.getRetweetedStatus()).orElseThrow(IllegalArgumentException::new);
        AsyncImageLoader userIconTask = new AsyncImageLoader(getProfileImageUrl(status));
        AsyncImageLoader rtIconTask = new AsyncImageLoader(getProfileImageUrl(retweetedStatus));
        setLayout(new OverlayLayout(this));
        userIconTask.onSuccess(icon -> {
            Image s = icon.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
            smallIcon = s;
        });
        rtIconTask.onSuccess(icon -> {
            Image s = icon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
            while ( smallIcon == null ) {
                //小さいアイコンが先に読まれてaddされると重なって見えなくなるためここで待つ
            }
            add(new JLabel(new ImageIcon(smallIcon)));
            add(new JLabel(new ImageIcon(s)));
        });
        UIAsyncTaskPool.INSTANCE.runTask(userIconTask);
        UIAsyncTaskPool.INSTANCE.runTask(rtIconTask);
    }

    private String getProfileImageUrl(Status status) {
        return status.getUser().getProfileImageURLHttps();
    }
}
