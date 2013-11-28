package net.nokok.twitduke.util.config;

import javax.swing.*;
import java.awt.*;

/**
 * @author noko
 *         デフォルトの設定を定義するクラスです
 */
public enum DefaultConfig {
    INSTANCE;

    /* OAuth Keys */
    public static final String OAUTH_CONSUMER_KEY = "";
    public static final String OAUTH_CONSUMER_SECRET = "";

    /* GUI Settings */
    public static final Dimension WINDOW_SIZE = new Dimension(512, 640);
    public static final String APP_TITLE = "TwitDuke";
    public static final String APP_VERSION = "Alpha";
    public static final Long REVISION = new Long(1000);
    public static final String FORMATTED_APP_VERSION = APP_VERSION + " (Rev." + REVISION + ")";

    /* ToolBar */
    public static final Dimension CELL_SIZE = new Dimension(512, 50);
    public static final Dimension TOOLBAR_SIZE = new Dimension(512, 40);
    public static final Dimension TOOLBAR_BUTTON_SIZE = new Dimension(70, 30);
    public static final Color BACKGROUND_BUTTON_COLOR = new Color(44, 62, 80);
    public static final Color FOREGROUND_BUTTON_COLOR = new Color(236, 240, 241);

    /* Others */
    public static final Dimension TWEET_OPTION_ICON_SIZE = new Dimension(24, 24);
    public static final Dimension TWEET_TEXTAREA_SIZE = new Dimension(512, 45);
}
