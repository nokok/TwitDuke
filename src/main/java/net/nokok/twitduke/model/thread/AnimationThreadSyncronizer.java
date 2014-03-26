package net.nokok.twitduke.model.thread;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AnimationThreadSyncronizer extends AbstractSyncronizer {

    private final Log logger = LogFactory.getLog(AnimationThreadSyncronizer.class);

    private static final AnimationThreadSyncronizer instance = new AnimationThreadSyncronizer();
    private int threadCount;

    private AnimationThreadSyncronizer() {

    }

    public static AnimationThreadSyncronizer getInstance() {
        return instance;
    }

    @Override
    public void lock() {
        logger.info("ロックを開始します");

        threadCount++;
        super.lock();
    }

    @Override
    public void unlock() {
        logger.info("ロック解除されます");

        threadCount--;
        super.unlock();
    }

    public int getThreadCount() {
        return threadCount;
    }
}
