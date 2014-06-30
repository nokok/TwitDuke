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
    private final RuntimeException ex = new RuntimeException(new TwitterException("テスト用のオブジェクトです"));

    @Override
    public void addListener(TwitterListener listener) {
        throw ex;
    }

    @Override
    public void addRateLimitStatusListener(RateLimitStatusListener listener) {
        throw ex;
    }

    @Override
    public void createBlock(long userId) {
        throw ex;
    }

    @Override
    public void createBlock(String screenName) {
        throw ex;
    }

    @Override
    public void createFavorite(long id) {
        throw ex;
    }

    @Override
    public void createFriendship(long userId) {
        throw ex;
    }

    @Override
    public void createFriendship(String screenName) {
        throw ex;
    }

    @Override
    public void createFriendship(long userId, boolean follow) {
        throw ex;
    }

    @Override
    public void createFriendship(String screenName, boolean follow) {
    }

    @Override
    public void createPlace(String name, String containedWithin, String token, GeoLocation location, String streetAddress) {
        throw ex;
    }

    @Override
    public void createSavedSearch(String query) {
        throw ex;
    }

    @Override
    public void createUserList(String listName, boolean isPublicList, String description) {
        throw ex;
    }

    @Override
    public void createUserListMember(long listId, long userId) {
        throw ex;
    }

    @Override
    public void createUserListMember(long ownerId, String slug, long userId) {
        throw ex;
    }

    @Override
    public void createUserListMembers(long listId, long[] userIds) {
        throw ex;
    }

    @Override
    public void createUserListMembers(long ownerId, String slug, long[] userIds) {
        throw ex;
    }

    @Override
    public void createUserListMembers(long listId, String[] screenNames) {
        throw ex;
    }

    @Override
    public void createUserListMembers(long ownerId, String slug, String[] screenNames) {
        throw ex;
    }

    @Override
    public void createUserListSubscription(long listId) {
        throw ex;
    }

    @Override
    public void createUserListSubscription(long ownerId, String slug) {
        throw ex;
    }

    @Override
    public void destroyBlock(long userId) {
        throw ex;
    }

    @Override
    public void destroyBlock(String screenName) {
        throw ex;
    }

    @Override
    public void destroyDirectMessage(long id) {
        throw ex;
    }

    @Override
    public void destroyFavorite(long id) {
        throw ex;
    }

    @Override
    public void destroyFriendship(long userId) {
        throw ex;
    }

    @Override
    public void destroyFriendship(String screenName) {
        throw ex;
    }

    @Override
    public void destroySavedSearch(int id) {
        throw ex;
    }

    @Override
    public void destroyStatus(long statusId) {
        throw ex;
    }

    @Override
    public void destroyUserList(long listId) {
        throw ex;
    }

    @Override
    public void destroyUserList(long ownerId, String slug) {
        throw ex;
    }

    @Override
    public void destroyUserListMember(long listId, long userId) {
        throw ex;
    }

    @Override
    public void destroyUserListMember(long ownerId, String slug, long userId) {
        throw ex;
    }

    @Override
    public void destroyUserListSubscription(long listId) {
        throw ex;
    }

    @Override
    public void destroyUserListSubscription(long ownerId, String slug) {
        throw ex;
    }

    @Override
    public void getAPIConfiguration() {
        throw ex;
    }

    @Override
    public void getAccountSettings() {
        throw ex;
    }

    @Override
    public Authorization getAuthorization() {
        throw ex;
    }

    @Override
    public void getAvailableTrends() {
        throw ex;
    }

    @Override
    public void getBlocksIDs() {
        throw ex;
    }

    @Override
    public void getBlocksIDs(long cursor) {
        throw ex;
    }

    @Override
    public void getBlocksList() {
        throw ex;
    }

    @Override
    public void getBlocksList(long cursor) {
        throw ex;
    }

    @Override
    public void getClosestTrends(GeoLocation location) throws TwitterException {
        throw ex;
    }

    @Override
    public Configuration getConfiguration() {
        throw ex;
    }

    @Override
    public void getContributees(long userId) {
        throw ex;
    }

    @Override
    public void getContributees(String screenName) {
        throw ex;
    }

    @Override
    public void getContributors(long userId) {
        throw ex;
    }

    @Override
    public void getContributors(String screenName) {
        throw ex;
    }

    @Override
    public void getDirectMessages() {
        throw ex;
    }

    @Override
    public void getDirectMessages(Paging paging) {
        throw ex;
    }

    @Override
    public void getFavorites() {
        throw ex;
    }

    @Override
    public void getFavorites(long id) {
        throw ex;
    }

    @Override
    public void getFavorites(String screenName) {
        throw ex;
    }

    @Override
    public void getFavorites(Paging paging) {
        throw ex;
    }

    @Override
    public void getFavorites(long userId, Paging paging) {
        throw ex;
    }

    @Override
    public void getFavorites(String screenName, Paging paging) {
        throw ex;
    }

    @Override
    public void getFollowersIDs(long cursor) {
        throw ex;
    }

    @Override
    public void getFollowersIDs(long userId, long cursor) {
        throw ex;
    }

    @Override
    public void getFollowersIDs(String screenName, long cursor) {
        throw ex;
    }

    @Override
    public void getFollowersList(long userId, long cursor) {
        throw ex;
    }

    @Override
    public void getFollowersList(String screenName, long cursor) {
        throw ex;
    }

    @Override
    public void getFriendsIDs(long cursor) {
        throw ex;
    }

    @Override
    public void getFriendsIDs(long userId, long cursor) {
        throw ex;
    }

    @Override
    public void getFriendsIDs(String screenName, long cursor) {
        throw ex;
    }

    @Override
    public void getFriendsList(long userId, long cursor) {
        throw ex;
    }

    @Override
    public void getFriendsList(String screenName, long cursor) {
        throw ex;
    }

    @Override
    public void getGeoDetails(String id) {
        throw ex;
    }

    @Override
    public void getHomeTimeline() {
        throw ex;
    }

    @Override
    public void getHomeTimeline(Paging paging) {
        throw ex;
    }

    @Override
    public long getId() throws TwitterException, IllegalStateException {
        throw ex;
    }

    @Override
    public void getIncomingFriendships(long cursor) {
        throw ex;
    }

    @Override
    public void getLanguages() {
        throw ex;
    }

    @Override
    public void getMemberSuggestions(String categorySlug) {
        throw ex;
    }

    @Override
    public void getMentions() {
        throw ex;
    }

    @Override
    public void getMentions(Paging paging) {
        throw ex;
    }

    @Override
    public OAuth2Token getOAuth2Token() throws TwitterException {
        throw ex;
    }

    @Override
    public void getOAuth2TokenAsync() {
        throw ex;
    }

    @Override
    public AccessToken getOAuthAccessToken() throws TwitterException {
        throw ex;
    }

    @Override
    public AccessToken getOAuthAccessToken(String oauthVerifier) throws TwitterException {
        throw ex;
    }

    @Override
    public AccessToken getOAuthAccessToken(RequestToken requestToken) throws TwitterException {
        throw ex;
    }

    @Override
    public AccessToken getOAuthAccessToken(RequestToken requestToken, String oauthVerifier) throws TwitterException {
        throw ex;
    }

    @Override
    public AccessToken getOAuthAccessToken(String screenName, String password) throws TwitterException {
        throw ex;
    }

    @Override
    public void getOAuthAccessTokenAsync() {
        throw ex;
    }

    @Override
    public void getOAuthAccessTokenAsync(String oauthVerifier) {
        throw ex;
    }

    @Override
    public void getOAuthAccessTokenAsync(RequestToken requestToken) {
        throw ex;
    }

    @Override
    public void getOAuthAccessTokenAsync(RequestToken requestToken, String oauthVerifier) {
        throw ex;
    }

    @Override
    public void getOAuthAccessTokenAsync(String screenName, String password) {
        throw ex;
    }

    @Override
    public RequestToken getOAuthRequestToken() throws TwitterException {
        throw ex;
    }

    @Override
    public RequestToken getOAuthRequestToken(String callbackURL) throws TwitterException {
        throw ex;
    }

    @Override
    public RequestToken getOAuthRequestToken(String callbackURL, String xAuthAccessType) throws TwitterException {
        throw ex;
    }

    @Override
    public void getOAuthRequestTokenAsync() {
        throw ex;
    }

    @Override
    public void getOAuthRequestTokenAsync(String callbackURL) {
        throw ex;
    }

    @Override
    public void getOAuthRequestTokenAsync(String callbackURL, String xAuthAccessType) {
        throw ex;
    }

    @Override
    public void getOEmbed(OEmbedRequest req) {
        throw ex;
    }

    @Override
    public void getOutgoingFriendships(long cursor) {
        throw ex;
    }

    @Override
    public void getPlaceTrends(int woeid) {
        throw ex;
    }

    @Override
    public void getPrivacyPolicy() {
        throw ex;
    }

    @Override
    public void getRateLimitStatus() {
        throw ex;
    }

    @Override
    public void getRateLimitStatus(String... resources) {
        throw ex;
    }

    @Override
    public void getRetweets(long statusId) {
        throw ex;
    }

    @Override
    public void getRetweetsOfMe() {
        throw ex;
    }

    @Override
    public void getRetweetsOfMe(Paging paging) {
        throw ex;
    }

    @Override
    public void getSavedSearches() {
        throw ex;
    }

    @Override
    public String getScreenName() throws TwitterException, IllegalStateException {
        throw ex;
    }

    @Override
    public void getSentDirectMessages() {
        throw ex;
    }

    @Override
    public void getSentDirectMessages(Paging paging) {
        throw ex;
    }

    @Override
    public void getSimilarPlaces(GeoLocation location, String name, String containedWithin, String streetAddress) {
        throw ex;
    }

    @Override
    public void getSuggestedUserCategories() {
        throw ex;
    }

    @Override
    public void getTermsOfService() {
        throw ex;
    }

    @Override
    public void getUserListMembers(long listId, long cursor) {
        throw ex;
    }

    @Override
    public void getUserListMembers(long ownerId, String slug, long cursor) {
        throw ex;
    }

    @Override
    public void getUserListMemberships(long cursor) {
        throw ex;
    }

    @Override
    public void getUserListMemberships(long listMemberId, long cursor) {
        throw ex;
    }

    @Override
    public void getUserListMemberships(String listMemberScreenName, long cursor) {
        throw ex;
    }

    @Override
    public void getUserListMemberships(long listMemberId, long cursor, boolean filterToOwnedLists) {
        throw ex;
    }

    @Override
    public void getUserListMemberships(String listMemberScreenName, long cursor, boolean filterToOwnedLists) {
        throw ex;
    }

    @Override
    public void getUserListStatuses(long listId, Paging paging) {
        throw ex;
    }

    @Override
    public void getUserListStatuses(long ownerId, String slug, Paging paging) {
        throw ex;
    }

    @Override
    public void getUserListSubscribers(long listId, long cursor) {
        throw ex;
    }

    @Override
    public void getUserListSubscribers(long ownerId, String slug, long cursor) {
        throw ex;
    }

    @Override
    public void getUserListSubscriptions(String listOwnerScreenName, long cursor) {
        throw ex;
    }

    @Override
    public void getUserLists(String listOwnerScreenName) {
        throw ex;
    }

    @Override
    public void getUserLists(long listOwnerUserId) {
        throw ex;
    }

    @Override
    public void getUserSuggestions(String categorySlug) {
        throw ex;
    }

    @Override
    public void getUserTimeline(String screenName, Paging paging) {
        throw ex;
    }

    @Override
    public void getUserTimeline(long userId, Paging paging) {
        throw ex;
    }

    @Override
    public void getUserTimeline(Paging paging) {
        throw ex;
    }

    @Override
    public void getUserTimeline(String screenName) {
        throw ex;
    }

    @Override
    public void getUserTimeline(long userId) {
        throw ex;
    }

    @Override
    public void getUserTimeline() {
        throw ex;
    }

    @Override
    public void invalidateOAuth2Token() throws TwitterException {
        throw ex;
    }

    @Override
    public void lookupFriendships(long[] ids) {
        throw ex;
    }

    @Override
    public void lookupFriendships(String[] screenNames) {
        throw ex;
    }

    @Override
    public void lookupUsers(long[] ids) {
        throw ex;
    }

    @Override
    public void lookupUsers(String[] screenNames) {
        throw ex;
    }

    @Override
    public void removeProfileBanner() {
        throw ex;
    }

    @Override
    public void reportSpam(long userId) {
        throw ex;
    }

    @Override
    public void reportSpam(String screenName) {
        throw ex;
    }

    @Override
    public void retweetStatus(long statusId) {
        throw ex;
    }

    @Override
    public void reverseGeoCode(GeoQuery query) {
        throw ex;
    }

    @Override
    public void search(Query query) {
        throw ex;
    }

    @Override
    public void searchPlaces(GeoQuery query) {
        throw ex;
    }

    @Override
    public void searchUsers(String query, int page) {
        throw ex;
    }

    @Override
    public void sendDirectMessage(long userId, String text) {
        throw ex;
    }

    @Override
    public void sendDirectMessage(String screenName, String text) {
        throw ex;
    }

    @Override
    public void setOAuth2Token(OAuth2Token oauth2Token) {
        throw ex;
    }

    @Override
    public void setOAuthAccessToken(AccessToken accessToken) {
        throw ex;
    }

    @Override
    public void setOAuthConsumer(String consumerKey, String consumerSecret) {
        throw ex;
    }

    @Override
    public void showDirectMessage(long id) {
        throw ex;
    }

    @Override
    public void showFriendship(long sourceId, long targetId) {
        throw ex;
    }

    @Override
    public void showFriendship(String sourceScreenName, String targetScreenName) {
        throw ex;
    }

    @Override
    public void showSavedSearch(int id) {
        throw ex;
    }

    @Override
    public void showStatus(long id) {
        throw ex;
    }

    @Override
    public void showUser(long userId) {
        throw ex;
    }

    @Override
    public void showUser(String screenName) {
        throw ex;
    }

    @Override
    public void showUserList(long listId) {
        throw ex;
    }

    @Override
    public void showUserList(long ownerId, String slug) {
        throw ex;
    }

    @Override
    public void showUserListMembership(long listId, long userId) {
        throw ex;
    }

    @Override
    public void showUserListMembership(long ownerId, String slug, long userId) {
        throw ex;
    }

    @Override
    public void showUserListSubscription(long listId, long userId) {
        throw ex;
    }

    @Override
    public void showUserListSubscription(long ownerId, String slug, long userId) {
        throw ex;
    }

    @Override
    public void shutdown() {
        throw ex;
    }

    @Override
    public void updateAccountSettings(Integer trendLocationWoeid, Boolean sleepTimeEnabled, String startSleepTime, String endSleepTime, String timeZone, String lang) {
        throw ex;
    }

    @Override
    public void updateFriendship(long userId, boolean enableDeviceNotification, boolean retweets) {
        throw ex;
    }

    @Override
    public void updateFriendship(String screenName, boolean enableDeviceNotification, boolean retweets) {
        throw ex;
    }

    @Override
    public void updateProfile(String name, String url, String location, String description) {
        throw ex;
    }

    @Override
    public void updateProfileBackgroundImage(File image, boolean tile) {
        throw ex;
    }

    @Override
    public void updateProfileBackgroundImage(InputStream image, boolean tile) {
        throw ex;
    }

    @Override
    public void updateProfileBanner(File image) {
        throw ex;
    }

    @Override
    public void updateProfileBanner(InputStream image) {
        throw ex;
    }

    @Override
    public void updateProfileColors(String profileBackgroundColor, String profileTextColor, String profileLinkColor, String profileSidebarFillColor, String profileSidebarBorderColor) {
        throw ex;
    }

    @Override
    public void updateProfileImage(File image) {
        throw ex;
    }

    @Override
    public void updateProfileImage(InputStream image) {
        throw ex;
    }

    @Override
    public void updateStatus(String status) {
        throw ex;
    }

    @Override
    public void updateStatus(StatusUpdate status) {
        throw ex;
    }

    @Override
    public void updateUserList(long listId, String newListName, boolean isPublicList, String newDescription) {
        throw ex;
    }

    @Override
    public void updateUserList(long ownerId, String slug, String newListName, boolean isPublicList, String newDescription) {
        throw ex;
    }

    @Override
    public void verifyCredentials() {
        throw ex;
    }

}
