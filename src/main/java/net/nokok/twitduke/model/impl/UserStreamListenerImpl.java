package net.nokok.twitduke.model.impl;

import net.nokok.twitduke.model.type.TweetCellUpdater;
import net.nokok.twitduke.model.type.UpdateCategory;
import net.nokok.twitduke.main.Config;
import net.nokok.twitduke.model.account.AccessTokenManager;
import net.nokok.twitduke.model.listener.CellInsertionListener;
import net.nokok.twitduke.model.listener.NotificationListener;
import net.nokok.twitduke.model.listener.TweetCellUpdateListener;
import net.nokok.twitduke.util.URLUtil;
import twitter4j.DirectMessage;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.User;
import twitter4j.UserList;
import twitter4j.UserStreamListener;

public class UserStreamListenerImpl implements UserStreamListener {

    private NotificationListener    notificationListener;
    private CellInsertionListener   cellInsertionListener;
    private TweetCellUpdateListener tweetCellUpdateListener;

    public void setNotificationListener(NotificationListener notificationListener) {
        this.notificationListener = notificationListener;
    }

    public void setCellInsertionListener(CellInsertionListener cellInsertionListener) {
        this.cellInsertionListener = cellInsertionListener;
    }

    public void setTweetCellUpdateListener(TweetCellUpdateListener tweetCellUpdateListener) {
        this.tweetCellUpdateListener = tweetCellUpdateListener;
    }

    @Override
    public void onStatus(Status status) {
        if (status.isRetweet() && isMe(status.getRetweetedStatus().getUser()) && Config.Flags.isShowRetweetNotification) {
            notificationListener.setNotification('「' + status.getRetweetedStatus().getText() + "」が @" + status.getUser().getScreenName() + " にリツイートされました");
        }
        cellInsertionListener.insertCell(status);
    }

    @Override
    public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
        tweetCellUpdateListener.updateTweetCellStatus(new TweetCellUpdater(statusDeletionNotice.getStatusId(), UpdateCategory.DELETED));
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
        if (isMe(source)) {
            tweetCellUpdateListener.updateTweetCellStatus(new TweetCellUpdater(favoritedStatus.getId(), UpdateCategory.FAVORITED));
            return;
        }
        if (!Config.Flags.isShowFavoriteNotification) {
            return;
        }
        notificationListener.setNotification('★' + URLUtil.extendURL(favoritedStatus) + " が @" + source.getScreenName() + " をお気に入り登録しました");
    }

    @Override
    public void onUnfavorite(User source, User target, Status unfavoritedStatus) {
        if (isMe(source)) {
            tweetCellUpdateListener.updateTweetCellStatus(new TweetCellUpdater(unfavoritedStatus.getId(), UpdateCategory.UNFAVORITED));
            return;
        }
        if (!Config.Flags.isShowFavoriteNotification) {
            return;
        }
        notificationListener.setNotification('☆' + source.getScreenName() + 'が' + URLUtil.extendURL(unfavoritedStatus) + "のお気に入り登録を解除しました");
    }

    @Override
    public void onFollow(User source, User followedUser) {
        if (isMe(source)) {
            return;
        }
        if (!Config.Flags.isShowFollowNotification) {
            return;
        }
        notificationListener.setNotification(source.getScreenName() + 'が' + followedUser.getScreenName() + "をフォローしました");
    }

    @Override
    public void onUnfollow(User user, User user2) {

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
        if (ex.getClass().equals(NullPointerException.class)) {
            return;
        }
        notificationListener.setNotification("エラーが発生しました" + ex);
    }

    private boolean isMe(User user) {
        return user.getScreenName().equals(AccessTokenManager.getInstance().getUserName());
    }
}
