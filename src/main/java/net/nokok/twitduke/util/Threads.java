package net.nokok.twitduke.util;

public class Threads {

    public static void sleep(Thread wantToStopThread, long milliseconds) {
        try {
            wantToStopThread.sleep(milliseconds);
        } catch (InterruptedException e) {

        }
    }

    public static void join(Thread wantToJoinThread, long milliseconds) {
        try {
            wantToJoinThread.join(milliseconds);
        } catch (InterruptedException e) {

        }
    }
}
