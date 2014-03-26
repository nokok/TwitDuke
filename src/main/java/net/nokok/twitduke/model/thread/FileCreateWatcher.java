package net.nokok.twitduke.model.thread;

import java.io.File;
import net.nokok.twitduke.main.Config;
import net.nokok.twitduke.util.ThreadUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 指定したファイルが作成されるのを3秒間隔で監視し、作成されたらfilesCreatedが呼ばれます
 */
public class FileCreateWatcher extends Thread {

    private final Log logger = LogFactory.getLog(FileCreateWatcher.class);

    private final File         watchingFile;
    private final IFileWatcher watcher;

    public FileCreateWatcher(String path, IFileWatcher watcher) {
        watchingFile = new File(path);
        this.watcher = watcher;
    }

    @Override
    public void run() {
        logger.info(watchingFile.getPath() + "の監視を開始します");

        while (true) {
            if (watchingFile.exists()) {
                logger.debug("ファイルが作成されました");

                watcher.filesCreated();
                break;
            } else {
                logger.debug("ファイルが作成されていません。" + Config.FILE_CHECK_INTERVAL + "ms待ちます");
                ThreadUtil.sleep(this, Config.FILE_CHECK_INTERVAL);
            }
        }
    }
}
