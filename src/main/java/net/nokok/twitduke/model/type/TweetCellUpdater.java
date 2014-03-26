package net.nokok.twitduke.model.type;

public class TweetCellUpdater {
    public final long           id;
    public final UpdateCategory category;

    public TweetCellUpdater(long id, UpdateCategory category) {
        this.id = id;
        this.category = category;
    }

    @Override
    public String toString() {
        return id + "," + category;
    }
}
