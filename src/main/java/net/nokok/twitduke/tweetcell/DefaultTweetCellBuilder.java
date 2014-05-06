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
package net.nokok.twitduke.tweetcell;

import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.ImageIcon;
import twitter4j.Status;
import twitter4j.User;

public class DefaultTweetCellBuilder {

    public DefaultTweetCell createDefaultTweetCell(Status status) {
        DefaultTweetCell tweetCell = new DefaultTweetCell();
        User user;
        String tweet;
        if ( status.isRetweet() ) {
            user = status.getRetweetedStatus().getUser();
            tweet = status.getRetweetedStatus().getText();
        } else {
            user = status.getUser();
            tweet = status.getText();
        }
        tweetCell.screenName.setText("@" + user.getScreenName());
        tweetCell.userName.setText(user.getName());
        tweetCell.textArea.setText(tweet);
        try {
            tweetCell.userIcon.setIcon(new ImageIcon(new URL(user.getProfileImageURLHttps())));
        } catch (MalformedURLException e) {
            throw new InternalError(e);
        }
        return tweetCell;
    }
}
