package net.nokok.twitduke.model.thread;

public class AnimationThreadSyncronizer extends AbstractSyncronizer {

    private static AnimationThreadSyncronizer instance = new AnimationThreadSyncronizer();
    private int threadCount;

    private AnimationThreadSyncronizer() {

    }

    public static AnimationThreadSyncronizer getInstance() {
        return instance;
    }

    @Override
    public void lock() {
        threadCount++;
        super.lock();
    }

    @Override
    public void unlock() {
        threadCount--;
        super.unlock();
    }

    public int getThreadCount() {
        return threadCount;
    }
}
