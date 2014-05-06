/*
 * The MIT License
 *
 * Copyright 2014 noko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
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

    public void debugStream() {
        this.twitterStream.sample();
    }
}
