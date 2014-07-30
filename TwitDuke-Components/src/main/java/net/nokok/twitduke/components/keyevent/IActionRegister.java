package net.nokok.twitduke.components.keyevent;

import java.awt.Component;
import java.util.List;
import javafx.scene.Node;

/**
 * Created by wtnbsts on 2014/07/26.
 */
public interface IActionRegister {

    static IActionRegister newInstance(final Component root) {
        return new JComponentActionRegister(root);
    }

    static IActionRegister newInstance(final Node root) {
        return new JavaFXActionRegister(root);
    }

    void registerKeyMap(final IKeyMapSetting setting);

    void unregisterAll();

    List<Exception> getErrors();
}
