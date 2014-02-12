package net.nokok.twitduke.util;

import java.awt.Desktop;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import twitter4j.MediaEntity;
import twitter4j.Status;
import twitter4j.URLEntity;

public class URLUtil {

    /**
     * 渡されたステータスに格納されているURLエンティティを取り出し、ツイートのテキストに含まれるt.coで始まる短縮URLを
     * 表示用のURLに置換します
     *
     * @param status ツイートのステータス
     * @return 短縮URLが表示用に展開されたURLで置換されたツイートのテキスト
     */
    public static String extendURL(Status status) {
        String statusText = status.getText();
        for (URLEntity entity : status.getURLEntities()) {
            statusText = statusText.replaceAll(entity.getURL(), entity.getDisplayURL());
        }
        for (MediaEntity entity : status.getMediaEntities()) {
            statusText = statusText.replaceAll(entity.getURL(), entity.getDisplayURL());
        }
        return statusText;
    }

    /**
     * 渡された文字列からURLオブジェクトを作成します。
     *
     * @param url URLオブジェクトを作成する文字列(例:"http://google.com")
     * @return 作成されたURLオブジェクト
     * @throws java.lang.IllegalArgumentException URLでない文字列が渡された時にスローされます
     */
    public static URL createURL(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("URLが正しくありません");
        }
    }

    /**
     * 渡された文字列からなるURLを既定のブラウザで開きます。
     *
     * @param url ブラウザで開くURL
     * @throws java.lang.IllegalArgumentException URLでない文字列が渡された時、または既定のブラウザが見つからない、起動できない時にスローされます
     */
    public static void openInBrowser(String url) {
        try {
            Desktop.getDesktop().browse(createURL(url).toURI());
        } catch (IOException | URISyntaxException e) {
            throw new IllegalArgumentException("URLをブラウザで開くと時にエラーが発生しました", e.getCause());
        }
    }

    /**
     * 渡されたURLオブジェクトに格納されているURLを既定のブラウザで開きます。
     *
     * @param url ブラウザで開くURLオブジェクト
     */
    public static void openInBrowser(URL url) {
        openInBrowser(url.toString());
    }

    /**
     * 渡された日本語を含むURLをURLEncoderを用いてUTF-8でエンコードします。
     *
     * @param text エンコードする文字列(主にURL)
     * @return エンコードされた文字列
     * @throws java.lang.InternalError TwitDukeを実行しているマシンでUTF-8がサポートされていない場合にスローされます
     */
    public static String encodeString(String text) {
        try {
            return URLEncoder.encode(text, "utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new InternalError("文字コードがサポートされていません" + e.getMessage());
        }
    }

    /**
     * 全てのURLエンティティを既定のブラウザで開きます
     *
     * @param entities URLエンティティの配列
     */
    public static void allURLEntitiesOpen(URLEntity[] entities) {
        for (URLEntity entity : entities) {
            openInBrowser(entity.getExpandedURL());
        }
    }

    /**
     * 全てのメディアエンティティを既定のブラウザで開きます
     *
     * @param entities メディアエンティティの配列
     */
    public static void allMediaEntitiesOpen(MediaEntity[] entities) {
        for (MediaEntity entity : entities) {
            openInBrowser(entity.getExpandedURL());
        }
    }
}
