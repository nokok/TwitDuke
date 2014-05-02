/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.nokok.twitduke.core.type;

import java.util.Objects;

/**
 * ユーザー名をラップするクラスです。
 * ユーザー名はスクリーンネームとは区別されます
 *
 * @author noko < nokok.kz at gmail.com >
 */
public class UserName implements Retrievable<String> {

    private final String userName;

    /**
     * 指定されたテキストでユーザー名を生成します
     *
     * @param userName ユーザー名を生成するテキスト
     *
     * @exception java.lang.NullPointerException     テキストがnull
     * @exception java.lang.IllegalArgumentException テキストが空の場合
     */
    public UserName(String userName) {
        String name = Objects.requireNonNull(userName, "渡されたユーザー名がnullです");
        if ( name.isEmpty() ) {
            throw new IllegalArgumentException("空の文字列は渡せません");
        }
        this.userName = userName;
    }

    @Override
    public String get() {
        return userName;
    }

}
