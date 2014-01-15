package net.nokok.twitduke.util;

import java.awt.Desktop;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
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

    public static URL createURL(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("URLが正しくありません");
        }
    }

    public static void openInBrowser(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (IOException | URISyntaxException e) {
            throw new InternalError("URLを開けませんでした");
        }
    }

    public static void openInBrowser(URL url) {
        openInBrowser(url.toString());
    }

    public static String encodeString(String text) {
        try {
            return URLEncoder.encode(text, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new InternalError("文字コードがサポートされていません");
        }
    }
}
