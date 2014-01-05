package net.nokok.twitduke.model;

import twitter4j.auth.AccessToken;

import java.util.ArrayList;

public class AccountManager {

    private static final AccountManager instance = new AccountManager();

    private final AccessTokenManager     accessTokenManager     = AccessTokenManager.getInstance();
    private final TwitterInstanceManager twitterInstanceManager = TwitterInstanceManager.getInstance();

    private final ArrayList<AccessToken> tokenArrayList = new ArrayList<>();

    private AccessToken currentToken;

    private AccountManager() {
        accessTokenManager.readTokenList();
        currentToken = accessTokenManager.readPrimaryAccount();
    }

    public static AccountManager getInstance() {
        return instance;
    }

    public void addAccount(AccessToken token) {
        if (currentToken == null) {
            currentToken = token;
        }
        accessTokenManager.writeAccessToken(token);
        twitterInstanceManager.addUser(token);
        tokenArrayList.add(token);
    }

    public void removeAccount(Long userId) {
        if (currentToken.getUserId() == userId) {
            return;
        }
        for (AccessToken token : tokenArrayList) {
            if (token.getUserId() == userId) {
                tokenArrayList.remove(token);
            }
        }
    }

    public AccessToken changeAccount(Long userId) {
        for (AccessToken token : tokenArrayList) {
            if (token.getUserId() == userId) {
                return token;
            }
        }
        throw new IllegalArgumentException("アカウントが存在しません");
    }

    public int accountCount() {
        return tokenArrayList.size();
    }

    public String getCurrentScreenName() {
        return currentToken.getScreenName();
    }

    public AccessToken getCurrentToken() {
        return currentToken;
    }
}