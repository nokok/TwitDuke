package net.nokok.twitduke.model;

import net.nokok.twitduke.model.factory.TweetCellFactory;
import net.nokok.twitduke.view.MainView;
import twitter4j.Status;
import twitter4j.UserStreamAdapter;

public class MyUserStreamAdapter extends UserStreamAdapter {

    private MainView         view;
    private TweetCellFactory factory;

    public MyUserStreamAdapter(MainView view, TweetCellFactory factory) {
        this.view = view;
        this.factory = factory;
    }

    @Override
    public void onStatus(Status status) {
        view.insertTweetCell(factory.createTweetCell(status));
    }
}
