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
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.JPanel;
import net.nokok.twitduke.components.basic.TWPanel;
import twitter4j.Status;
import twitter4j.auth.AccessToken;

/**
 * 標準サイズのツイートを生成するクラスです。
 */
public class DefaultTweetCell extends TWPanel {

    private static final long serialVersionUID = 2533161383478118204L;

    private final TweetPanelFactory panelFactory;

    /**
     * 指定されたツイートのステータスとアクセストークンを使用してファクトリーを生成します。
     *
     * @param status      ツイートのステータス
     * @param accessToken 有効なアクセストークン
     */
    public DefaultTweetCell(Status status, AccessToken accessToken) {
        panelFactory = new TweetPanelFactoryImpl(status, accessToken);
        setLayout(new BorderLayout());
        add(panelFactory.createUserIcon(), BorderLayout.WEST);
        JPanel contentPanel = new TWPanel(new BorderLayout());
        JPanel userInfoPanel = new TWPanel(new FlowLayout(FlowLayout.LEFT));
        userInfoPanel.add(panelFactory.createFavoriteButton());
        userInfoPanel.add(panelFactory.createRetweetButton());
        userInfoPanel.add(panelFactory.createUserNameLabel());
        userInfoPanel.add(panelFactory.createScreenNameLabel());
        userInfoPanel.add(panelFactory.createTimeLabel());
        userInfoPanel.setPreferredSize(new Dimension(-1, 30));
        contentPanel.add(userInfoPanel, BorderLayout.NORTH);
        contentPanel.add(panelFactory.createTweetTextArea(), BorderLayout.CENTER);
        JPanel thumbnailPanel = new TWPanel(new FlowLayout(FlowLayout.CENTER));
        List<Component> thumbnails = panelFactory.createThumbnailList();
        thumbnails.forEach(t -> thumbnailPanel.add(t, BorderLayout.CENTER));
        contentPanel.add(thumbnailPanel, BorderLayout.SOUTH);
        add(contentPanel, BorderLayout.CENTER);
        JPanel southPanel = new TWPanel(new FlowLayout(FlowLayout.LEFT));
        List<Component> urlButtons = panelFactory.createURLButtonList();
        urlButtons.forEach(southPanel::add);
        List<Component> mediaButtons = panelFactory.createMediaButtonList();
        mediaButtons.forEach(southPanel::add);
        List<Component> hashTagButtons = panelFactory.createHashtagButtonList();
        hashTagButtons.forEach(southPanel::add);
        add(southPanel, BorderLayout.SOUTH);
        setSize(getPreferredSize());
    }
}
