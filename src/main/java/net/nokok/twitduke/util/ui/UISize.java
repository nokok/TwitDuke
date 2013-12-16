package net.nokok.twitduke.util.ui;

import java.awt.*;

public class UISize {

    public static class MainWindow {
        public static final Dimension MINIMUM_SIZE   = new Dimension(530, 600);
        public static final Dimension TEXTFIELD_SIZE = new Dimension(530, 30);
    }

    public static class TweetCell {
        public static final Dimension DEFAULT_ICON_SIZE              = new Dimension(50, 50);
        public static final Dimension DEFAULT_RETWEET_USER_ICON_SIZE = new Dimension(15, 15);
        public static final Dimension DEFAULT_CELL_SIZE              = new Dimension(505, 50);
    }
}
