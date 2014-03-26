package net.nokok.twitduke.model.account;

import org.jetbrains.annotations.Nullable;
import twitter4j.auth.AccessToken;

/**
 * AccessTokenのscreenNameとuserIdのみ保持するクラスです
 */
class SimpleToken {
    private final String      screenName;
    private final long        userId;
    @Nullable
    private final AccessToken accessToken;

    public SimpleToken(AccessToken accessToken) {
        this.accessToken = accessToken;
        screenName = accessToken.getScreenName();
        userId = accessToken.getUserId();
    }

    public SimpleToken(String screenName, long userId) {
        accessToken = null;
        this.screenName = screenName;
        this.userId = userId;
    }

    public String getScreenName() {
        return screenName;
    }

    public long getUserId() {
        return userId;
    }

    public AccessToken getAccessToken() {
        return accessToken;
    }
}
