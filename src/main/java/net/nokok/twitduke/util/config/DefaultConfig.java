package net.nokok.twitduke.util.config;

import java.awt.*;

/**
 * @author noko
 *         デフォルトの設定を定義するクラスです
 */
public class DefaultConfig {
    public static final Dimension WINDOW_SIZE = new Dimension(512, 640);
    public static final String APP_TITLE = "TwitDuke";
    public static final String APP_VERSION = "Alpha";
    public static final Long REVISION = new Long(1000);
    public static final String FORMATTED_APP_VERSION = APP_VERSION + " (Rev." + REVISION + ")";
}
