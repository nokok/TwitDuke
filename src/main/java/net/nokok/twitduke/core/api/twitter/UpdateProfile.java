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
package net.nokok.twitduke.core.api.twitter;

import net.nokok.twitduke.core.type.ErrorMessageReceivable;

/**
 * ユーザー情報の変更が出来ます。
 *
 */
public interface UpdateProfile {

    /**
     * ユーザー情報変更中にエラーが発生した場合に呼ばれるメソッドです。
     * エラーメッセージはErrorMessageReceivableを実装したオブジェクトに移譲されます。
     *
     * @param receivable エラーメッセージを実際に受信するオブジェクト
     *
     */
    void onError(ErrorMessageReceivable receivable);

    /**
     * プロフィールの詳細(TwitterAPI上ではDescription)を変更します。
     * このプロフィールは160文字以内である必要があります。
     *
     * @param bio 変更後のプロフィールの詳細
     */
    void updateBio(String bio);

    /**
     * プロフィールの場所を変更します。
     * 30文字以内である必要があります
     *
     * @param location 変更後の場所
     */
    void updateLocation(String location);

    /**
     * プロフィールの名前を変更します。
     * 名前は20文字以内である必要があります。
     *
     * @param name 変更後の名前
     */
    void updateName(String name);

    /**
     * プロフィールのURLを変更します。
     * 100文字以内である必要があります。
     * "http://"を付けなかった場合は自動で先頭に追加されます。
     *
     * @param url 変更後のURL
     */
    void updateUrl(String url);

}
