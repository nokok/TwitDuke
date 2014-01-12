package net.nokok.twitduke.model.thread;

import java.io.File;
import net.nokok.twitduke.main.Main;

public class BootFileWatcher extends Thread implements Runnable {

    private File watchingFile;
    private Main main;

    public BootFileWatcher(String path, Main main) {
        this.watchingFile = new File(path);
        this.main = main;
    }

    @Override
    public void run() {
        while (true) {
            if (watchingFile.exists()) {
                main.writeAccessTokenFilesFinished();
                break;
            } else {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {

                }
            }
        }
    }
}
