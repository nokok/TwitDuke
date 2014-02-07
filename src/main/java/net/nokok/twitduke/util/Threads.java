package net.nokok.twitduke.util;

/**
 * スレッドのsleepメソッドとjoinメソッドのInterrupterExceptionをキャッチし、使いやすくするクラスです
 */
public class Threads {

    public static void sleep(Thread wantToStopThread, long milliseconds) {
        try {
            wantToStopThread.sleep(milliseconds);
        } catch (InterruptedException ignored) {

        }
    }
}
