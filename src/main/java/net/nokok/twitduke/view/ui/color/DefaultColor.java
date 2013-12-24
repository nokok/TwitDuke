package net.nokok.twitduke.view.ui.color;

import java.awt.*;

public class DefaultColor {
    public static final Color DEFAULT_BACKGROUND = new Color(30, 30, 30);

    public static class TweetCell {
        public static final Color DEFAULT_BACKGROUND   = new Color(40, 40, 40);
        public static final Color DEFAULT_FOREGROUND   = new Color(200, 200, 200);
        public static final Color RETWEETED_BACKGROUND = new Color(50, 60, 50);
        public static final Color FAVORITED_BACKGROUND = new Color(100, 100, 50);
        public static final Color MENTION_BACKGROUND   = new Color(90, 50, 50);
    }

    public static class TWButton {
        public static final Color DEFAULT_FOREGROUND = new Color(225, 231, 235);
        public static final Color DEFAULT_BACKGROUND = new Color(44, 62, 80);
    }

    public static class TWPanel {
        public static final Color DEFAULT_BACKGROUND = new Color(30, 30, 30);
    }
}
