/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.nokok.twitduke.core.type;

/**
 * ScriptExceptionを実行時例外にラップしたクラスです
 * RuntimeExceptionの仕様及び制限に依存します
 *
 * @author noko < nokok.kz at gmail.com >
 */
public class UncheckedScriptException extends RuntimeException {

    /**
     * 指定された原因を使用して新しいスクリプトの実行時例外を生成します。
     *
     * @param e 原因
     */
    public UncheckedScriptException(Exception e) {
        super(e);
    }

    /**
     * 指定された詳細メッセージを使用して新しいスクリプトの実行時例外を生成します
     *
     * @param s 詳細メッセージ
     */
    public UncheckedScriptException(String s) {
        super(s);
    }
}
