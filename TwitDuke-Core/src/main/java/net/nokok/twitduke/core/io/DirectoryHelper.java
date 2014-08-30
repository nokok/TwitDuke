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
package net.nokok.twitduke.core.io;

import net.nokok.twitduke.base.io.Paths;
import java.io.File;
import net.nokok.twitduke.base.type.ScreenName;

/**
 * ディレクトリ関連する処理を行います
 *
 */
@Deprecated
public class DirectoryHelper {

    /**
     * 指定したアカウント固有のディレクトリを作成します。デフォルトではTwitDukeHome/.td/accounts/アカウント名/です
     *
     * @param accountName アカウントの名前
     *
     * @return アカウントのディレクトリ
     */
    @Deprecated
    public static File createAccountDirectory(String accountName) {
        File dir = getAccountDirectory(accountName);
        dir.mkdir();
        new File(String.join(File.separator, dir.getAbsolutePath(), "plugins")).mkdir();
        return dir;
    }

    /**
     * 指定したアカウントのディレクトリを返します。存在する場合、返すFileオブジェクトのisDirectoryはtrueを返します。
     *
     * @param accountName アカウント名
     *
     * @return アカウントのディレクトリ
     */
    @Deprecated
    public static File getAccountDirectory(String accountName) {
        return new File(String.join(File.separator, AccountPath.ACCOUNTS_PATH, accountName));
    }

    /**
     * 指定したスクリーンネームのアカウントのディレクトリを返します。
     *
     * @param screenName
     *
     * @return 指定したアカウントディレクトリのFileオブジェクト
     */
    @Deprecated
    public static File getAccountDirectory(ScreenName screenName) {
        return getAccountDirectory(screenName.get());
    }

    /**
     * TwitDukeのディレクトリを作成します。初回起動時にのみ呼ぶべきです
     */
    @Deprecated
    public static void createTwitDukeDirectories() {
        createDirectory(Paths.TWITDUKE_HOME);
        createDirectory(PluginPath.PLUGIN_DIR);
        createDirectory(LogPath.LOG_DIR);
        createDirectory(AccountPath.ACCOUNTS_PATH);
    }

    @Deprecated
    private static void createDirectory(String directoryPath) {
        File file = new File(directoryPath);
        boolean result = file.mkdir();
        if ( !result ) {
            throw new RuntimeException("ディレクトリの作成に失敗しました:" + directoryPath);
        }
    }
}
