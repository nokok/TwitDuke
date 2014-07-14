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
package net.nokok.twitduke.util;

import java.util.Map;
import twitter4j.AccountSettings;
import twitter4j.Category;
import twitter4j.DirectMessage;
import twitter4j.Friendship;
import twitter4j.IDs;
import twitter4j.Location;
import twitter4j.OEmbed;
import twitter4j.PagableResponseList;
import twitter4j.Place;
import twitter4j.QueryResult;
import twitter4j.RateLimitStatus;
import twitter4j.Relationship;
import twitter4j.ResponseList;
import twitter4j.SavedSearch;
import twitter4j.Status;
import twitter4j.Trends;
import twitter4j.TwitterAPIConfiguration;
import twitter4j.TwitterException;
import twitter4j.TwitterListener;
import twitter4j.TwitterMethod;
import twitter4j.User;
import twitter4j.UserList;
import twitter4j.api.HelpResources;
import twitter4j.auth.AccessToken;
import twitter4j.auth.OAuth2Token;
import twitter4j.auth.RequestToken;

public class MockTwitterListener implements TwitterListener {

    @Override
    public void checkedUserListMembership(User users) {
    }

    @Override
    public void checkedUserListSubscription(User user) {
    }

    @Override
    public void createdBlock(User user) {
    }

    @Override
    public void createdFavorite(Status status) {
    }

    @Override
    public void createdFriendship(User user) {
    }

    @Override
    public void createdMute(User user) {
    }

    @Override
    public void createdSavedSearch(SavedSearch savedSearch) {
    }

    @Override
    public void createdUserList(UserList userList) {
    }

    @Override
    public void createdUserListMember(UserList userList) {
    }

    @Override
    public void createdUserListMembers(UserList userList) {
    }

    @Override
    public void destroyedBlock(User user) {
    }

    @Override
    public void destroyedDirectMessage(DirectMessage message) {
    }

    @Override
    public void destroyedFavorite(Status status) {
    }

    @Override
    public void destroyedFriendship(User user) {
    }

    @Override
    public void destroyedMute(User user) {
    }

    @Override
    public void destroyedSavedSearch(SavedSearch savedSearch) {
    }

    @Override
    public void destroyedStatus(Status destroyedStatus) {
    }

    @Override
    public void destroyedUserList(UserList userList) {
    }

    @Override
    public void destroyedUserListMember(UserList userList) {
    }

    @Override
    public void gotAPIConfiguration(TwitterAPIConfiguration conf) {
    }

    @Override
    public void gotAccountSettings(AccountSettings settings) {
    }

    @Override
    public void gotAvailableTrends(ResponseList<Location> locations) {
    }

    @Override
    public void gotBlockIDs(IDs blockingUsersIDs) {
    }

    @Override
    public void gotBlocksList(ResponseList<User> blockingUsers) {
    }

    @Override
    public void gotClosestTrends(ResponseList<Location> locations) {
    }

    @Override
    public void gotContributees(ResponseList<User> users) {
    }

    @Override
    public void gotContributors(ResponseList<User> users) {
    }

    @Override
    public void gotDirectMessage(DirectMessage message) {
    }

    @Override
    public void gotDirectMessages(ResponseList<DirectMessage> messages) {
    }

    @Override
    public void gotFavorites(ResponseList<Status> statuses) {
    }

    @Override
    public void gotFollowersIDs(IDs ids) {
    }

    @Override
    public void gotFollowersList(PagableResponseList<User> users) {
    }

    @Override
    public void gotFriendsIDs(IDs ids) {
    }

    @Override
    public void gotFriendsList(PagableResponseList<User> users) {
    }

    @Override
    public void gotGeoDetails(Place place) {
    }

    @Override
    public void gotHomeTimeline(ResponseList<Status> statuses) {
    }

    @Override
    public void gotIncomingFriendships(IDs ids) {
    }

    @Override
    public void gotLanguages(ResponseList<HelpResources.Language> languages) {
    }

    @Override
    public void gotMemberSuggestions(ResponseList<User> users) {
    }

    @Override
    public void gotMentions(ResponseList<Status> statuses) {
    }

    @Override
    public void gotMuteIDs(IDs ids) {
    }

    @Override
    public void gotMutesList(ResponseList<User> rl) {
    }

    @Override
    public void gotOAuth2Token(OAuth2Token token) {
    }

    @Override
    public void gotOAuthAccessToken(AccessToken token) {
    }

    @Override
    public void gotOAuthRequestToken(RequestToken token) {
    }

    @Override
    public void gotOEmbed(OEmbed oembed) {
    }

    @Override
    public void gotOutgoingFriendships(IDs ids) {
    }

    @Override
    public void gotPlaceTrends(Trends trends) {
    }

    @Override
    public void gotPrivacyPolicy(String privacyPolicy) {
    }

    @Override
    public void gotRateLimitStatus(Map<String, RateLimitStatus> rateLimitStatus) {
    }

    @Override
    public void gotRetweets(ResponseList<Status> retweets) {
    }

    @Override
    public void gotRetweetsOfMe(ResponseList<Status> statuses) {
    }

    @Override
    public void gotReverseGeoCode(ResponseList<Place> places) {
    }

    @Override
    public void gotSavedSearch(SavedSearch savedSearch) {
    }

    @Override
    public void gotSavedSearches(ResponseList<SavedSearch> savedSearches) {
    }

    @Override
    public void gotSentDirectMessages(ResponseList<DirectMessage> messages) {
    }

    @Override
    public void gotShowFriendship(Relationship relationship) {
    }

    @Override
    public void gotShowStatus(Status status) {
    }

    @Override
    public void gotShowUserList(UserList userList) {
    }

    @Override
    public void gotSimilarPlaces(ResponseList<Place> rl) {
    }

    @Override
    public void gotSuggestedUserCategories(ResponseList<Category> category) {
    }

    @Override
    public void gotTermsOfService(String tof) {
    }

    @Override
    public void gotUserDetail(User user) {
    }

    @Override
    public void gotUserListMembers(PagableResponseList<User> users) {
    }

    @Override
    public void gotUserListMemberships(PagableResponseList<UserList> userLists) {
    }

    @Override
    public void gotUserListStatuses(ResponseList<Status> statuses) {
    }

    @Override
    public void gotUserListSubscribers(PagableResponseList<User> users) {
    }

    @Override
    public void gotUserListSubscriptions(PagableResponseList<UserList> userLists) {
    }

    @Override
    public void gotUserLists(ResponseList<UserList> userLists) {
    }

    @Override
    public void gotUserSuggestions(ResponseList<User> users) {
    }

    @Override
    public void gotUserTimeline(ResponseList<Status> statuses) {
    }

    @Override
    public void lookedUpFriendships(ResponseList<Friendship> friendships) {
    }

    @Override
    public void lookedup(ResponseList<Status> rl) {
    }

    @Override
    public void lookedupUsers(ResponseList<User> users) {
    }

    @Override
    public void onException(TwitterException te, TwitterMethod method) {
    }

    @Override
    public void removedProfileBanner() {
    }

    @Override
    public void reportedSpam(User reportedSpammer) {
    }

    @Override
    public void retweetedStatus(Status retweetedStatus) {
    }

    @Override
    public void searched(QueryResult queryResult) {
    }

    @Override
    public void searchedPlaces(ResponseList<Place> places) {
    }

    @Override
    public void searchedUser(ResponseList<User> userList) {
    }

    @Override
    public void sentDirectMessage(DirectMessage message) {
    }

    @Override
    public void subscribedUserList(UserList userList) {
    }

    @Override
    public void unsubscribedUserList(UserList userList) {
    }

    @Override
    public void updatedAccountSettings(AccountSettings settings) {
    }

    @Override
    public void updatedFriendship(Relationship relationship) {
    }

    @Override
    public void updatedProfile(User user) {
    }

    @Override
    public void updatedProfileBackgroundImage(User user) {
    }

    @Override
    public void updatedProfileBanner() {
    }

    @Override
    public void updatedProfileColors(User user) {
    }

    @Override
    public void updatedProfileImage(User user) {
    }

    @Override
    public void updatedStatus(Status status) {
    }

    @Override
    public void updatedUserList(UserList userList) {
    }

    @Override
    public void verifiedCredentials(User user) {
    }

}
