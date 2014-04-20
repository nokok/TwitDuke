package net.nokok.twitduke;

import java.util.Objects;

/**
 * TwitDukeのMainクラスです
 * <p>
 * @author noko <nokok.kz at gmail.com>
 */
public class Main {

    private boolean isDebugMode;

    /**
     * TwitDukeのエントリポイントです。
     * 引数は-debugのみ有効です。
     * それ以外は全て無視されます。
     * <p>
     * @param args 渡された引数の配列
     */
    public static void main(String[] args) {
        new Main().run(hasDebugOption(args));
    }

    /**
     * 渡された引数の中に-debugが含まれているかをチェックします
     * <p>
     * @param args TwitDukeに渡された引数の配列
     * <p>
     * @return -debugが含まれていた場合true それ以外はfalse
     */
    private static boolean hasDebugOption(String[] args) {
        if ( Objects.isNull(args) ) {
            return false;
        }
        for ( String arg : args ) {
            if ( arg.equals("-debug") ) {
                return true;
            }
        }
        return false;
    }

    /**
     * 実際に起動処理を開始します。
     * <p>
     * @param isDebugMode デバッグモードで起動するかどうかを指定します。
     *                    trueでデバッグモードで起動します
     */
    public void run(boolean isDebugMode) {
        this.isDebugMode = isDebugMode;

    }

    /**
     * デバッグモードかどうかを返します
     * <p>
     * @return デバッグモードならtrue
     */
    public boolean isDebugMode() {
        return isDebugMode;
    }
}
