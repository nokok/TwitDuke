package net.nokok.twitduke.components.keyevent;

import java.util.List;
import javafx.scene.Node;

/**
 * キーボードショートカットをGUIパーツに登録するメソッドを提供するインターフェース
 * Created by wtnbsts on 2014/07/26.
 */
public interface ActionRegister {

    /**
     * キーボードショートカットを設定するノードを指定して、キーボードショートカット設定インスタンスを生成する
     * あとで、削除する
     *
     * @param root キーボードを設定したいノード
     *
     * @return ショートカット設定インスタンス
     */
    @Deprecated
    static ActionRegister newInstance(final Node root) {
        return new JavaFXActionRegister(root);
    }

    /**
     * キーボードショートカット設定を適用する
     *
     * @param setting キーボードショートカット設定
     */
    void registerKeyMap(final KeyMapSetting setting);

    /**
     * キーボードショートカット設定を解除する
     */
    void unregisterAll();

    /**
     * {@link ActionRegister#registerKeyMap(net.nokok.twitduke.components.keyevent.KeyMapSetting) },
     * {@link ActionRegister#unregisterAll() } のいずれかで発生した例外の一覧を取得する
     *
     * @return 例外のリスト
     */
    List<Exception> getErrors();

    /**
     * 発生した例外の履歴を削除する
     */
    void clearErrors();
}
