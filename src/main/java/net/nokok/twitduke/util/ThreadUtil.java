package net.nokok.twitduke.util;

/**
 * スレッドのsleepメソッドとjoinメソッドのInterrupterExceptionをキャッチし、使いやすくするクラスです
 */
public class ThreadUtil {

    public static void sleep(Thread wantToStopThread, long milliseconds) {
        try {
            wantToStopThread.sleep(milliseconds);
        } catch (InterruptedException ignored) {

        }
    }
}
