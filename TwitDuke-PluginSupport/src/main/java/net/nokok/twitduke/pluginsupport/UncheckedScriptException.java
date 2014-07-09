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
package net.nokok.twitduke.pluginsupport;

/**
 * ScriptExceptionを実行時例外にラップしたクラスです
 * RuntimeExceptionの仕様及び制限に依存します
 */
public class UncheckedScriptException extends RuntimeException {

    private static final long serialVersionUID = 8440599197184839543L;

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
