package net.nokok.twitduke.components.keyevent;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Created by wtnbsts on 2014/07/26.
 */
public class ActionRegister implements IActionRegister {

    private Container root;
    private Map<JComponent, KeyBind> registry = new HashMap<>();

    public ActionRegister(final Container root) {
        this.root = root;
    }

    private static void walk(Component component, Consumer<JComponent> callback) {
        if ( component instanceof JComponent ) {
            callback.accept((JComponent) component);
        }
        if ( component instanceof Container ) {
            Stream.of(((Container) component).getComponents()).forEach(c -> walk(c, callback));
        }
    }

    @Override
    public void registerKeyMap(final IKeyMapSetting setting) {
        Consumer<JComponent> walkman = component -> {
            Map<String, List<KeyBind>> keyBindMap = setting.collectKeyBinds(component.getClass().getCanonicalName());
            keyBindMap.values().forEach(
                    keybindList -> {
                        keybindList.forEach(
                                keybind -> {
                                    registry.put(component, keybind);
                                }
                        );
                    }
            );
        };
        walk(root, walkman);
    }

    @Override
    public void unregisterAll() {

    }

    @Override
    public List<Exception> getErrors() {
        return null;
    }
}
