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
import java.io.File;
import java.io.PrintStream;
import net.nokok.twitduke.core.api.account.AccountManager;
import net.nokok.twitduke.core.api.auth.OAuthRunnable;
import net.nokok.twitduke.core.api.io.Paths;
import net.nokok.twitduke.core.impl.account.DirectoryHelper;
import net.nokok.twitduke.core.impl.auth.LambdaOAuthFactory;
import net.nokok.twitduke.core.impl.factory.AccountManagerFactory;
import net.nokok.twitduke.core.impl.log.ErrorLogExporter;

/**
 * TwitDukeのMainクラスです。このクラスはエントリーポイントを持っています。
 *
 * このクラスがTwitDukeの起動処理を制御します。
 *
 */
public class Main {

    /**
     * TwitDukeのエントリポイントです。
     *
     * @param args 渡された引数の配列
     */
    public static void main(String[] args) {

        PrintStream out = System.out;
        PrintStream err = System.err;
        ErrorLogExporter logger = new ErrorLogExporter();

        try {
            System.setErr(new PrintStream(nullOutputStream()));
            System.setOut(new PrintStream(nullOutputStream()));
            if ( !existsTwitDukeDir() ) {
                DirectoryHelper.createTwitDukeDirectories();
            }
            final AccountManager accountManager = AccountManagerFactory.newInstance();
            if ( !accountManager.hasValidAccount() ) {
                OAuthRunnable auth = LambdaOAuthFactory.newInstance();
                auth.onError(logger::onError);
                auth.onSuccess(accountManager::addAccount);
                auth.startOAuth();
            }
        } catch (Throwable e) {
            System.setOut(out);
            System.setErr(err);
            logger.error(e);
        }
    }

    private static boolean existsTwitDukeDir() {
        return new File(Paths.TWITDUKE_HOME).exists();
    }

}
