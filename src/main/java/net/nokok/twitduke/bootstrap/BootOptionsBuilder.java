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
package net.nokok.twitduke.bootstrap;

import java.util.Objects;

/**
 * オプションの配列を扱いやすいBootOptionsオブジェクトにします
 */
public class BootOptionsBuilder {

    /**
     * 指定したオプションの配列でBootOptionsオブジェクトを構築します
     *
     * @param args TwitDukeに渡されたオプションの配列
     *
     * @return BootOptionsオブジェクト
     */
    public static BootOptions createBootOptions(String[] args) {
        boolean isDebugMode = hasDebugOption(args);
        boolean isCliMode = hasCliOption(args);
        BootOptions bootOptions = new BootOptions(isDebugMode, isCliMode);
        return bootOptions;
    }

    /**
     * 渡されたオプションの中に-debugが含まれているかをチェックします。nullが渡された場合はfalseを返します。
     *
     * @param args TwitDukeに渡されたオプションの配列
     *
     * @return -debugが含まれていた場合true
     *         それ以外またはnullが渡された場合false
     */
    private static boolean hasDebugOption(String[] args) {
        return hasOption(args, "-debug");
    }

    /**
     * 渡されたオプションの中に-cliが含まれているかをチェックします。nullが渡された場合はfalseを返します。
     *
     * @param args TwitDukeに渡されたオプションの配列
     *
     * @return -cliが含まれていた場合true
     *         それ以外またはnullが渡された場合false
     */
    private static boolean hasCliOption(String[] args) {
        return hasOption(args, "-cli");
    }

    /**
     * オプションの配列に指定されたオプションが含まれているかをチェックします。検索をするオプションまたはオプションの配列
     * がnullの場合はfalseを返します。
     *
     * @param args     TwitDukeに渡されたオプションの配列
     * @param searchOp 検索するオプション
     *
     * @return オプションの配列に指定された検索するオプションが含まれている場合はtrue
     */
    private static boolean hasOption(String[] args, String searchOp) {
        if ( Objects.isNull(args) || Objects.isNull(searchOp) ) {
            return false;
        }
        for ( String arg : args ) {
            if ( arg.equals(searchOp) ) {
                return true;
            }
        }
        return false;
    }
}
