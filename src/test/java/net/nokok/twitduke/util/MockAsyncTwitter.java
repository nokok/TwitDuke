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

import java.io.File;
import java.io.InputStream;
import twitter4j.AsyncTwitter;
import twitter4j.GeoLocation;
import twitter4j.GeoQuery;
import twitter4j.OEmbedRequest;
import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.RateLimitStatusListener;
import twitter4j.StatusUpdate;
import twitter4j.TwitterException;
import twitter4j.TwitterListener;
import twitter4j.auth.AccessToken;
import twitter4j.auth.Authorization;
import twitter4j.auth.OAuth2Token;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;

public class MockAsyncTwitter implements AsyncTwitter {

    private static final long serialVersionUID = -2693773352392324621L;

    @Override
    public void addListener(TwitterListener listener) {
    }

    @Override
    public void addRateLimitStatusListener(RateLimitStatusListener listener) {
    }

    @Override
    public void createBlock(long userId) {
    }

    @Override
    public void createBlock(String screenName) {
    }

    @Override
    public void createFavorite(long id) {
    }

    @Override
    public void createFriendship(long userId) {
    }

    @Override
    public void createFriendship(String screenName) {
    }

    @Override
    public void createFriendship(long userId, boolean follow) {
    }

    @Override
    public void createFriendship(String screenName, boolean follow) {
    }

    @Override
    public void createPlace(String name, String containedWithin, String token, GeoLocation location, String streetAddress) {
    }

    @Override
    public void createSavedSearch(String query) {
    }

    @Override
    public void createUserList(String listName, boolean isPublicList, String description) {
    }

    @Override
    public void createUserListMember(long listId, long userId) {
    }

    @Override
    public void createUserListMember(long ownerId, String slug, long userId) {
    }

    @Override
    public void createUserListMembers(long listId, long[] userIds) {
    }

    @Override
    public void createUserListMembers(long ownerId, String slug, long[] userIds) {
    }

    @Override
    public void createUserListMembers(long listId, String[] screenNames) {
    }

    @Override
    public void createUserListMembers(long ownerId, String slug, String[] screenNames) {
    }

    @Override
    public void createUserListSubscription(long listId) {
    }

    @Override
    public void createUserListSubscription(long ownerId, String slug) {
    }

    @Override
    public void destroyBlock(long userId) {
    }

    @Override
    public void destroyBlock(String screenName) {
    }

    @Override
    public void destroyDirectMessage(long id) {
    }

    @Override
    public void destroyFavorite(long id) {
    }

    @Override
    public void destroyFriendship(long userId) {
    }

    @Override
    public void destroyFriendship(String screenName) {
    }

    @Override
    public void destroySavedSearch(int id) {
    }

    @Override
    public void destroyStatus(long statusId) {
    }

    @Override
    public void destroyUserList(long listId) {
    }

    @Override
    public void destroyUserList(long ownerId, String slug) {
    }

    @Override
    public void destroyUserListMember(long listId, long userId) {
    }

    @Override
    public void destroyUserListMember(long ownerId, String slug, long userId) {
    }

    @Override
    public void destroyUserListSubscription(long listId) {
    }

    @Override
    public void destroyUserListSubscription(long ownerId, String slug) {
    }

    @Override
    public void getAPIConfiguration() {
    }

    @Override
    public void getAccountSettings() {
    }

    @Override
    public Authorization getAuthorization() {
        return null;
    }

    @Override
    public void getAvailableTrends() {
    }

    @Override
    public void getBlocksIDs() {
    }

    @Override
    public void getBlocksIDs(long cursor) {
    }

    @Override
    public void getBlocksList() {
    }

    @Override
    public void getBlocksList(long cursor) {
    }

    @Override
    public void getClosestTrends(GeoLocation location) throws TwitterException {
    }

    @Override
    public Configuration getConfiguration() {
        return null;
    }

    @Override
    public void getContributees(long userId) {
    }

    @Override
    public void getContributees(String screenName) {
    }

    @Override
    public void getContributors(long userId) {
    }

    @Override
    public void getContributors(String screenName) {
    }

    @Override
    public void getDirectMessages() {
    }

    @Override
    public void getDirectMessages(Paging paging) {
    }

    @Override
    public void getFavorites() {
    }

    @Override
    public void getFavorites(long id) {
    }

    @Override
    public void getFavorites(String screenName) {
    }

    @Override
    public void getFavorites(Paging paging) {
    }

    @Override
    public void getFavorites(long userId, Paging paging) {
    }

    @Override
    public void getFavorites(String screenName, Paging paging) {
    }

    @Override
    public void getFollowersIDs(long cursor) {
    }

    @Override
    public void getFollowersIDs(long userId, long cursor) {
    }

    @Override
    public void getFollowersIDs(String screenName, long cursor) {
    }

    @Override
    public void getFollowersList(long userId, long cursor) {
    }

    @Override
    public void getFollowersList(String screenName, long cursor) {
    }

    @Override
    public void getFriendsIDs(long cursor) {
    }

    @Override
    public void getFriendsIDs(long userId, long cursor) {
    }

    @Override
    public void getFriendsIDs(String screenName, long cursor) {
    }

    @Override
    public void getFriendsList(long userId, long cursor) {
    }

    @Override
    public void getFriendsList(String screenName, long cursor) {
    }

    @Override
    public void getGeoDetails(String id) {
    }

    @Override
    public void getHomeTimeline() {
    }

    @Override
    public void getHomeTimeline(Paging paging) {
    }

    @Override
    public long getId() throws TwitterException, IllegalStateException {
        return -1;
    }

    @Override
    public void getIncomingFriendships(long cursor) {
    }

    @Override
    public void getLanguages() {
    }

    @Override
    public void getMemberSuggestions(String categorySlug) {
    }

    @Override
    public void getMentions() {
    }

    @Override
    public void getMentions(Paging paging) {
    }

    @Override
    public OAuth2Token getOAuth2Token() throws TwitterException {
        return null;
    }

    @Override
    public void getOAuth2TokenAsync() {
    }

    @Override
    public AccessToken getOAuthAccessToken() throws TwitterException {
        return null;
    }

    @Override
    public AccessToken getOAuthAccessToken(String oauthVerifier) throws TwitterException {
        return null;
    }

    @Override
    public AccessToken getOAuthAccessToken(RequestToken requestToken) throws TwitterException {
        return null;
    }

    @Override
    public AccessToken getOAuthAccessToken(RequestToken requestToken, String oauthVerifier) throws TwitterException {
        return null;
    }

    @Override
    public AccessToken getOAuthAccessToken(String screenName, String password) throws TwitterException {
        return null;
    }

    @Override
    public void getOAuthAccessTokenAsync() {
    }

    @Override
    public void getOAuthAccessTokenAsync(String oauthVerifier) {
    }

    @Override
    public void getOAuthAccessTokenAsync(RequestToken requestToken) {
    }

    @Override
    public void getOAuthAccessTokenAsync(RequestToken requestToken, String oauthVerifier) {
    }

    @Override
    public void getOAuthAccessTokenAsync(String screenName, String password) {
    }

    @Override
    public RequestToken getOAuthRequestToken() throws TwitterException {
        return null;
    }

    @Override
    public RequestToken getOAuthRequestToken(String callbackURL) throws TwitterException {
        return null;
    }

    @Override
    public RequestToken getOAuthRequestToken(String callbackURL, String xAuthAccessType) throws TwitterException {
        return null;
    }

    @Override
    public void getOAuthRequestTokenAsync() {
    }

    @Override
    public void getOAuthRequestTokenAsync(String callbackURL) {
    }

    @Override
    public void getOAuthRequestTokenAsync(String callbackURL, String xAuthAccessType) {
    }

    @Override
    public void getOEmbed(OEmbedRequest req) {
    }

    @Override
    public void getOutgoingFriendships(long cursor) {
    }

    @Override
    public void getPlaceTrends(int woeid) {
    }

    @Override
    public void getPrivacyPolicy() {
    }

    @Override
    public void getRateLimitStatus() {
    }

    @Override
    public void getRateLimitStatus(String... resources) {
    }

    @Override
    public void getRetweets(long statusId) {
    }

    @Override
    public void getRetweetsOfMe() {
    }

    @Override
    public void getRetweetsOfMe(Paging paging) {
    }

    @Override
    public void getSavedSearches() {
    }

    @Override
    public String getScreenName() throws TwitterException, IllegalStateException {
        return null;
    }

    @Override
    public void getSentDirectMessages() {
    }

    @Override
    public void getSentDirectMessages(Paging paging) {
    }

    @Override
    public void getSimilarPlaces(GeoLocation location, String name, String containedWithin, String streetAddress) {
    }

    @Override
    public void getSuggestedUserCategories() {
    }

    @Override
    public void getTermsOfService() {
    }

    @Override
    public void getUserListMembers(long listId, long cursor) {
    }

    @Override
    public void getUserListMembers(long ownerId, String slug, long cursor) {
    }

    @Override
    public void getUserListMemberships(long cursor) {
    }

    @Override
    public void getUserListMemberships(long listMemberId, long cursor) {
    }

    @Override
    public void getUserListMemberships(String listMemberScreenName, long cursor) {
    }

    @Override
    public void getUserListMemberships(long listMemberId, long cursor, boolean filterToOwnedLists) {
    }

    @Override
    public void getUserListMemberships(String listMemberScreenName, long cursor, boolean filterToOwnedLists) {
    }

    @Override
    public void getUserListStatuses(long listId, Paging paging) {
    }

    @Override
    public void getUserListStatuses(long ownerId, String slug, Paging paging) {
    }

    @Override
    public void getUserListSubscribers(long listId, long cursor) {
    }

    @Override
    public void getUserListSubscribers(long ownerId, String slug, long cursor) {
    }

    @Override
    public void getUserListSubscriptions(String listOwnerScreenName, long cursor) {
    }

    @Override
    public void getUserLists(String listOwnerScreenName) {
    }

    @Override
    public void getUserLists(long listOwnerUserId) {
    }

    @Override
    public void getUserSuggestions(String categorySlug) {
    }

    @Override
    public void getUserTimeline(String screenName, Paging paging) {
    }

    @Override
    public void getUserTimeline(long userId, Paging paging) {
    }

    @Override
    public void getUserTimeline(Paging paging) {
    }

    @Override
    public void getUserTimeline(String screenName) {
    }

    @Override
    public void getUserTimeline(long userId) {
    }

    @Override
    public void getUserTimeline() {
    }

    @Override
    public void invalidateOAuth2Token() throws TwitterException {
    }

    @Override
    public void lookupFriendships(long[] ids) {
    }

    @Override
    public void lookupFriendships(String[] screenNames) {
    }

    @Override
    public void lookupUsers(long[] ids) {
    }

    @Override
    public void lookupUsers(String[] screenNames) {
    }

    @Override
    public void removeProfileBanner() {
    }

    @Override
    public void reportSpam(long userId) {
    }

    @Override
    public void reportSpam(String screenName) {
    }

    @Override
    public void retweetStatus(long statusId) {
    }

    @Override
    public void reverseGeoCode(GeoQuery query) {
    }

    @Override
    public void search(Query query) {
    }

    @Override
    public void searchPlaces(GeoQuery query) {
    }

    @Override
    public void searchUsers(String query, int page) {
    }

    @Override
    public void sendDirectMessage(long userId, String text) {
    }

    @Override
    public void sendDirectMessage(String screenName, String text) {
    }

    @Override
    public void setOAuth2Token(OAuth2Token oauth2Token) {
    }

    @Override
    public void setOAuthAccessToken(AccessToken accessToken) {
    }

    @Override
    public void setOAuthConsumer(String consumerKey, String consumerSecret) {
    }

    @Override
    public void showDirectMessage(long id) {
    }

    @Override
    public void showFriendship(long sourceId, long targetId) {
    }

    @Override
    public void showFriendship(String sourceScreenName, String targetScreenName) {
    }

    @Override
    public void showSavedSearch(int id) {
    }

    @Override
    public void showStatus(long id) {
    }

    @Override
    public void showUser(long userId) {
    }

    @Override
    public void showUser(String screenName) {
    }

    @Override
    public void showUserList(long listId) {
    }

    @Override
    public void showUserList(long ownerId, String slug) {
    }

    @Override
    public void showUserListMembership(long listId, long userId) {
    }

    @Override
    public void showUserListMembership(long ownerId, String slug, long userId) {
    }

    @Override
    public void showUserListSubscription(long listId, long userId) {
    }

    @Override
    public void showUserListSubscription(long ownerId, String slug, long userId) {
    }

    @Override
    public void shutdown() {
    }

    @Override
    public void updateAccountSettings(Integer trendLocationWoeid, Boolean sleepTimeEnabled, String startSleepTime, String endSleepTime, String timeZone, String lang) {
    }

    @Override
    public void updateFriendship(long userId, boolean enableDeviceNotification, boolean retweets) {
    }

    @Override
    public void updateFriendship(String screenName, boolean enableDeviceNotification, boolean retweets) {
    }

    @Override
    public void updateProfile(String name, String url, String location, String description) {
    }

    @Override
    public void updateProfileBackgroundImage(File image, boolean tile) {
    }

    @Override
    public void updateProfileBackgroundImage(InputStream image, boolean tile) {
    }

    @Override
    public void updateProfileBanner(File image) {
    }

    @Override
    public void updateProfileBanner(InputStream image) {
    }

    @Override
    public void updateProfileColors(String profileBackgroundColor, String profileTextColor, String profileLinkColor, String profileSidebarFillColor, String profileSidebarBorderColor) {
    }

    @Override
    public void updateProfileImage(File image) {
    }

    @Override
    public void updateProfileImage(InputStream image) {
    }

    @Override
    public void updateStatus(String status) {
    }

    @Override
    public void updateStatus(StatusUpdate status) {
    }

    @Override
    public void updateUserList(long listId, String newListName, boolean isPublicList, String newDescription) {
    }

    @Override
    public void updateUserList(long ownerId, String slug, String newListName, boolean isPublicList, String newDescription) {
    }

    @Override
    public void verifyCredentials() {
    }

}
