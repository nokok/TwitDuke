package net.nokok.twitduke.util.url;

/**
 * @author noko
 *         Bitlyで短縮URLを生成するクラスです
 */
public class Bitly extends ShortURLCreator implements IShortURLCreator {

    @Override
    public String requestShortenedURL(String url) {
        checkURL(url);
        return null;
    }
}
