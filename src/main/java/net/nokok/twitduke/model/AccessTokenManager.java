package net.nokok.twitduke.model;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.primitives.Longs;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOError;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import twitter4j.auth.AccessToken;

public class AccessTokenManager {

    private static final AccessTokenManager instance = new AccessTokenManager();

    private final String ACCESS_TOKEN_LIST_FILENAME = "authlist";
    private final String ACCESS_TOKEN_PREFIX        = "token";
    private TokenList primaryUser;

    private final String AUTH_DIRECTORY_PATH        = new File(new File("").getAbsolutePath(), "auth").getAbsolutePath();
    private final File   tokenListFile              = new File(AUTH_DIRECTORY_PATH + File.separator + ACCESS_TOKEN_LIST_FILENAME);
    private final String TOKENFILE_PATH_WITH_PREFIX = AUTH_DIRECTORY_PATH + File.separator + ACCESS_TOKEN_PREFIX;

    private ArrayList<TokenList> tokenList = Lists.newArrayList();

    private AccessTokenManager() {
        if (isAuthenticated()) {
            readTokenList();
        } else {
            createTokenDirectory();
        }
    }

    private boolean createTokenDirectory() {
        File authDirectory = new File(AUTH_DIRECTORY_PATH);
        return authDirectory.exists() || authDirectory.mkdir();
    }

    public static AccessTokenManager getInstance() {
        return instance;
    }

    public void readTokenList() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(tokenListFile))) {
            String readLine;
            while ((readLine = bufferedReader.readLine()) != null) {
                Iterator<String> iteratableTokenList = Splitter.on(',').trimResults().omitEmptyStrings().split(readLine).iterator();
                while (iteratableTokenList.hasNext()) {
                    String userName = iteratableTokenList.next();
                    long userId = Longs.tryParse(iteratableTokenList.next());
                    tokenList.add(new TokenList(userName, userId));
                }
            }
            //TODO:ヤバいのでなんとかする
            /*
            初回起動で認証ファイルが存在しない(つまりtokenListが空の)時にnullを返す為、82行目などでNullPointerExceptionが発生する
             */
            primaryUser = tokenList.get(0);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOError(e.getCause());
        }
    }

    public boolean isAuthenticated() {
        return tokenListFile.exists();
    }

    public AccessToken readPrimaryAccount() {
        return readAccessToken(primaryUser.userId);
    }

    public String getUserName() {
        return primaryUser.userName;
    } //TODO:50行目と同じく

    public AccessToken readAccessToken(long id) {
        try (FileInputStream fileInputStream = new FileInputStream(TOKENFILE_PATH_WITH_PREFIX + id);
             ObjectInputStream stream = new ObjectInputStream(fileInputStream)) {
            return (AccessToken) stream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new IOError(e.getCause());
        }
    }

    public void writeAccessToken(AccessToken accessToken) {
        File authUserListFile = new File(AUTH_DIRECTORY_PATH + File.separator + ACCESS_TOKEN_LIST_FILENAME);
        try (FileOutputStream fileOutputStream = new FileOutputStream(TOKENFILE_PATH_WITH_PREFIX + accessToken.getUserId());
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
             FileWriter writer = new FileWriter(authUserListFile)) {
            writer.write(accessToken.getScreenName() + "," + accessToken.getUserId() + "\n");
            objectOutputStream.writeObject(accessToken);
        } catch (IOException e) {
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
