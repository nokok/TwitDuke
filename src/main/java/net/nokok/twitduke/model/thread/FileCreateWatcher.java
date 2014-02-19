package net.nokok.twitduke.model.thread;

import java.io.File;
import net.nokok.twitduke.main.Config;
import net.nokok.twitduke.util.ThreadUtil;

/**
 * 指定したファイルが作成されるのを3秒間隔で監視し、作成されたらfilesCreatedが呼ばれます
 */
public class FileCreateWatcher extends Thread implements Runnable {

    private final File         watchingFile;
    private final IFileWatcher watcher;

    public FileCreateWatcher(String path, IFileWatcher watcher) {
        watchingFile = new File(path);
        this.watcher = watcher;
    }

    @Override
    public void run() {
        while (true) {
            if (watchingFile.exists()) {
                watcher.filesCreated();
                break;
            } else {
                ThreadUtil.sleep(this, Config.FILE_CHECK_INTERVAL);
            }
        }
    }
}
