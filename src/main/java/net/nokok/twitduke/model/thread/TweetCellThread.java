package net.nokok.twitduke.model.thread;

import net.nokok.twitduke.main.Config;
import net.nokok.twitduke.model.factory.TweetCellFactory;
import net.nokok.twitduke.view.MainView;
import net.nokok.twitduke.view.TweetCell;
import twitter4j.Status;

public class TweetCellThread extends Thread {
    private TweetCellThreadSyncronizer tweetCellThreadSyncronizer = TweetCellThreadSyncronizer.getInstance();

    private final MainView         mainView;
    private final TweetCellFactory factory;
    private final Status           status;

    public TweetCellThread(MainView mainView, TweetCellFactory factory, Status status) {
        this.mainView = mainView;
        this.factory = factory;
        this.status = status;
    }

    @Override
    public synchronized void run() {
        tweetCellThreadSyncronizer.lock();
        TweetCell cell = factory.createTweetCell(status);
        if (!mainView.isScrollbarTop()) {
            mainView.shiftScrollBar((int) cell.getPreferredSize().getHeight());
        }
        mainView.insertTweetCell(cell);
        if (cell.isMention()) {
            if ((status.getText().contains("QT") || status.getText().contains("RT")) && Config.IS_MUTE_UNOFFICIAL_RT) {
                tweetCellThreadSyncronizer.unlock();
                return;
            }
            mainView.insertMentionTweetCell(factory.createTweetCell(status));
        }
        tweetCellThreadSyncronizer.unlock();
    }
}
