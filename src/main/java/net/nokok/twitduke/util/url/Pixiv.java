package net.nokok.twitduke.util.url;

/**
 * @author noko
 *         Pixiv(p.tl)で短縮URLを作成するクラスです
 */
public class Pixiv extends CommonURLCreator implements IShortURLCreator {

    public String requestShortenedURL(String url) {
        checkURL(url);
        return null;
    }
}
