package net.nokok.twitduke.model.thread;

import java.io.File;
import net.nokok.twitduke.util.Threads;

/**
 * 指定したファイルが作成されるのを待ちます
 */
public class FileCreateWatcher extends Thread implements Runnable {

    private File         watchingFile;
    private IFileWatcher watcher;

    public FileCreateWatcher(String path, IFileWatcher watcher) {
        this.watchingFile = new File(path);
        this.watcher = watcher;
    }

    @Override
    public void run() {
        while (true) {
            if (watchingFile.exists()) {
                watcher.filesCreated();
                break;
            } else {
                Threads.sleep(this, 3000);
            }
        }
    }
}
