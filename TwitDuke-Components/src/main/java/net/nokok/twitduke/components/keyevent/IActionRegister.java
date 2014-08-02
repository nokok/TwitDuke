package net.nokok.twitduke.components.keyevent;

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
     */
    static IActionRegister newInstance(final Node root) {
        return new JavaFXActionRegister(root);
    }

    void registerKeyMap(final IKeyMapSetting setting);

    void unregisterAll();

    List<Exception> getErrors();
}
