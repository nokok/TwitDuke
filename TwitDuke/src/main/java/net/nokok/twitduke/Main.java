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

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.util.stream.Stream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import net.nokok.twitduke.base.io.Paths;
import net.nokok.twitduke.components.javafx.MainViewController;
import net.nokok.twitduke.components.javafx.TweetTextareaToolbarController;
import net.nokok.twitduke.components.keyevent.ActionRegister;
import net.nokok.twitduke.components.keyevent.KeyMapSetting;
import net.nokok.twitduke.components.keyevent.KeyMapStore;
import net.nokok.twitduke.core.account.AccountManager;
import net.nokok.twitduke.core.auth.LambdaOAuthFactory;
import net.nokok.twitduke.core.auth.OAuthOnSuccess;
import net.nokok.twitduke.core.auth.OAuthRunnable;
import net.nokok.twitduke.core.log.ErrorLogExporter;
import net.nokok.twitduke.core.twitter.TwitterNotificationListener;
import net.nokok.twitduke.core.twitter.TwitterStreamRunner;
import net.nokok.twitduke.core.view.Window;
import net.nokok.twitduke.pluginsupport.PluginManager;
import net.nokok.twitduke.pluginsupport.eventrunner.StreamEventRunner;
import net.nokok.twitduke.resources.FXMLResources;
import net.nokok.twitduke.resources.KeyMapResources;
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
        URL mainFxml = FXMLResources.MAIN_FXML.orElseThrow(() -> new RuntimeException("リソース[main.fxml]が見つかりません"));
        URL tweetTextAreaToolbarFxml = FXMLResources.TWEET_TEXTAREA_TOOLBAR.orElseThrow(() -> new RuntimeException("リソース[tweetTextAreaToolbar.fxml]が見つかりません"));
        FXMLLoader mainFxmlLoader = new FXMLLoader(mainFxml);
        FXMLLoader tweetTextAreaToolbarLoader = new FXMLLoader(tweetTextAreaToolbarFxml);
        try {
            Scene main = new Scene(mainFxmlLoader.load());
            MainViewController controller = mainFxmlLoader.getController();
            BorderPane borderPane = tweetTextAreaToolbarLoader.load();
            controller.setTweetTextAreaToolbar(borderPane);
            TweetTextareaToolbarController toolbarController = tweetTextAreaToolbarLoader.getController();

            KeyMapStore store = KeyMapStore.newInstance();
            KeyMapSetting setting = store.load(KeyMapResources.DEFAULT_SETTING.get().openStream());
            ActionRegister register = ActionRegister.newInstance(main.getRoot());
            register.registerKeyMap(setting);

            stage.setScene(main);
            stage.show();

        } catch (IOException e) {
            throw new UncheckedIOException("FXMLファイルを読み込めませんでした。ファイルは見つかりましたが、ファイルがおかしいようです。", e);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * TwitDukeのエントリポイントです。
     *
     * @param args 渡された引数の配列
     */
    public static void main(String[] args) {
        Application.launch(args);
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
        try {
            Runnable server = new WebServerStarter(accessToken);
            new Thread(server).start();
        } catch (RuntimeException e) {
            throw new RuntimeException("サーバーが既に起動しています。ポート:8192が使用できません。", e);
        }
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
