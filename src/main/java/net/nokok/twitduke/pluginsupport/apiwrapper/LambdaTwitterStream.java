/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.nokok.twitduke.pluginsupport.apiwrapper;

import twitter4j.DirectMessage;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusAdapter;
import twitter4j.StatusDeletionNotice;
import twitter4j.TwitterStream;
import twitter4j.User;
import twitter4j.UserList;
import twitter4j.UserStreamAdapter;

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

    public void onRetweetedStatus(EventWithSingleArg<Status> s) {
        this.twitterStream.addListener(new StatusAdapter() {

            @Override
            public void onStatus(Status status) {
                if ( status.isRetweet() ) {
                    s.onEvent(status.getRetweetedStatus());
                }
            }

        });
    }

    public void onStatusUser(EventWithSingleArg<User> u) {
        this.twitterStream.addListener(new StatusAdapter() {

            @Override
            public void onStatus(Status status) {
                u.onEvent(status.getUser());
            }

        });
    }

    public void onStatusScreenName(EventWithSingleArg<String> s) {
        this.twitterStream.addListener(new StatusAdapter() {

            @Override
            public void onStatus(Status status) {
                s.onEvent(status.getUser().getScreenName());
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

    public void onBlock(EventWithDoubleArg<User, User> b) {
        this.twitterStream.addListener(new UserStreamAdapter() {
            @Override
            public void onBlock(User source, User blockedUser) {
                b.onEvent(source, blockedUser);
            }
        });
    }

    public void onUnBlock(EventWithDoubleArg<User, User> arg) {
        this.twitterStream.addListener(new UserStreamAdapter() {
            @Override
            public void onUnblock(User source, User unblockedUser) {
                arg.onEvent(source, unblockedUser);
            }
        });
    }

    public void onFavorite(EventWithTripleArg<User, User, Status> arg) {
        this.twitterStream.addListener(new UserStreamAdapter() {
            @Override
            public void onFavorite(User source, User target, Status favoritedStatus) {
                arg.onEvent(source, target, favoritedStatus);
            }
        });
    }

    public void onUnfavorite(EventWithTripleArg<User, User, Status> arg) {
        this.twitterStream.addListener(new UserStreamAdapter() {
            @Override
            public void onUnfavorite(User source, User target, Status unfavoritedStatus) {
                arg.onEvent(source, target, unfavoritedStatus);
            }
        });
    }

    public void onFollow(EventWithDoubleArg<User, User> arg) {
        this.twitterStream.addListener(new UserStreamAdapter() {
            @Override
            public void onFollow(User source, User followedUser) {
                arg.onEvent(source, followedUser);
            }
        });
    }

    public void onDirectMessage(EventWithSingleArg<DirectMessage> arg) {
        this.twitterStream.addListener(new UserStreamAdapter() {
            @Override
            public void onDirectMessage(DirectMessage directMessage) {
                arg.onEvent(directMessage);
            }
        });
    }

    public void onUserListMenberAddition(EventWithTripleArg<User, User, UserList> arg) {
        this.twitterStream.addListener(new UserStreamAdapter() {
            @Override
            public void onUserListMemberAddition(User addedMember, User listOwner, UserList list) {
                arg.onEvent(addedMember, listOwner, list);
            }
        });
    }

    public void onUserListMemberDeletion(EventWithTripleArg<User, User, UserList> arg) {
        this.twitterStream.addListener(new UserStreamAdapter() {
            @Override
            public void onUserListMemberDeletion(User deletedMember, User listOwner, UserList list) {
                arg.onEvent(deletedMember, listOwner, list);
            }
        });
    }

    public void onUserListSubscription(EventWithTripleArg<User, User, UserList> arg) {
        this.twitterStream.addListener(new UserStreamAdapter() {
            @Override
            public void onUserListSubscription(User subscriber, User listOwner, UserList list) {
                arg.onEvent(subscriber, listOwner, list);
            }
        });
    }

    public void onUserListUnsubscription(EventWithTripleArg<User, User, UserList> arg) {
        this.twitterStream.addListener(new UserStreamAdapter() {
            @Override
            public void onUserListUnsubscription(User subscriber, User listOwner, UserList list) {
                arg.onEvent(subscriber, listOwner, list);
            }
        });
    }

    public void onUserListCreation(EventWithDoubleArg<User, UserList> arg) {
        this.twitterStream.addListener(new UserStreamAdapter() {
            @Override
            public void onUserListCreation(User listOwner, UserList list) {
                arg.onEvent(listOwner, list);
            }
        });
    }

    public void onUserListUpdate(EventWithDoubleArg<User, UserList> arg) {
        this.twitterStream.addListener(new UserStreamAdapter() {
            @Override
            public void onUserListUpdate(User listOwner, UserList list) {
                arg.onEvent(listOwner, list);
            }
        });
    }

    public void onUserProfileUpdate(EventWithSingleArg<User> arg) {
        this.twitterStream.addListener(new UserStreamAdapter() {
            @Override
            public void onUserProfileUpdate(User updatedUser) {
                arg.onEvent(updatedUser);
            }
        });
    }

    public void startStream() {
        this.twitterStream.user();
    }
}
