package net.nokok.twitduke.server.handlers;

import java.util.Random;
import java.util.stream.IntStream;

public class StringUtil {

    public static String appendRandomWhitespace(String text) {
        return appendRandomString(text, "　");
    }

    public static String appendRandomString(String text, String append) {
        StringBuilder stringBuilder = new StringBuilder(text);
        IntStream.range(0, new Random().nextInt(50)).mapToObj(i -> append).forEach(stringBuilder::append);
        return stringBuilder.toString();
    }
}
