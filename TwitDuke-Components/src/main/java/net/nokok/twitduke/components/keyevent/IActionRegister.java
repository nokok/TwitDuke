package net.nokok.twitduke.components.keyevent;

import java.awt.Component;
import java.util.List;
import javafx.scene.Node;

/**
 * Created by wtnbsts on 2014/07/26.
 */
public interface IActionRegister {

    /**
     * キーボードショートカットを設定するノードを指定して、キーボードショートカット設定インスタンスを生成する
     *
     * @param root キーボードを設定したいノード
     *
     * @return ショートカット設定インスタンス
     *
     * @deprecated swing用クラスの削除と同時に消されます
     */
    @Deprecated
    static IActionRegister newInstance(final Component root) {
        return new JComponentActionRegister(root);
    }

    /**
     * キーボードショートカットを設定するノードを指定して、キーボードショートカット設定インスタンスを生成する
     *
     * @param root キーボードを設定したいノード
     *
     * @return ショートカット設定インスタンス
     */
    static IActionRegister newInstance(final Node root) {
        return new JavaFXActionRegister(root);
    }

    void registerKeyMap(final IKeyMapSetting setting);

    void unregisterAll();

    List<Exception> getErrors();
}
