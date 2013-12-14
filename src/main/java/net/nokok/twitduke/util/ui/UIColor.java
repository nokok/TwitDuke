package net.nokok.twitduke.util.ui;

import java.awt.*;

public class UIColor {

    public static class TweetCell {
        public static final Color DEFAULT_BACKGROUND   = new Color(50, 50, 50);
        public static final Color DEFAULT_FOREGROUND   = new Color(200, 200, 200);
        public static final Color RETWEETED_BACKGROUND = new Color(50, 70, 50);
    }

    public static class CommonButton {
        public static final Color DEFAULT_FOREGROUND = new Color(250, 250, 250);
        public static final Color DEFAULT_BACKGROUND = new Color(10, 10, 50);
    }

    public static class TweetPanel {
        public static final Color DEFAULT_BACKGROUND = new Color(30, 30, 30);
    }

    public static class Toolbar {
        public static final Color DEFAULT_FOREGROUND = TweetCell.DEFAULT_FOREGROUND;
        public static final Color DEFAULT_BACKGROUND = TweetCell.DEFAULT_BACKGROUND;
    }
}
