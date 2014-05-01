/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.nokok.twitduke.core.api.twitter;

import net.nokok.twitduke.core.type.ErrorMessageReceivable;

/**
 * ユーザー情報の変更が出来ます。
 *
 * <p>
 * @author noko <nokok.kz at gmail.com>
 * @version 0.2
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
