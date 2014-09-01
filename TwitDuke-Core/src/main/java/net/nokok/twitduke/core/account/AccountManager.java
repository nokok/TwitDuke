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
package net.nokok.twitduke.core.account;

import java.util.List;
import java.util.Optional;
import net.nokok.twitduke.core.type.ScreenName;
import twitter4j.auth.AccessToken;

/**
 * Twitterアカウントに関する操作を定義するインターフェースです
 *
 */
public interface AccountManager extends AccountsInfo {

    /**
     * 指定した認証済みのアクセストークンからアカウントを追加します
     *
     * @param accessToken 認証済みのアクセストークン
     */
    void addAccount(AccessToken accessToken);

    /**
     * 指定したアクセストークンのアカウントを削除します
     *
     * @param accessToken 削除するアカウントのアクセストークン
     */
    void removeAccount(AccessToken accessToken);

    /**
     * 指定したスクリーンネームのアカウントを削除します
     *
     * @param screenName 削除するアカウントのスクリーンネーム
     */
    void removeAccount(ScreenName screenName);

    /**
     * 利用できるアカウントのスクリーンネームのリストを返します
     *
     * @return 利用できるアカウントのスクリーンネームのリスト
     */
    List<ScreenName> readAccountList();

    /**
     * 指定したスクリーンネームのアカウントの保存されたアクセストークンを読み込みます
     * 読み込みに失敗した場合はOptional.empty()が返ります
     *
     * @param screenName スクリーンネーム
     *
     * @return アクセストークン
     */
    Optional<AccessToken> readAccessToken(ScreenName screenName);

    /**
     * プライマリアカウントのアクセストークンを取得します
     *
     * @return プライマリアカウントのアクセストークン
     */
    Optional<AccessToken> readPrimaryAccount();
}
