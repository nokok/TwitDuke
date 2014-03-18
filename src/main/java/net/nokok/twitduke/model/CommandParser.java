package net.nokok.twitduke.model;

import twitter4j.AsyncTwitter;

public class CommandParser {

    private final AsyncTwitter twitter;

    public CommandParser(AsyncTwitter twitter) {
        this.twitter = twitter;
    }

    public void parse(String text) {

    }
}
