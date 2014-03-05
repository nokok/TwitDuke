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
import net.nokok.twitduke.main.Config;
import twitter4j.auth.AccessToken;

public class AccessTokenManager {

    private boolean     isLoaded;
    private SimpleToken primaryUser;

    private static final AccessTokenManager INSTANCE = new AccessTokenManager();

    private final File                   tokenListFile   = new File(Config.Path.TOKEN_LIST_FILE);
    private final ArrayList<SimpleToken> simpleTokenList = new ArrayList<>(3);

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
     * 初回認証時のアクセストークン関連の処理を行います。
     * アクセストークンを保存するauthディレクトリを作成し渡された
     * アクセストークンを保存します
     *
     * @param accessToken 保存するアクセストークン
     */
    public void createPrimaryAccount(AccessToken accessToken) {
        createTokenDirectory();
        writeAccessToken(accessToken);
    }

    /**
     * カレントディレクトリにauthディレクトリを作成します
     */
    private void createTokenDirectory() {
        File authDirectory = new File(Config.Path.AUTH_DIRECTORY);
        if (!authDirectory.exists()) {
            authDirectory.mkdir();
        }
    }

    /**
     * AccessTokenManagerのインスタンスを返します
     *
     * @return AccessTokenManagerのインスタンス
     */
    public static AccessTokenManager getInstance() {
        return INSTANCE;
    }

    /**
     * トークンリストを読み込んでtokenListに追加します
     * トークンリストが無い場合にこのメソッドが呼び出されるとIOErrorが投げられます
     *
     * @throws java.io.IOError
     */
    void readTokenList() {
        if (!isTokenListExists()) {
            throw new IOError(new FileNotFoundException("トークンリストファイルが見つかりません"));
        }
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(tokenListFile))) {
            String readLine;
            while ((readLine = bufferedReader.readLine()) != null) {
                Iterator<String> iteratableTokenList = Splitter.on(',').trimResults().omitEmptyStrings().split(readLine).iterator();
                String userName = iteratableTokenList.next();
                long userId = Longs.tryParse(iteratableTokenList.next());
                simpleTokenList.add(new SimpleToken(userName, userId));
            }
            primaryUser = simpleTokenList.get(0);
            isLoaded = primaryUser != null;
        } catch (IOException e) {
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
    AccessToken readAccessToken(long id) {
        try (FileInputStream fileInputStream = new FileInputStream(String.format("%s%d", Config.Path.TOKENFILE_PREFIX, id));
             ObjectInputStream stream = new ObjectInputStream(fileInputStream)) {

            return (AccessToken) stream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new IOError(e.getCause());
        }
    }

    /**
     * 渡されたアクセストークンをディスクに書き込みます
     * アクセストークンのスクリーンネームとIDがトークンリストにコンマ区切りで追記され、
     * 渡されたアクセストークンのオブジェクトを「token_ユーザーID」というファイル名でauthディレクトリ以下に書き込みます
     *
     * @param accessToken 書き込むアクセストークン
     * @throws java.io.IOError ファイルが見つからなかったり、ファイルがオープンできなかったりするなどの理由で処理が失敗した時にスローされます
     */
    public void writeAccessToken(AccessToken accessToken) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(String.format("%s%d", Config.Path.TOKENFILE_PREFIX, accessToken.getUserId()));
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
             FileWriter writer = new FileWriter(tokenListFile, true) /*true指定で追記出来る*/) {

            writer.write(accessToken.getScreenName() + ',' + accessToken.getUserId() + '\n');
            objectOutputStream.writeObject(accessToken);
        } catch (IOException e) {
            throw new IOError(e.getCause());
        }
    }

    public void removeAccessToken(long id) {
        new File(String.format("%s%d", Config.Path.TOKENFILE_PREFIX, id)).delete();
    }
}
