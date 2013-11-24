package net.nokok.twitduke.util.url;

/**
 * @author noko
 *         短縮URLクラスのインターフェースです
 */
public interface IShortURLCreator {

    /**
     * 短縮URLをAPIから取得します
     *
     * @param url 短縮前のURL
     * @return 短縮されたURL
     * @throws IllegalArgumentException 渡されたURLの文字列が空だった場合
     */
    public String requestShortenedURL(String url) throws IllegalArgumentException;
}
