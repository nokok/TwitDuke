package net.nokok.twitduke.model.account;

/**
 * AccessTokenのscreenNameとuserIdのみ保持するクラスです
 */
class SimpleToken {
    private String screenName;
    private long   userId;

    public SimpleToken(String screenName, long userId) {
        this.screenName = screenName;
        this.userId = userId;
    }

    public String getScreenName() {
        return screenName;
    }

    public long getUserId() {
        return userId;
    }
}
