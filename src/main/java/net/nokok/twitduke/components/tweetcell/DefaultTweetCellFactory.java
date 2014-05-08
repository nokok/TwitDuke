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

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JPanel;
import net.nokok.twitduke.components.basic.TWPanel;
import net.nokok.twitduke.tweetcell.TweetPanelFactory;
import twitter4j.Status;
import twitter4j.auth.AccessToken;

/**
 * 標準サイズのツイートを生成するクラスです。
 */
public class DefaultTweetCellFactory {

    private final TweetPanelFactory panelFactory;

    /**
     * 指定されたツイートのステータスとアクセストークンを使用してファクトリーを生成します。
     *
     * @param status      ツイートのステータス
     * @param accessToken 有効なアクセストークン
     */
    public DefaultTweetCellFactory(Status status, AccessToken accessToken) {
        panelFactory = new TweetPanelFactory(status, accessToken);
    }

    /**
     * 標準サイズのツイートセルを生成します
     *
     * @return 標準サイズのツイートセル
     */
    public JPanel newPanel() {
        JPanel p = new TWPanel(new BorderLayout());
        p.add(panelFactory.createUserIcon(), BorderLayout.WEST);
        JPanel tweetContent = new TWPanel(new BorderLayout());
        JPanel northPanel = new TWPanel(new FlowLayout(FlowLayout.LEFT));
        northPanel.add(panelFactory.createUserNameLabel());
        northPanel.add(panelFactory.createScreenNameLabel());
        tweetContent.add(northPanel, BorderLayout.NORTH);
        tweetContent.add(panelFactory.createTweetTextArea(), BorderLayout.CENTER);
        tweetContent.add(panelFactory.createTimeLabel(), BorderLayout.EAST);
        p.add(tweetContent, BorderLayout.CENTER);
        JPanel southPanel = new TWPanel(new FlowLayout(FlowLayout.LEFT, 2, 1));
        southPanel.add(panelFactory.createFavoriteButton());
        southPanel.add(panelFactory.createRetweetButton());
        p.add(southPanel, BorderLayout.SOUTH);
        return p;
    }
}
