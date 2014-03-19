package net.nokok.twitduke.model.thread;

abstract class AbstractSyncronizer {
    private boolean isAvailable = true;

    synchronized void lock() {
        while (!isAvailable) {
            try {
                wait();
            } catch (InterruptedException ignored) {

            }
        }
        isAvailable = false;
    }

    synchronized void unlock() {
        isAvailable = true;
        notify();
    }
}
