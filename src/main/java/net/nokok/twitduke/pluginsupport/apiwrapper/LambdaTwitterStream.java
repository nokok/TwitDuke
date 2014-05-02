/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.nokok.twitduke.pluginsupport.apiwrapper;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusAdapter;
import twitter4j.StatusDeletionNotice;
import twitter4j.TwitterStream;

/**
 *
 * @author noko < nokok.kz at gmail.com >
 */
public class LambdaTwitterStream {

    private final TwitterStream twitterStream;

    public LambdaTwitterStream(TwitterStream twitterStream) {
        this.twitterStream = twitterStream;
    }

    public void onDeletionNotice(EventWithSingleArg<StatusDeletionNotice> s) {
        this.twitterStream.addListener(new StatusAdapter() {
            @Override
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
                s.onEvent(statusDeletionNotice);
            }
        });
    }

    public void onException(EventWithSingleArg<Exception> e) {
        this.twitterStream.addListener(new StatusAdapter() {
            @Override
            public void onException(Exception ex) {
                e.onEvent(ex);
            }
        });
    }

    public void ScrubGeo(EventWithDoubleArg<Long, Long> s) {
        this.twitterStream.addListener(new StatusAdapter() {
            @Override
            public void onScrubGeo(long userId, long upToStatusId) {
                s.onEvent(userId, upToStatusId);
            }
        });
    }

    public void onStallWarning(EventWithSingleArg<StallWarning> w) {
        this.twitterStream.addListener(new StatusAdapter() {
            @Override
            public void onStallWarning(StallWarning warning) {
                w.onEvent(warning);
            }
        });
    }

    public void onStatus(EventWithSingleArg<Status> s) {
        this.twitterStream.addListener(new StatusAdapter() {
            @Override
            public void onStatus(Status status) {
                s.onEvent(status);
            }
        });
    }

    public void onTrackLimitationNotice(EventWithSingleArg<Integer> i) {
        this.twitterStream.addListener(new StatusAdapter() {
            @Override
            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
                i.onEvent(numberOfLimitedStatuses);
            }
        });
    }
}
