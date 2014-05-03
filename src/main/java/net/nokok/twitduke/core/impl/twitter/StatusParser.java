/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.nokok.twitduke.core.impl.twitter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import twitter4j.HashtagEntity;
import twitter4j.MediaEntity;
import twitter4j.Status;
import twitter4j.SymbolEntity;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.URLEntity;
import twitter4j.UserMentionEntity;

/**
 *
 * @author noko < nokok.kz at gmail.com >
 */
public class StatusParser {

    private final Twitter twitter;

    public StatusParser(Twitter twitter) {
        this.twitter = twitter;
    }

    public LinkedList<Status> createReplyChain(Status status) {
        LinkedList<Status> linkedList = new LinkedList<>();
        Status currentStatus = status;
        long currentStatusId;
        while ( (currentStatusId = currentStatus.getInReplyToStatusId()) > 0 ) {
            linkedList.add(currentStatus);
            try {
                currentStatus = twitter.showStatus(currentStatusId);
            } catch (TwitterException e) {
                return linkedList;
            }
        }
        return linkedList;
    }

    public Iterable<Status> createReplyChain(long statusId) {
        LinkedList<Status> linkedList = new LinkedList<>();
        try {
            Status status = twitter.showStatus(statusId);
            linkedList.add(status);
            linkedList.addAll(createReplyChain(status));
            return linkedList;
        } catch (TwitterException ex) {
            return linkedList;
        }
    }

    public List<URLEntity> createURLEntityList(Status status) {
        return Stream.of(status.getURLEntities())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public List<UserMentionEntity> createUserMentionEntityList(Status status) {
        return Stream.of(status.getUserMentionEntities())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public List<HashtagEntity> createHashtagEntityList(Status status) {
        return Stream.of(status.getHashtagEntities())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public List<MediaEntity> createMediaEntityList(Status status) {
        return Stream.of(status.getMediaEntities())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public List<SymbolEntity> createSymbolEntityList(Status status) {
        return Stream.of(status.getSymbolEntities())
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
