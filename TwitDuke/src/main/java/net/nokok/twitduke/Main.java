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
import com.google.common.io.Resources;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UncheckedIOException;
import java.net.URL;
import java.util.stream.Stream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.nokok.twitduke.base.io.Paths;
import net.nokok.twitduke.components.javafx.MainViewController;
import net.nokok.twitduke.core.account.AccountManager;
import net.nokok.twitduke.core.account.AccountManagerFactory;
import net.nokok.twitduke.core.auth.LambdaOAuthFactory;
import net.nokok.twitduke.core.auth.OAuthOnSuccess;
import net.nokok.twitduke.core.auth.OAuthRunnable;
import net.nokok.twitduke.core.io.DirectoryHelper;
import net.nokok.twitduke.core.log.ErrorLogExporter;
import net.nokok.twitduke.core.twitter.TwitterNotificationListener;
import net.nokok.twitduke.core.twitter.TwitterStreamRunner;
import net.nokok.twitduke.core.view.Window;
import net.nokok.twitduke.pluginsupport.PluginManager;
import net.nokok.twitduke.pluginsupport.eventrunner.StreamEventRunner;
import net.nokok.twitduke.server.WebServerStarter;
import twitter4j.auth.AccessToken;

/**
 * TwitDukeのMainクラスです。このクラスはエントリーポイントを持っています。
 *
 * このクラスがTwitDukeの起動処理を制御します。
 *
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) {
        URL url = Resources.getResource(String.join(File.separator, "fxml", "main.fxml"));
        FXMLLoader loader = new FXMLLoader(url);
        try {
            MainViewController controller = loader.getController();
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException e) {
            throw new UncheckedIOException("FXMLファイルを読み込めませんでした", e);
        }
    }

    /**
     * TwitDukeのエントリポイントです。
     *
     * @param args 渡された引数の配列
     */
    public static void main(String[] args) {
        if ( !existsTwitDukeDir() ) {
            DirectoryHelper.createTwitDukeDirectories();
        }
        boolean isDebug = hasOption("--debug", args);
        boolean isServerMode = hasOption("--server-mode", args);
        if ( !isDebug ) {
            disableOutput();
        }
        final AccountManager accountManager = AccountManagerFactory.newInstance();
        if ( accountManager.hasValidAccount() ) {
            if ( !isServerMode ) {
                openWindow(accountManager);
            }
            AccessToken accessToken = accountManager.readPrimaryAccount().get();
            startServer(accessToken);
        } else {
            startOAuth(accountManager, token -> {
                if ( !isServerMode ) {
                    openWindow(accountManager);
                }
                startServer(token);
            });
        }
    }

    private static void disableOutput() {
        System.setErr(new PrintStream(nullOutputStream()));
        System.setOut(new PrintStream(nullOutputStream()));
    }

    /**
     * 指定されたアカウントマネージャと認証結果レシーバオブジェクトを用いて認証をします。
     * 認証完了後、アカウントマネージャを通じてアカウントを追加し、レシーバに通知します
     *
     * @param accountManager
     * @param receiver
     */
    private static void startOAuth(AccountManager accountManager, OAuthOnSuccess receiver) {
        OAuthRunnable auth = LambdaOAuthFactory.newInstance();
        ErrorLogExporter logger = new ErrorLogExporter();
        auth.onError(logger::onError);
        auth.onSuccess(token -> {
            accountManager.addAccount(token);
            receiver.onSuccess(token);
        });
        auth.startOAuth();
    }

    /**
     * ウィンドウを初期化し、ストリームの受信を開始します
     *
     * @param accountManager
     */
    private static void openWindow(AccountManager accountManager) {
        Window window = Window.createNewWindow(accountManager);
        AccessToken accessToken = accountManager.readPrimaryAccount().orElseThrow(IllegalStateException::new);
        PluginManager globaPluginManager = new PluginManager("plugins", accessToken);
        TwitterStreamRunner streamRunner = new TwitterStreamRunner(accessToken);
        streamRunner.addTwitterListener(new StreamEventRunner(globaPluginManager.getPlugins()));
        streamRunner.addTwitterListener(new TwitterNotificationListener());
        streamRunner.addHomeTimelineTweetCellListener(window);
        streamRunner.enableStackTrace();
        streamRunner.run();
    }

    /**
     * 指定されたアクセストークンでサーバーを起動します。
     *
     * @param accessToken
     */
    private static void startServer(AccessToken accessToken) {
        Runnable server = new WebServerStarter(accessToken);
        server.run();
    }

    /**
     * @return TwitDukeディレクトリ(.td)が存在する場合true
     */
    private static boolean existsTwitDukeDir() {
        return new File(Paths.TWITDUKE_HOME).exists();
    }

    /**
     * 指定された配列内に指定された引数が存在するかチェックします。
     *
     * @param arg
     * @param args
     *
     * @return 存在する場合true
     */
    private static boolean hasOption(String arg, String[] args) {
        return Stream.of(args).anyMatch(a -> a.equals(arg));
    }

}
