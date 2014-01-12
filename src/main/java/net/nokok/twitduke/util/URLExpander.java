package net.nokok.twitduke.util;

import twitter4j.MediaEntity;
import twitter4j.Status;
import twitter4j.URLEntity;

public class URLExpander {

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
}
