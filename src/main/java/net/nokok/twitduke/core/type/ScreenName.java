/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.nokok.twitduke.core.type;

import java.util.Objects;

/**
 * スクリーンネームをラップするクラスです。
 * スクリーンネームは@から始まるIDのことで、ユーザー名とは区別されます
 *
 * @author noko < nokok.kz at gmail.com >
 */
public class ScreenName implements Retrievable<String> {

    private final String screenName;

    /**
     * 指定されたテキストでスクリーンネームを生成します
     *
     * @param screenName スクリーンネームを生成するテキスト
     *
     * @exception java.lang.NullPointerException     テキストがnull
     * @exception java.lang.IllegalArgumentException テキストが空の場合
     */
    public ScreenName(String screenName) {
        String name = Objects.requireNonNull(screenName, "渡されたスクリーンネームがnullです");
        if ( name.isEmpty() ) {
            throw new IllegalArgumentException("空の文字列は渡せません");
        }
        this.screenName = screenName;
    }

    @Override
    public String get() {
        return screenName;
    }

}
