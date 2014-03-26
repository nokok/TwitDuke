package net.nokok.twitduke.model.thread;

import org.apache.commons.logging.LogFactory;

abstract class AbstractSyncronizer {
    private boolean isAvailable = true;

    synchronized void lock() {
        while (!isAvailable) {
            try {
                wait();
            } catch (InterruptedException ignored) {
                LogFactory.getLog(AbstractSyncronizer.class).debug("割り込みが発生しました", ignored);
            }
        }
        isAvailable = false;
    }

    synchronized void unlock() {
        isAvailable = true;
        notify();
    }
}
