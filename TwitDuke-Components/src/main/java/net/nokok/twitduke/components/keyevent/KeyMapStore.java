package net.nokok.twitduke.components.keyevent;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * キーボードショートカット設定を、外部環境からload&saveするメソッドを提供するインターフェース
 * Created by wtnbsts on 2014/07/24.
 */
public interface KeyMapStore {

    /**
     * このインターフェースを実装したオブジェクトを生成する
     * あとで削除する
     *
     * @return オブジェクト
     */
    @Deprecated
    static KeyMapStore newInstance() {
        return new XmlKeyMapStore();
    }

    /**
     * 設定を外部から読み込む
     *
     * @param source 設定の入力元ストリーム
     *
     * @return 読み込んだ設定情報
     *
     * @throws Exception IOException当たりが考えられそう
     */
    KeyMapSetting load(final InputStream source) throws Exception;

    /**
     * 設定を外部へ出力する
     *
     * @param dist    設定の出力先ストリーム
     * @param setting 出力する設定情報
     *
     * @return true : 成功
     *         false : なんか失敗？
     *
     * @throws Exception
     */
    boolean save(final OutputStream dist, final KeyMapSetting setting) throws Exception;
}
