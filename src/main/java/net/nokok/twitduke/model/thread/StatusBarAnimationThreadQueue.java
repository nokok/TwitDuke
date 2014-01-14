package net.nokok.twitduke.model.thread;

import java.util.LinkedList;
import java.util.Queue;

/**
 * ステータスバーのアニメーションスレッドをキューで管理します
 */
public class StatusBarAnimationThreadQueue extends Thread implements Runnable {

    private final Queue<StatusBarAnimationInvoker> eventQueue = new LinkedList<>();

    @Override
    public void run() {
        Thread currentThread;
        while ((currentThread = eventQueue.poll()) != null) {
            try {
                currentThread.start();
                currentThread.join(50);
            } catch (InterruptedException e) {

            }
        }
    }

    public void addEvent(StatusBarAnimationInvoker thread) {
        eventQueue.offer(thread);
        this.run();
    }
}
