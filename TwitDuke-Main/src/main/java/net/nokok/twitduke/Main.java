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
import java.util.stream.Stream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import net.nokok.twitduke.core.account.AccountManager;
import net.nokok.twitduke.core.account.AccountManagerFactory;
import net.nokok.twitduke.core.auth.LambdaOAuthFactory;
import net.nokok.twitduke.core.auth.OAuthOnSuccess;
import net.nokok.twitduke.core.auth.OAuthRunnable;
import net.nokok.twitduke.core.io.DirectoryHelper;
import net.nokok.twitduke.core.io.Paths;
import net.nokok.twitduke.core.log.ErrorLogExporter;
import net.nokok.twitduke.core.view.javafx.MainViewController;
import net.nokok.twitduke.core.view.javafx.TweetTextareaController;
import net.nokok.twitduke.core.view.javafx.TweetTextareaToolbarController;
import net.nokok.twitduke.core.view.keyevent.ActionRegister;
import net.nokok.twitduke.core.view.keyevent.ActionRegisterBuilder;
import net.nokok.twitduke.core.view.keyevent.KeyMapSetting;
import net.nokok.twitduke.core.view.keyevent.KeyMapStore;
import net.nokok.twitduke.core.view.keyevent.KeyMapStoreBuilder;
import net.nokok.twitduke.pluginsupport.PluginManager;
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
    public void start(Stage stage) throws Exception {
        if ( !existsTwitDukeDir() ) {
            DirectoryHelper.createTwitDukeDirectories();
        }
        AccountManager accountManager = AccountManagerFactory.newInstance();
        if ( accountManager.hasValidAccount() ) {
            FXMLLoader mainLoader = FXMLResources.MAIN.loader();
            FXMLLoader toolbarLoader = FXMLResources.TWEET_TEXTAREA_TOOLBAR.loader();
            FXMLLoader textAreaLoader = FXMLResources.TWEET_TEXTAREA.loader();
            Scene main = new Scene(mainLoader.load());
            MainViewController mainController = mainLoader.getController();
            BorderPane borderPane = toolbarLoader.load();
            TextArea textArea = textAreaLoader.load();
            mainController.setTweetTextAreaToolbar(borderPane);
            mainController.setTweetTextArea(textArea);
            TweetTextareaToolbarController toolbarController = toolbarLoader.getController();
            TweetTextareaController tweetTextareaController = textAreaLoader.getController();

            toolbarController.addTweetTextAreaController(tweetTextareaController);
            toolbarController.setSaveDraftButtonListener(tweetTextareaController);

            applyKeymap(stage);

            stage.setScene(main);

            stage.show();
        } else {
            startOAuth(accountManager, System.out::println);
        }
    }

    private void applyKeymap(Stage stage) throws Exception {
        KeyMapStore store = new KeyMapStoreBuilder().build();
        KeyMapSetting setting = store.load(KeyMapResources.DEFAULT_SETTING.get().openStream());
        ActionRegister register = new ActionRegisterBuilder(stage).build();
        register.registerKeyMap(setting, true);
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
        AccessToken accessToken = accountManager.readPrimaryAccount().orElseThrow(IllegalStateException::new);
        PluginManager globalPluginManager = new PluginManager("plugins", accessToken);
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
