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
package net.nokok.twitduke;

import static com.google.common.io.ByteStreams.nullOutputStream;
import java.io.PrintStream;
import java.util.Objects;
import net.nokok.twitduke.bootstrap.Bootable;
import net.nokok.twitduke.bootstrap.BootstrapFactory;

/**
 * TwitDukeのMainクラスです。このクラスはエントリーポイントを持っています。mainメソッドへ渡す
 * オプションは-debugと-cliのみ有効です。それ以外のオプションおよびnullを渡した場合は無視されます。
 * -debugを渡した場合はデバッグモードとして起動します。
 * -cliを渡した場合はUIは表示されません。
 * 両方同時に渡すことも出来ます。-debugと-cliを渡した場合は、デバッグモードかつUIなしで起動します。
 *
 * このクラスがTwitDukeの起動処理を制御します。
 *
 */
public class Main {

    private static boolean isDebugMode;
    private static boolean isCliMode;

    /**
     * TwitDukeのエントリポイントです。 オプションは-debugと-cliのみ有効です。 それ以外は全て無視されます。
     *
     * @param args 渡された引数の配列
     */
    public static void main(String[] args) {
        isDebugMode = hasDebugOption(args);
        isCliMode = hasCliOption(args);
        new Main().run();
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

    /**
     * 実際の起動処理を開始します。
     */
    private void run() {

        PrintStream out = System.out;
        PrintStream err = System.err;

        try {
            if ( !isDebugMode ) {
                System.setErr(new PrintStream(nullOutputStream()));
                System.setOut(new PrintStream(nullOutputStream()));
            }
            Bootable bootable = BootstrapFactory.createBootableObject();
            bootable.startInitialize();
        } catch (Throwable e) {
            System.setOut(out);
            System.setErr(err);
        }
    }

    /**
     * @return デバッグモードで起動された場合trueを返します
     */
    public static boolean isDebugMode() {
        return isDebugMode;
    }

    /**
     * @return CLIモードで起動された場合trueを返します
     */
    public static boolean isCliMode() {
        return isCliMode;
    }
}
