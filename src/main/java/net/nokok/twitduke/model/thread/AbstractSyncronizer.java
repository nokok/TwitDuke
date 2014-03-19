package net.nokok.twitduke.model.thread;

abstract class AbstractSyncronizer {
    private boolean isAvailable = true;

    synchronized void lock() {
        while (!isAvailable) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        isAvailable = false;
    }

    synchronized void unlock() {
        isAvailable = true;
        notify();
    }
}
