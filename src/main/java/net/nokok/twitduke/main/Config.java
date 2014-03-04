package net.nokok.twitduke.main;

import java.awt.Dimension;
import java.awt.Font;
import java.io.File;

public class Config {

    public static final String VERSION = "0.1.1 Alpha";

    public static final String TWITTER_CONSUMER_KEY    = "VOIW6nzPVPEGyILu0kgMRQ";
    public static final String TWITTER_CONSUMER_SECRET = "x42tjv2Xrzsi3p5hfiGSYSiNLfa7VZv8Ozd0VHEaQ";

    public static final int FILE_CHECK_INTERVAL = 3000;
    public static final int UPDATE_INTERVAL     = 1800000;

    public static final int SCROLL_SPEED              = 35;
    public static final int CACHE_SIZE                = 1000;
    public static final int JAVAJAVA_MAX_W_COUNT      = 30;
    public static final int ANIMATION_THREAD_CAPACITY = 10;


    public static class Flags {
        public static boolean isMuteUnOfficialRT         = true;
        public static boolean isShowFavoriteNotification = true;
        public static boolean isShowRetweetNotification  = true;
        public static boolean isShowFollowNotification   = true;
    }

    public static class FontConfig {
        public static final Font USER_NAME    = new Font("", Font.PLAIN, 13);
        public static final Font SCREEN_NAME  = new Font("", Font.BOLD, 11);
        public static final Font NOTIFICATION = new Font("", Font.BOLD, 12);
        public static final Font BUTTON       = new Font("", Font.PLAIN, 14);
        public static final Font SMALL_BUTTON = new Font("", Font.PLAIN, 12);
    }

    public static class ComponentSize {

        public static final Dimension RETWEET_USER_ICON        = new Dimension(15, 15);
        public static final Dimension FUNCTION_BUTTON          = new Dimension(17, 10);
        public static final Dimension SLIM_BUTTON              = new Dimension(120, 20);
        public static final Dimension SMALL_BUTTON             = new Dimension(70, 20);
        public static final Dimension MAINVIEW_DEFAULT         = new Dimension(530, 600);
        public static final Dimension TEXTFIELD                = new Dimension(530, 30);
        public static final Dimension STATUS_BAR               = new Dimension(530, 25);
        public static final int       CELL_SEPARATOR_HEIGHT    = 1;
        public static final int       THUMBNAIL_WIDTH          = 256;
        public static final int       FUNCTION_BUTTON_MARGIN   = 5;
        public static final int       BEFORE_USERNAME_MARGIN   = 5;
        public static final int       BEFORE_SCREENNAME_MARGIN = 5;
    }

    public static class Name {

        public static final String ACCESS_TOKEN_LIST_FILENAME = "authlist";
        public static final String ACCESS_TOKEN_PREFIX        = "token";
    }

    public static class Path {

        public static final String AUTH_DIRECTORY   = new File(new File("").getAbsolutePath(), "auth").getAbsolutePath();
        public static final String TOKENFILE_PREFIX = AUTH_DIRECTORY + File.separator + Name.ACCESS_TOKEN_PREFIX;
        public static final String TOKEN_LIST_FILE  = AUTH_DIRECTORY + File.separator + Name.ACCESS_TOKEN_LIST_FILENAME;
    }

    public static class AnimationWait {

        public static final int NOTIFICATION       = 3000;
        public static final int FAST_NOTIFICATION  = 500;
        public static final int NOTIFICATION_SPEED = 10;
        public static final int TITLE_SPEED        = 30;
    }
}
