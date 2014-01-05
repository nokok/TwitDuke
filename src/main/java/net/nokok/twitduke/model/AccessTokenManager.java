package net.nokok.twitduke.model;

import twitter4j.auth.AccessToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

class AccessTokenManager {

    private static final AccessTokenManager instance = new AccessTokenManager();

    private final String ACCESS_TOKEN_PREFIX = "token_";

    private final String AUTH_DIRECTORY_PATH        = new File(new File("").getAbsolutePath(), "auth").getAbsolutePath();
    private final String TOKENFILE_PATH_WITH_PREFIX = AUTH_DIRECTORY_PATH + File.separator + ACCESS_TOKEN_PREFIX;
    private final File   authDirectory              = new File(AUTH_DIRECTORY_PATH);

    private final ArrayList<AccessToken> tokenList = new ArrayList<>();

    private AccessTokenManager() {
        if (authDirectory.exists()) {
            readTokenList();
        } else {
            authDirectory.mkdir();
        }
    }

    public static AccessTokenManager getInstance() {
        return instance;
    }

    public int readTokenList() {
        File[] files = authDirectory.listFiles();
        if (files == null) {
            return 0;
        }
        for (File file : files) {
            if (file.getName().startsWith(ACCESS_TOKEN_PREFIX)) {
                String fileName = file.getName();
                long fileId = Long.valueOf(fileName.split("_")[1]);
                tokenList.add(readAccessToken(fileId));
            }
        }

        return tokenList.size();
    }

    public boolean isAuthenticated() {
        return readTokenList() != 0 && authDirectory.exists();
    }

    public AccessToken readPrimaryAccount() {
        return readAccessToken(tokenList.get(0).getUserId());
    }

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
        try (FileOutputStream fileOutputStream = new FileOutputStream(TOKENFILE_PATH_WITH_PREFIX + accessToken.getUserId());
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(accessToken);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
