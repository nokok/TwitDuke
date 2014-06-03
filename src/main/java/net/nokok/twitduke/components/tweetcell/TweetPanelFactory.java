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

import java.awt.Component;
import java.util.List;

public interface TweetPanelFactory {

    /**
     * お気に入りボタンを生成します。
     * 既にお気に入り済みのツイートだった場合はボタンの背景色をお気に入り済みの色に変更します。
     *
     * @return お気に入りボタン
     */
    Component createFavoriteButton();

    /**
     * リツイートボタンを生成します。
     * 既にリツイート済みのボタンだった場合はボタンの背景色をリツイート済みの色に変更します
     *
     * @return リツイートボタン
     */
    Component createRetweetButton();

    /**
     * スクリーンネームを表示するラベルを生成します
     *
     * @return スクリーンネームがセットされたラベル
     */
    Component createScreenNameLabel();

    /**
     * ステータスに含まれる画像のリストを生成します
     *
     * @return
     */
    List<Component> createThumbnailList();

    /**
     * ステータスの時間と現在時刻との差を表示するラベルを生成します
     *
     * @return 時間差を表示するラベル
     */
    Component createTimeLabel();

    /**
     * ツイート本文を表示するテキストエリアを生成します
     *
     * @return ツイート本文を表示するテキストエリア
     */
    Component createTweetTextArea();

    /**
     * ステータスに含まれるURLのボタンリストを生成します
     *
     * @return URLのボタンのリスト
     */
    List<Component> createURLButtonList();

    /**
     * ステータスに含まれる画像のURLのボタンリストを生成します
     *
     * @return
     */
    List<Component> createMediaButtonList();

    /**
     * ステータスに含まれるハッシュタグのボタンリストを生成します
     *
     * @return ハッシュタグのボタンのリスト
     */
    List<Component> createHashtagButtonList();

    /**
     * ユーザーのアイコンを生成します。
     *
     * ステータスがRTの場合はRTしたユーザーとされたユーザーのアイコンをオーバーレイ表示します
     *
     * @return アイコン
     */
    Component createUserIcon();

    /**
     * ユーザー名を表示するラベルを生成します
     *
     * @return ユーザー名がセットされたラベル
     */
    Component createUserNameLabel();

    /**
     * Viaのラベルを生成します
     *
     * @return Viaのラベル
     */
    Component createViaLabel();

}
