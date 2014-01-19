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

    public static void join(Thread wantToJoinThread, long milliseconds) {
        try {
            wantToJoinThread.join(milliseconds);
        } catch (InterruptedException ignored) {

        }
    }
}
