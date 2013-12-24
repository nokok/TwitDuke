package net.nokok.twitduke.model;

import twitter4j.auth.AccessToken;

import java.io.*;
import java.util.ArrayList;

public class AccessTokenManager {

    private static final AccessTokenManager instance = new AccessTokenManager();

    private final String ACCESS_TOKEN_LIST_FILENAME = "authlist";
    private final String ACCESS_TOKEN_PREFIX        = "token";
    private long primaryUserId;

    private final File tokenListFile = new File(ACCESS_TOKEN_LIST_FILENAME);

    private ArrayList<TokenList> tokenList = new ArrayList<>();

    private AccessTokenManager() {
        if (isAuthenticated()) {
            readTokenList();
        }
    }

    public static AccessTokenManager getInstance() {
        return instance;
    }

    public void readTokenList() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(tokenListFile))) {
            String readLine;
            while ((readLine = bufferedReader.readLine()) != null) {
                String userName = readLine.split(",")[0];
                long userId = Long.valueOf(readLine.split(",")[1]);
                tokenList.add(new TokenList(userName, userId));
            }
            primaryUserId = tokenList.get(0).userId;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isAuthenticated() {
        return tokenListFile.exists();
    }

    public AccessToken readPrimaryAccount() {
        return readAccessToken(primaryUserId);
    }

    public AccessToken readAccessToken(long id) {
        try (FileInputStream fileInputStream = new FileInputStream(ACCESS_TOKEN_PREFIX + id);
             ObjectInputStream stream = new ObjectInputStream(fileInputStream)) {
            return (AccessToken) stream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalError("トークンファイルの読み込み中にエラーが発生しました");
        }
    }

    public void writeAccessToken(AccessToken accessToken) {
        File authUserListFile = new File(ACCESS_TOKEN_LIST_FILENAME);
        try (FileOutputStream fileOutputStream = new FileOutputStream(ACCESS_TOKEN_PREFIX + accessToken.getUserId());
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
             FileWriter writer = new FileWriter(authUserListFile)) {
            writer.write(accessToken.getScreenName() + "," + accessToken.getUserId() + "\n");
            objectOutputStream.writeObject(accessToken);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class TokenList {
        private String userName;
        private long   userId;

        public TokenList(String userName, long userId) {
            this.userName = userName;
            this.userId = userId;
        }
    }
}
