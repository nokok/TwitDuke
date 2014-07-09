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
package net.nokok.twitduke.core.view.notifications;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.net.URL;
import net.nokok.twitduke.components.basics.TWFrame;
import net.nokok.twitduke.components.basics.TWTextArea;
import net.nokok.twitduke.core.view.async.UserIcon;

public class NotificationFrame extends TWFrame {

    public NotificationFrame() {
        this.setUndecorated(true);
        setMinimumSize(new Dimension(300, 75));
        setResizable(false);
        Dimension s = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((int) s.getWidth() - 320, 20);
        setAlwaysOnTop(true);
        NotificationTimer.INSTANCE.startNotification(() -> {
            this.setVisible(false);
            this.dispose();
        });
    }

    public NotificationFrame(String title, String description) {
        this();
        setLayout(new BorderLayout());
        add(TWTextArea.newNotEditableTextArea(title), BorderLayout.NORTH);
        add(TWTextArea.newNotEditableTextArea(description), BorderLayout.CENTER);
        setVisible(true);
    }

    public NotificationFrame(String title, String description, URL imageUrl) {
        this(title, description);
        UserIcon userIcon = new UserIcon(imageUrl);
        add(userIcon, BorderLayout.WEST);
        validate();
    }
}
