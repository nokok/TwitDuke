package net.nokok.twitduke.model.thread;

public class AnimationThreadSyncronizer {

    private static AnimationThreadSyncronizer instance    = new AnimationThreadSyncronizer();
    private        boolean                    isAvailable = true;

    private AnimationThreadSyncronizer() {

    }

    public static AnimationThreadSyncronizer getInstance() {
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
