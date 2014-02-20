package net.nokok.twitduke.model.thread;

public abstract class AbstractSyncronizer {
    protected boolean isAvailable = true;

    public synchronized void lock() {
        while (!isAvailable) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        isAvailable = false;
    }

    public synchronized void unlock() {
        isAvailable = true;
        notify();
    }
}
