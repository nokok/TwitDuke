/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.nokok.twitduke.core.api.twitter;

import java.util.Objects;
import net.nokok.twitduke.core.type.Tweet;
import twitter4j.api.TweetsResourcesAsync;

public class TweetAPIImpl implements TweetAPI {

    private final TweetsResourcesAsync twitter;

    public TweetAPIImpl(TweetsResourcesAsync twitter) {
        this.twitter = Objects.requireNonNull(twitter);
    }

    @Override
    public void sendTweet(Tweet tweet) {
        twitter.updateStatus(tweet.toString());
    }
}
