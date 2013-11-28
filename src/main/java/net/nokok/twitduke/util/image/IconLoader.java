package net.nokok.twitduke.util.image;

/**
 * @author noko
 *         アイコンをロードするクラスです
 */
public class IconLoader {
    private static final IconLoader instance = new IconLoader();

    private IconLoader() {

    }

    public IconLoader getInstance() {
        return instance;
    }
}
