package net.nokok.twitduke.components.keyevent;

import java.util.Arrays;

/**
 * Created by a13612 on 2014/07/23.
 */
public class ValidateUtil {

    /**
     * 受け取った配列に、nullがあれば例外を発生させる
     *
     * @param args
     * @throws IllegalArgumentException
     */
    public static void notNull(final Object... args) throws IllegalArgumentException {
        Arrays.stream(args).forEach(
                v -> {
                    if ( v == null ) {
                        throw new IllegalArgumentException(Arrays.asList(args).toString());
                    }
                }
        );
    }

    /**
     * 受け取った文字列配列に、nullか空文字列があれば例外を発生させる
     *
     * @param args
     * @throws IllegalArgumentException
     */
    public static void notNullAndEmpty(final Object... args) throws IllegalArgumentException {
        notNull(args);
        Arrays.stream(args).forEach(
                str -> {
                    if ( str.toString().isEmpty() ) {
                        throw new IllegalArgumentException(str.toString());
                    }
                }
        );
    }
}
