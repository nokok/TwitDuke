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
package net.nokok.twitduke.core.twitter;

import twitter4j.DirectMessage;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.User;
import twitter4j.UserStreamAdapter;

public class PrintUserStreamListener extends UserStreamAdapter {

    @Override
    public void onBlock(User source, User blockedUser) {
        System.out.println("onBlock: " + source.getScreenName() + "," + blockedUser.getScreenName());
    }

    @Override
    public void onDeletionNotice(long directMessageId, long userId) {
        System.out.println("onDeletionNotice: " + directMessageId + "," + userId);
    }

    @Override
    public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
        System.out.println("onDeletionNoticeStatus: " + statusDeletionNotice.getStatusId());
    }

    @Override
    public void onDirectMessage(DirectMessage directMessage) {
        System.out.println("onDirectMessage: " + directMessage.getSenderScreenName() + "," + directMessage.getText());
    }

    @Override
    public void onException(Exception ex) {
        System.out.println("onException: " + ex.toString());
    }

    @Override
    public void onFavorite(User source, User target, Status favoritedStatus) {
        System.out.println("onFavorite: " + source.getScreenName() + "," + target.getScreenName() + "," + favoritedStatus.getText());
    }

    @Override
    public void onFollow(User source, User followedUser) {
        System.out.println("onFollow: " + source.getScreenName() + "," + followedUser.getScreenName());
    }

    @Override
    public void onStallWarning(StallWarning warning) {
        System.out.println("onStallWarning: " + warning.getMessage());
    }

    @Override
    public void onStatus(Status status) {
        System.out.println("onStatus: " + status.getUser().getScreenName() + "," + status.getText());
    }

    @Override
    public void onUnblock(User source, User unblockedUser) {
        System.out.println("onUnblock: " + source.getScreenName() + "," + unblockedUser.getScreenName());
    }

    @Override
    public void onUnfavorite(User source, User target, Status unfavoritedStatus) {
        System.out.println("onUnfavorite: " + source.getScreenName() + "," + target.getScreenName() + "," + unfavoritedStatus.getText());
    }

    @Override
    public void onUnfollow(User source, User unfollowedUser) {
        System.out.println("onUnfollow: " + source.getScreenName() + "," + unfollowedUser.getScreenName());
    }
}
