package net.nokok.twitduke.components.keyevent;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

/**
 * Created by wtnbsts on 2014/07/26.
 */
public class JComponentActionRegister implements IActionRegister {

    private final Component root;
    private final Map<JComponent, List<KeyBind>> registry = new HashMap<>();
    private List<Exception> errors = new ArrayList<>();

    public JComponentActionRegister(final Component root) {
        this.root = root;
    }

    @Override
    public void registerKeyMap(final IKeyMapSetting setting) {
        Consumer<JComponent> registerCall = component -> {
            registry.putIfAbsent(component, new ArrayList<>());
            Map<String, List<KeyBind>> keyBindMap = setting.collectKeyBinds(component.getClass().getCanonicalName());
            keyBindMap.forEach((id, binds) -> {
                try {
                    Class<?> commandClass = Class.forName(setting.getCommandClassName(id));
                    List<KeyBind> added = registerCommand(component, commandClass, binds);
                    registry.get(component).addAll(added);
                } catch (Exception ex) {
                    errors.add(ex);
                }
            });
        };
        walk(root, registerCall);
    }

    @Override
    public void unregisterAll() {
        registry.forEach((component, keyBinds) -> {
            keyBinds.forEach(keyBind -> {
                component.unregisterKeyboardAction(
                        KeyStroke.getKeyStroke(keyBind.getKeyStroke())
                );
            });
        });
        registry.clear();
    }

    @Override
    public List<Exception> getErrors() {
        return errors;
    }

    private List<KeyBind> registerCommand(final JComponent component, final Class<?> commandClass,
                                          final List<KeyBind> keyBinds) throws Exception {
        List<KeyBind> added = new ArrayList<>();
        ActionListener command = (ActionListener) commandClass.newInstance();
        keyBinds.forEach(bind -> {
            component.registerKeyboardAction(
                    command, KeyStroke.getKeyStroke(bind.getKeyStroke()),
                    bind.getTargetComponentCondition()
            );
            added.add(bind);
        });
        return added;
    }

    private void walk(Component component, Consumer<JComponent> callback) {
        if ( component instanceof JComponent ) {
            callback.accept((JComponent) component);
        }
        if ( component instanceof Container ) {
            Stream.of(((Container) component).getComponents()).forEach(c -> walk(c, callback));
        }
    }
}
