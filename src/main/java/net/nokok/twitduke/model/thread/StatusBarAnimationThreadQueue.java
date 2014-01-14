package net.nokok.twitduke.model.thread;

import java.util.LinkedList;
import java.util.Queue;
import net.nokok.twitduke.util.Threads;

/**
 * ステータスバーのアニメーションスレッドをキューで管理します
 */
public class StatusBarAnimationThreadQueue extends Thread implements Runnable {

    private final Queue<StatusBarAnimationInvoker> eventQueue = new LinkedList<>();

    @Override
    public void run() {
        Thread currentThread;
        while ((currentThread = eventQueue.poll()) != null) {
            currentThread.start();
            Threads.join(this, 50);
        }
    }

    public void addEvent(StatusBarAnimationInvoker thread) {
        eventQueue.offer(thread);
        this.run();
    }
}
