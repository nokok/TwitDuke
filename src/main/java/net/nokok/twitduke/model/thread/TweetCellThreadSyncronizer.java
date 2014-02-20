package net.nokok.twitduke.model.thread;

public class TweetCellThreadSyncronizer extends AbstractSyncronizer {

    private static TweetCellThreadSyncronizer instance = new TweetCellThreadSyncronizer();

    private TweetCellThreadSyncronizer() {

    }

    public static TweetCellThreadSyncronizer getInstance() {
        return instance;
    }
}
