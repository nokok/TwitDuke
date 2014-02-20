package net.nokok.twitduke.model.thread;

public class AnimationThreadSyncronizer extends AbstractSyncronizer {

    private static AnimationThreadSyncronizer instance = new AnimationThreadSyncronizer();

    private AnimationThreadSyncronizer() {

    }

    public static AnimationThreadSyncronizer getInstance() {
        return instance;
    }
}
