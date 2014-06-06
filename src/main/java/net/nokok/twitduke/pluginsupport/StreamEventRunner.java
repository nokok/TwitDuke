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
package net.nokok.twitduke.pluginsupport;

import java.util.List;
import net.nokok.twitduke.pluginsupport.plugin.Plugin;
import net.nokok.twitduke.pluginsupport.plugin.PluginRegistrable;
import twitter4j.DirectMessage;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.User;
import twitter4j.UserList;
import twitter4j.UserStreamListener;

public class StreamEventRunner implements UserStreamListener, StatusListener, PluginRegistrable {

    private final EventRunner eventRunner = new EventRunner(PluginObjectName.STREAM);

    public StreamEventRunner() {

    }

    public StreamEventRunner(List<Plugin> plugins) {
        plugins.forEach(eventRunner::addPlugin);
    }

    @Override
    public void addPlugin(Plugin p) {
        eventRunner.addPlugin(p);
    }

    @Override
    public void onBlock(User source, User blockedUser) {
        eventRunner.invokeAll("onBlock", source, blockedUser);
    }

    @Override
    public void onDeletionNotice(long directMessageId, long userId) {
        eventRunner.invokeAll("onDeletionNotice", directMessageId, userId);
    }

    @Override
    public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
        eventRunner.invokeAll("onDeletionNotice", statusDeletionNotice);
    }

    @Override
    public void onDirectMessage(DirectMessage directMessage) {
        eventRunner.invokeAll("onDirectMessage", directMessage);
    }

    @Override
    public void onException(Exception ex) {
        eventRunner.invokeAll("onException", ex);
    }

    @Override
    public void onFavorite(User source, User target, Status favoritedStatus) {
        eventRunner.invokeAll("onFavorite", source, target, favoritedStatus);
    }

    @Override
    public void onFollow(User source, User followedUser) {
        eventRunner.invokeAll("onFollow", source, followedUser);
    }

    @Override
    public void onFriendList(long[] friendIds) {
        eventRunner.invokeAll("onFriendList", friendIds);
    }

    @Override
    public void onScrubGeo(long userId, long upToStatusId) {
        eventRunner.invokeAll("onScrubGeo", userId, upToStatusId);
    }

    @Override
    public void onStallWarning(StallWarning warning) {
        eventRunner.invokeAll("onStallWarning", warning);
    }

    @Override
    public void onStatus(Status status) {
        eventRunner.invokeAll("onStatus", status);
    }

    @Override
    public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
        eventRunner.invokeAll("onTrackLimitationNotice", numberOfLimitedStatuses);
    }

    @Override
    public void onUnblock(User source, User unblockedUser) {
        eventRunner.invokeAll("onUnblock", source, unblockedUser);
    }

    @Override
    public void onUnfavorite(User source, User target, Status unfavoritedStatus) {
        eventRunner.invokeAll("onUnfavorite", source, target, unfavoritedStatus);
    }

    @Override
    public void onUnfollow(User source, User unfollowedUser) {
        eventRunner.invokeAll("onUnFollow", source, unfollowedUser);
    }

    @Override
    public void onUserListCreation(User listOwner, UserList list) {
        eventRunner.invokeAll("onUserListCreation", listOwner, list);
    }

    @Override
    public void onUserListDeletion(User listOwner, UserList list) {
        eventRunner.invokeAll("onUserListDeletion", listOwner, list);
    }

    @Override
    public void onUserListMemberAddition(User addedMember, User listOwner, UserList list) {
        eventRunner.invokeAll("onUserListMemberAddition", addedMember, listOwner, list);
    }

    @Override
    public void onUserListMemberDeletion(User deletedMember, User listOwner, UserList list) {
        eventRunner.invokeAll("onUserListMemberDeletion", deletedMember, listOwner, list);
    }

    @Override
    public void onUserListSubscription(User subscriber, User listOwner, UserList list) {
        eventRunner.invokeAll("onUserListSubscription", subscriber, listOwner, list);
    }

    @Override
    public void onUserListUnsubscription(User subscriber, User listOwner, UserList list) {
        eventRunner.invokeAll("onUserListUnsubscription", subscriber, listOwner, list);
    }

    @Override
    public void onUserListUpdate(User listOwner, UserList list) {
        eventRunner.invokeAll("onUserListUpdate", listOwner, list);
    }

    @Override
    public void onUserProfileUpdate(User updatedUser) {
        eventRunner.invokeAll("onUserProfileUpdate", updatedUser);
    }

}
