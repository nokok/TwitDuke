package net.nokok.twitduke.model.account;

import com.google.common.base.Splitter;
import com.google.common.primitives.Longs;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
    private SimpleToken primaryUser;

    private final String AUTH_DIRECTORY_PATH        = new File(new File("").getAbsolutePath(), "auth").getAbsolutePath();
    private final File   tokenListFile              = new File(AUTH_DIRECTORY_PATH + File.separator + ACCESS_TOKEN_LIST_FILENAME);
    private final String TOKENFILE_PATH_WITH_PREFIX = AUTH_DIRECTORY_PATH + File.separator + ACCESS_TOKEN_PREFIX;

    private final ArrayList<SimpleToken> simpleTokenList = new ArrayList<>();
    private boolean isLoaded;

    /**
     * AccessTokenManagerのコンストラクタです
     * このクラスはシングルトンなクラスで、他のクラスからnewを使用してのインスタンスの生成が出来ません。
     * アクセストークンのリストが存在する場合はそのリストが読み込まれます
     * アクセストークンのリストが存在しない場合、authディレクトリをカレントディレクトリ内に作成します。
     */
    private AccessTokenManager() {
        if (!isTokenListExists()) {
            return;
        }
        readTokenList();
    }

    /**
     * カレントディレクトリにauthディレクトリを作成します
     *
     * @return ディレクトリの作成に成功または既に存在していた場合true
     */
    public boolean createTokenDirectory() {
        File authDirectory = new File(AUTH_DIRECTORY_PATH);
        return authDirectory.exists() || authDirectory.mkdir();
    }

    /**
     * AccessTokenManagerのインスタンスを返します
     *
     * @return AccessTokenManagerのインスタンス
     */
    public static AccessTokenManager getInstance() {
        return instance;
    }

    /**
     * トークンリストを読み込んでtokenListに追加します
     * トークンリストが無い場合にこのメソッドが呼び出されるとIOErrorが投げられます
     *
     * @throws java.io.IOError
     */
    public boolean readTokenList() {
        if (!isTokenListExists()) {
            throw new IOError(new FileNotFoundException("トークンリストファイルが見つかりません"));
        }
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(tokenListFile))) {
            String readLine;
            while ((readLine = bufferedReader.readLine()) != null) {
                Iterator<String> iteratableTokenList = Splitter.on(',').trimResults().omitEmptyStrings().split(readLine).iterator();
                while (iteratableTokenList.hasNext()) {
                    String userName = iteratableTokenList.next();
                    long userId = Longs.tryParse(iteratableTokenList.next());
                    simpleTokenList.add(new SimpleToken(userName, userId));
                }
            }
            primaryUser = simpleTokenList.get(0);
            isLoaded = primaryUser != null;
            return isLoaded;
        } catch (IOException e) {
            e.printStackTrace();
            throw new InternalError("トークんリストの読み込み中にエラーが発生しました");
        }
    }

    /**
     * TwitDukeのカレントディレクトリのauthディレクトリ内にトークンリストが存在するかどうか返します
     *
     * @return 存在する場合true
     */
    public boolean isTokenListExists() {
        return tokenListFile.exists();
    }

    /**
     * @return トークンリストファイルの絶対パス
     */
    public String getTokenFileListPath() {
        return tokenListFile.getPath();
    }

    /**
     * プライマリアカウントのアクセストークンファイルをディスクから読み込みます
     * 認証ファイルが生成される前にはこのメソッドは使用できません
     *
     * @return 読み込まれたAccessToken
     */
    public AccessToken readPrimaryAccount() {
        if (!isLoaded) {
            readTokenList();
        } else if (primaryUser == null) {
            throw new IllegalStateException("認証が完了していません");
        }
        return readAccessToken(primaryUser.getUserId());
    }

    /**
     * @return プライマリアカウントのスクリーンネーム
     */
    public String getUserName() {
        if (primaryUser == null) {
            throw new IllegalStateException("認証が完了していません");
        }
        return primaryUser.getScreenName();
    }

    /**
     * 指定されたIDを持つユーザーのアクセストークンファイルをディスクから読み込みます
     *
     * @param id 読み込むユーザーのID
     * @return 読み込まれたAccessToken
     */
    public AccessToken readAccessToken(long id) {
        try (FileInputStream fileInputStream = new FileInputStream(TOKENFILE_PATH_WITH_PREFIX + id);
             ObjectInputStream stream = new ObjectInputStream(fileInputStream)) {

            return (AccessToken) stream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new IOError(e.getCause());
        }
    }

    /**
     * 渡されたアクセストークンをディスクに書き込みます
     * アクセストークンのスクリーンネームとIDがトークンリストにコンマ区切りで追記され、
     * 渡されたアクセストークンのオブジェクトを「token_ユーザーID」というファイル名でauthディレクトリ以下に書き込みます
     *
     * @param accessToken 書き込むアクセストークン
     */
    public void writeAccessToken(AccessToken accessToken) {
        File authUserListFile = new File(AUTH_DIRECTORY_PATH + File.separator + ACCESS_TOKEN_LIST_FILENAME);
        try (FileOutputStream fileOutputStream = new FileOutputStream(TOKENFILE_PATH_WITH_PREFIX + accessToken.getUserId());
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
             FileWriter writer = new FileWriter(authUserListFile)) {

            writer.write(accessToken.getScreenName() + "," + accessToken.getUserId() + "\n");
            objectOutputStream.writeObject(accessToken);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOError(e.getCause());
        }
    }

}
