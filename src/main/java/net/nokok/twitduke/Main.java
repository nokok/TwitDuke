/*
 * The MIT License
 *
 * Copyright 2014 noko <nokok.kz at gmail.com>.
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
package net.nokok.twitduke;

import java.util.Objects;

/**
 * TwitDukeのMainクラスです。このクラスはエントリーポイントを持っています。mainメソッドへ渡す
 * オプションは-debugのみ有効です。それ以外のオプションおよびnullを渡した場合は無視されます。
 *
 * このクラスがTwitDukeの起動処理を制御します。
 * <p>
 */
public class Main {

    /**
     * TwitDukeのエントリポイントです。 オプションは-debugのみ有効です。 それ以外は全て無視されます。
     * <p>
     * @param args 渡された引数の配列
     */
    public static void main(String[] args) {
        new Main().run(hasDebugOption(args));
    }

    /**
     * 渡されたオプションの中に-debugが含まれているかをチェックします。nullが渡された場合はfalseを返します。
     * <p>
     * @param args TwitDukeに渡されたオプションの配列
     * <p>
     * @return -debugが含まれていた場合true
     *         それ以外またはnullが渡された場合false
     */
    private static boolean hasDebugOption(String[] args) {
        if ( Objects.isNull(args) ) {
            return false;
        }
        for ( String arg : args ) {
            if ( arg.equals("-debug") ) {
                return true;
            }
        }
        return false;
    }

    /**
     * 実際の起動処理を開始します。
     * <p>
     * @param isDebugMode デバッグモードで起動したい場合はtrueを渡します。
     */
    private void run(boolean isDebugMode) {

    }
}
