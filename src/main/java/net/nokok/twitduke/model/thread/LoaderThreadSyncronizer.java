package net.nokok.twitduke.model.thread;

public class LoaderThreadSyncronizer {

    private static LoaderThreadSyncronizer instance    = new LoaderThreadSyncronizer();
    private        boolean                 isAvailable = true;

    private LoaderThreadSyncronizer() {

    }

    public static LoaderThreadSyncronizer getInstance() {
        return instance;
    }

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
