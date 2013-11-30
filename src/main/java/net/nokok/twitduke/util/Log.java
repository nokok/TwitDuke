package net.nokok.twitduke.util;

public class Log {
    private static final Log instance = new Log();
    private static StringBuilder builder;

    private Log() {
        builder = new StringBuilder();
    }

    public static Log getInstance() {
        return instance;
    }

    public static void write(String text) {
        builder.append(text);
    }
}
