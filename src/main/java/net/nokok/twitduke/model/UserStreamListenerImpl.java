package net.nokok.twitduke.model;

import net.nokok.twitduke.controller.MainViewController;
import net.nokok.twitduke.util.URLExpander;
import twitter4j.DirectMessage;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.User;
import twitter4j.UserList;
import twitter4j.UserStreamListener;

public class UserStreamListenerImpl implements UserStreamListener {

    private MainViewController mainViewController;

    public UserStreamListenerImpl(MainViewController mainViewController) {
        this.mainViewController = mainViewController;
    }

    @Override
    public void onStatus(Status status) {
        mainViewController.insertTweetCell(status);
    }

    @Override
    public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {

    }

    @Override
    public void onTrackLimitationNotice(int numberOfLimitedStatuses) {

    }

    @Override
    public void onScrubGeo(long userId, long upToStatusId) {

    }

    @Override
    public void onStallWarning(StallWarning warning) {

    }


    @Override
    public void onDeletionNotice(long directMessageId, long userId) {

    }

    @Override
    public void onFriendList(long[] friendIds) {

    }

    @Override
    public void onFavorite(User source, User target, Status favoritedStatus) {
        mainViewController.setStatus("★" + URLExpander.extendURL(favoritedStatus) + "が" + source.getScreenName() + "にお気に入り登録されました");
    }

    @Override
    public void onUnfavorite(User source, User target, Status unfavoritedStatus) {
        mainViewController.setStatus("☆" + source.getScreenName() + "が" + URLExpander.extendURL(unfavoritedStatus) + "のお気に入り登録を解除しました");
    }

    @Override
    public void onFollow(User source, User followedUser) {
        mainViewController.setStatus(source.getScreenName() + "が" + followedUser.getScreenName() + "をフォローしました");
    }

    @Override
    public void onDirectMessage(DirectMessage directMessage) {

    }

    @Override
    public void onUserListMemberAddition(User addedMember, User listOwner, UserList list) {

    }

    @Override
    public void onUserListMemberDeletion(User deletedMember, User listOwner, UserList list) {

    }

    @Override
    public void onUserListSubscription(User subscriber, User listOwner, UserList list) {

    }

    @Override
    public void onUserListUnsubscription(User subscriber, User listOwner, UserList list) {

    }

    @Override
    public void onUserListCreation(User listOwner, UserList list) {

    }

    @Override
    public void onUserListUpdate(User listOwner, UserList list) {

    }

    @Override
    public void onUserListDeletion(User listOwner, UserList list) {

    }

    @Override
    public void onUserProfileUpdate(User updatedUser) {

    }

    @Override
    public void onBlock(User source, User blockedUser) {

    }

    @Override
    public void onUnblock(User source, User unblockedUser) {

    }

    @Override
    public void onException(Exception ex) {
        mainViewController.setStatus(ex.getMessage());
    }
}
