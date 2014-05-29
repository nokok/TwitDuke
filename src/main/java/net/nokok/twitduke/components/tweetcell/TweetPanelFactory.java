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

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import net.nokok.twitduke.components.async.UserIcon;
import net.nokok.twitduke.components.basic.TWButton;
import net.nokok.twitduke.components.basic.TWTextArea;
import net.nokok.twitduke.core.factory.AsyncTwitterFactory;
import twitter4j.AsyncTwitter;
import twitter4j.HashtagEntity;
import twitter4j.Status;
import twitter4j.URLEntity;
import twitter4j.auth.AccessToken;

/**
 * 指定されたステータスから個別のパネルやラベルを生成します
 */
public class TweetPanelFactory {

    private final Status status;
    private final AsyncTwitter twitter;

    /**
     * 指定されたステータスとアクセストークンで新しいパネルファクトリーを生成します。
     *
     * @param status      ツイートのステータス
     * @param accessToken アクセストークン
     *
     */
    public TweetPanelFactory(Status status, AccessToken accessToken) {
        this.status = status;
        twitter = AsyncTwitterFactory.newInstance(accessToken);
    }

    /**
     * ユーザー名を表示するラベルを生成します
     *
     * @return ユーザー名がセットされたラベル
     */
    public JLabel createUserNameLabel() {
        String userName = status.getUser().getName();
        JLabel label = new UserNameLabel(userName);
        return label;
    }

    /**
     * スクリーンネームを表示するラベルを生成します
     *
     * @return スクリーンネームがセットされたラベル
     */
    public JLabel createScreenNameLabel() {
        String screenName = status.getUser().getScreenName();
        JLabel label = new ScreenNameLabel("@" + screenName);
        return label;
    }

    /**
     * お気に入りボタンを生成します。
     * 既にお気に入り済みのツイートだった場合はボタンの背景色をお気に入り済みの色に変更します。
     *
     * @return お気に入りボタン
     */
    public JButton createFavoriteButton() {
        FavoriteButton button = new FavoriteButton();
        if ( status.isFavorited() ) {
            button.select();
        }
        button.addActionListener(e -> {
            if ( button.isSelected() ) {
                twitter.destroyFavorite(status.getId());
                button.unselect();
            } else {
                twitter.createFavorite(status.getId());
                button.select();
            }
        });
        return button;
    }

    /**
     * リツイートボタンを生成します。
     * 既にリツイート済みのボタンだった場合はボタンの背景色をリツイート済みの色に変更します
     *
     * @return リツイートボタン
     */
    public JButton createRetweetButton() {
        RetweetButton button = new RetweetButton();
        if ( status.isRetweetedByMe() ) {
            button.select();
        }
        button.addActionListener(e -> {
            if ( button.isSelected() ) {
                twitter.destroyStatus(status.getId());
                button.unselect();
            } else {
                twitter.retweetStatus(status.getId());
                button.select();
            }
        });
        return button;
    }

    /**
     * ツイート本文を表示するテキストエリアを生成します
     *
     * @return ツイート本文を表示するテキストエリア
     */
    public JTextArea createTweetTextArea() {
        JTextArea textArea = TWTextArea.newNotEditableTextArea(status.getText());
        textArea.setLineWrap(true);
        return textArea;
    }

    /**
     * ステータスに含まれるハッシュタグのボタンリストを生成します
     *
     * @return ハッシュタグのボタンのリスト
     */
    public List<JButton> createHashtagButtonList() {
        HashtagEntity[] hashtagEntities = status.getHashtagEntities();
        List<JButton> buttonList = new ArrayList<>(hashtagEntities.length);
        Stream
                .of(hashtagEntities)
                .forEach(h -> buttonList.add(new TWButton(h.getText())));
        return buttonList;
    }

    /**
     * ステータスに含まれるURLのボタンリストを生成します
     *
     * @return URLのボタンのリスト
     */
    public List<JButton> createURLButtonList() {
        URLEntity[] urlEntities = status.getURLEntities();
        List<JButton> buttonList = new ArrayList<>(urlEntities.length);
        Stream.of(urlEntities)
                .forEach(u -> buttonList.add(new TWButton(u.getDisplayURL())));
        return buttonList;
    }

    /**
     * ステータスの時間と現在時刻との差を表示するラベルを生成します
     *
     * @return 時間差を表示するラベル
     */
    public JLabel createTimeLabel() {
        Instant instant = Instant.ofEpochMilli(status.getCreatedAt().getTime());
        LocalDateTime time = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return new TimeLabel(time);
    }

    /**
     * ユーザーアイコンを生成します
     *
     * @return ユーザーアイコン
     */
    public JLabel createUserIcon() {
        JLabel label = new UserIcon(status.getUser().getProfileImageURLHttps());
        return label;
    }

    /**
     * Viaのラベルを生成します
     *
     * @return Viaのラベル
     */
    public JLabel createViaLabel() {
        JLabel label = new ViaLabel(status.getSource());
        return label;
    }
}
