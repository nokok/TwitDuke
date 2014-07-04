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

import net.nokok.twitduke.core.factory.TTwitterStreamFactory;
import net.nokok.twitduke.core.twitter.TwitterExceptionReceivable;
import net.nokok.twitduke.core.type.ThrowableReceivable;
import net.nokok.twitduke.pluginsupport.event.Event2;
import net.nokok.twitduke.pluginsupport.event.Event;
import net.nokok.twitduke.pluginsupport.event.Event3;
import twitter4j.DirectMessage;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusAdapter;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.User;
import twitter4j.UserList;
import twitter4j.UserStreamAdapter;
import twitter4j.auth.AccessToken;

/**
 * ラムダ式で記述可能なTwitterStreamのラッパークラスです。
 */
public class LambdaTwitterStream implements TwitterExceptionReceivable {

    private final TwitterStream twitterStream;

    /**
     * 指定されたアクセストークンでTwitterStreamを生成します。
     *
     * @param accessToken アクセストークン
     */
    public LambdaTwitterStream(AccessToken accessToken) {
        this.twitterStream = TTwitterStreamFactory.newInstance(accessToken);
    }

    public void addListener(StatusListener statusListener) {
        this.twitterStream.addListener(statusListener);
    }

    public void onDeletionNotice(Event<StatusDeletionNotice> s) {
        this.twitterStream.addListener(new StatusAdapter() {
            @Override
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
                s.onEvent(statusDeletionNotice);
            }
        });
    }

    @Override
    public void onException(ThrowableReceivable receiver) {
        this.twitterStream.addListener(new StatusAdapter() {
            @Override
            public void onException(Exception ex) {
                receiver.onError(ex);
            }
        });
    }

    public void ScrubGeo(Event2<Long, Long> s) {
        this.twitterStream.addListener(new StatusAdapter() {
            @Override
            public void onScrubGeo(long userId, long upToStatusId) {
                s.onEvent(userId, upToStatusId);
            }
        });
    }

    public void onStallWarning(Event<StallWarning> w) {
        this.twitterStream.addListener(new StatusAdapter() {
            @Override
            public void onStallWarning(StallWarning warning) {
                w.onEvent(warning);
            }
        });
    }

    public void onStatus(Event<Status> s) {
        this.twitterStream.addListener(new StatusAdapter() {
            @Override
            public void onStatus(Status status) {
                s.onEvent(status);
            }
        });
    }

    public void onStatusUser(Event<User> u) {
        this.twitterStream.addListener(new StatusAdapter() {

            @Override
            public void onStatus(Status status) {
                u.onEvent(status.getUser());
            }

        });
    }

    public void onStatusScreenName(Event<String> s) {
        this.twitterStream.addListener(new StatusAdapter() {

            @Override
            public void onStatus(Status status) {
                s.onEvent(status.getUser().getScreenName());
            }

        });
    }

    public void onTrackLimitationNotice(Event<Integer> i) {
        this.twitterStream.addListener(new StatusAdapter() {
            @Override
            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
                i.onEvent(numberOfLimitedStatuses);
            }
        });
    }

    public void onBlock(Event2<User, User> b) {
        this.twitterStream.addListener(new UserStreamAdapter() {
            @Override
            public void onBlock(User source, User blockedUser) {
                b.onEvent(source, blockedUser);
            }
        });
    }

    public void onUnBlock(Event2<User, User> arg) {
        this.twitterStream.addListener(new UserStreamAdapter() {
            @Override
            public void onUnblock(User source, User unblockedUser) {
                arg.onEvent(source, unblockedUser);
            }
        });
    }

    public void onFavorite(Event3<User, User, Status> arg) {
        this.twitterStream.addListener(new UserStreamAdapter() {
            @Override
            public void onFavorite(User source, User target, Status favoritedStatus) {
                arg.onEvent(source, target, favoritedStatus);
            }
        });
    }

    public void onUnfavorite(Event3<User, User, Status> arg) {
        this.twitterStream.addListener(new UserStreamAdapter() {
            @Override
            public void onUnfavorite(User source, User target, Status unfavoritedStatus) {
                arg.onEvent(source, target, unfavoritedStatus);
            }
        });
    }

    public void onFollow(Event2<User, User> arg) {
        this.twitterStream.addListener(new UserStreamAdapter() {
            @Override
            public void onFollow(User source, User followedUser) {
                arg.onEvent(source, followedUser);
            }
        });
    }

    public void onDirectMessage(Event<DirectMessage> arg) {
        this.twitterStream.addListener(new UserStreamAdapter() {
            @Override
            public void onDirectMessage(DirectMessage directMessage) {
                arg.onEvent(directMessage);
            }
        });
    }

    public void onUserListMenberAddition(Event3<User, User, UserList> arg) {
        this.twitterStream.addListener(new UserStreamAdapter() {
            @Override
            public void onUserListMemberAddition(User addedMember, User listOwner, UserList list) {
                arg.onEvent(addedMember, listOwner, list);
            }
        });
    }

    public void onUserListMemberDeletion(Event3<User, User, UserList> arg) {
        this.twitterStream.addListener(new UserStreamAdapter() {
            @Override
            public void onUserListMemberDeletion(User deletedMember, User listOwner, UserList list) {
                arg.onEvent(deletedMember, listOwner, list);
            }
        });
    }

    public void onUserListSubscription(Event3<User, User, UserList> arg) {
        this.twitterStream.addListener(new UserStreamAdapter() {
            @Override
            public void onUserListSubscription(User subscriber, User listOwner, UserList list) {
                arg.onEvent(subscriber, listOwner, list);
            }
        });
    }

    public void onUserListUnsubscription(Event3<User, User, UserList> arg) {
        this.twitterStream.addListener(new UserStreamAdapter() {
            @Override
            public void onUserListUnsubscription(User subscriber, User listOwner, UserList list) {
                arg.onEvent(subscriber, listOwner, list);
            }
        });
    }

    public void onUserListCreation(Event2<User, UserList> arg) {
        this.twitterStream.addListener(new UserStreamAdapter() {
            @Override
            public void onUserListCreation(User listOwner, UserList list) {
                arg.onEvent(listOwner, list);
            }
        });
    }

    public void onUserListUpdate(Event2<User, UserList> arg) {
        this.twitterStream.addListener(new UserStreamAdapter() {
            @Override
            public void onUserListUpdate(User listOwner, UserList list) {
                arg.onEvent(listOwner, list);
            }
        });
    }

    public void onUserProfileUpdate(Event<User> arg) {
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
