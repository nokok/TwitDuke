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

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import net.nokok.twitduke.components.basics.TWPanel;
import net.nokok.twitduke.components.basics.TWSlimButton;
import net.nokok.twitduke.components.basics.TWTextArea;
import net.nokok.twitduke.core.factory.TAsyncTwitterFactory;
import net.nokok.twitduke.core.view.async.AsyncImageIcon;
import net.nokok.twitduke.core.view.async.MediaThumbnail;
import net.nokok.twitduke.core.view.async.OverlayUserIcon;
import twitter4j.AsyncTwitter;
import twitter4j.HashtagEntity;
import twitter4j.MediaEntity;
import twitter4j.Status;
import twitter4j.URLEntity;
import twitter4j.auth.AccessToken;

/**
 * 指定されたステータスから個別のパネルやラベルを生成します
 */
public class TweetPanelFactoryImpl implements TweetPanelFactory {

    private final Status status;
    private final Optional<Status> retweetedStatus;
    private final Status activeStatus;
    private final AsyncTwitter twitter;
    private final AccessToken accessToken;
    private final String screenName;

    /**
     * 指定されたステータスとアクセストークンで新しいパネルファクトリーを生成します。
     *
     * @param status      ツイートのステータス
     * @param accessToken アクセストークン
     *
     */
    public TweetPanelFactoryImpl(Status status, AccessToken accessToken) {
        this.status = status;
        this.accessToken = accessToken;
        retweetedStatus = Optional.ofNullable(status.getRetweetedStatus());
        //リツイートの場合はRetweeteeStatus、そうでない場合はstatusが入る
        activeStatus = retweetedStatus.orElseGet(() -> status);
        twitter = TAsyncTwitterFactory.newInstance(accessToken);
        this.screenName = accessToken.getScreenName();
    }

    @Override
    public List<Component> createMediaButtonList() {
        List<Component> buttons = new ArrayList<>(status.getMediaEntities().length);
        Stream.of(status.getMediaEntities()).map(s -> new MediaSlimButton(s)).forEach(buttons::add);
        return buttons;
    }

    @Override
    public Component createTweetStatusPanel() {
        JPanel panel = new TWPanel();
        panel.setPreferredSize(new Dimension(7, -1));
        if ( status.isRetweet() ) {
            panel.setBackground(Color.GREEN);
        }
        if ( activeStatus.getText().contains("@" + accessToken.getScreenName()) ) {
            panel.setBackground(Color.RED);
        }
        return panel;
    }

    /**
     * ユーザー名を表示するラベルを生成します
     *
     * @return ユーザー名がセットされたラベル
     */
    @Override
    public Component createUserNameLabel() {
        String userName = activeStatus.getUser().getName();
        JLabel label = new UserNameLabel(userName);
        return label;
    }

    /**
     * スクリーンネームを表示するラベルを生成します
     *
     * @return スクリーンネームがセットされたラベル
     */
    @Override
    public Component createScreenNameLabel() {
        JLabel label = new ScreenNameLabel("@" + activeStatus.getUser().getScreenName());
        return label;
    }

    /**
     * お気に入りボタンを生成します。
     * 既にお気に入り済みのツイートだった場合はボタンの背景色をお気に入り済みの色に変更します。
     *
     * @return お気に入りボタン
     */
    @Override
    public Component createFavoriteButton() {
        FavoriteButton button = new FavoriteButton();
        if ( status.isFavorited() ) {
            button.select();
        }
        button.addActionListener(e -> {
            if ( button.isSelect() ) {
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
    @Override
    public Component createRetweetButton() {
        RetweetButton button = new RetweetButton();
        if ( status.isRetweetedByMe() ) {
            button.select();
        }
        button.addActionListener(e -> {
            if ( button.isSelect() ) {
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
    @Override
    public Component createTweetTextArea() {
        String text = activeStatus.getText();
        for ( URLEntity entity : status.getURLEntities() ) {
            text = text.replaceAll(entity.getURL(), entity.getDisplayURL());
        }
        for ( MediaEntity entity : status.getMediaEntities() ) {
            text = text.replace(entity.getURL(), entity.getDisplayURL());
        }
        JTextArea textArea = TWTextArea.newNotEditableTextArea(text);
        return textArea;
    }

    /**
     * ステータスに含まれるハッシュタグのボタンリストを生成します
     *
     * @return ハッシュタグのボタンのリスト
     */
    @Override
    public List<Component> createHashtagButtonList() {
        HashtagEntity[] hashtagEntities = status.getHashtagEntities();
        List<Component> buttonList = new ArrayList<>(hashtagEntities.length);
        Stream
            .of(hashtagEntities)
            .forEach(h -> buttonList.add(new TWSlimButton("#" + h.getText())));
        return buttonList;
    }

    /**
     * ステータスに含まれるURLのボタンリストを生成します
     *
     * @return URLのボタンのリスト
     */
    @Override
    public List<Component> createURLButtonList() {
        URLEntity[] urlEntities = status.getURLEntities();
        List<Component> buttonList = new ArrayList<>(urlEntities.length);
        Stream.of(urlEntities)
            .forEach(u -> buttonList.add(new URLSlimButton(u)));
        Stream.of(urlEntities)
            .filter(p -> p.getDisplayURL().contains("shindanmaker"))
            .map(p -> new ShindanmakerSlimButton(p.getExpandedURL(), accessToken))
            .forEach(buttonList::add);
        return buttonList;
    }

    /**
     * ステータスに含まれる画像のリストを生成します
     *
     * @return
     */
    @Override
    public List<Component> createThumbnailList() {
        List<Component> thunbnails = new ArrayList<>(status.getMediaEntities().length);
        Stream.of(status.getMediaEntities())
            .map(entity -> new MediaThumbnail(entity.getMediaURLHttps()))
            .forEach(thunbnails::add);
        return thunbnails;
    }

    /**
     * ステータスの時間と現在時刻との差を表示するラベルを生成します
     *
     * @return 時間差を表示するラベル
     */
    @Override
    public Component createTimeLabel() {
        Instant instant = Instant.ofEpochMilli(status.getCreatedAt().getTime());
        LocalDateTime time = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return new TimeLabel(time);
    }

    /**
     * ユーザーアイコンを生成します
     *
     * @return ユーザーアイコン
     */
    private Component createUserIconLabel() {
        JLabel label = new AsyncImageIcon(status.getUser().getProfileImageURLHttps());
        return label;
    }

    /**
     * ユーザーのアイコンを生成します。
     *
     * ステータスがRTの場合はRTしたユーザーとされたユーザーのアイコンをオーバーレイ表示します
     *
     * @return アイコン
     */
    @Override
    public Component createUserIcon() {
        if ( retweetedStatus.isPresent() ) {
            return new OverlayUserIcon(status);
        }
        return createUserIconLabel();
    }

    /**
     * Viaのラベルを生成します
     *
     * @return Viaのラベル
     */
    @Override
    public Component createViaLabel() {
        JLabel label = new ViaLabel(status.getSource());
        return label;
    }
}
