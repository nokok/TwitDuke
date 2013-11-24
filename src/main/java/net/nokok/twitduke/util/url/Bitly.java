package net.nokok.twitduke.util.url;

/**
 * @author noko
 *         Bitlyで短縮URLを生成するクラスです
 */
public class Bitly extends CommonURLCreator implements IShortURLCreator {

    public String requestShortenedURL(String url) {
        checkURL(url);
        return null;
    }
}
