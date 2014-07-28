package net.nokok.twitduke.components.keyevent;

import java.awt.*;
import java.util.List;

/**
 * Created by wtnbsts on 2014/07/26.
 */
public interface IActionRegister {

    static IActionRegister newInstance(final Container root) {
        return new ActionRegister(root);
    }

    void registerKeyMap(final IKeyMapSetting setting);

    void unregisterAll();

    List<Exception> getErrors();
}
