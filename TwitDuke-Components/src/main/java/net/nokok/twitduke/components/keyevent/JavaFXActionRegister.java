package net.nokok.twitduke.components.keyevent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;

/**
 * Created by wtnbsts on 2014/07/29.
 */
public class JavaFXActionRegister implements ActionRegister {

    private final Node root;
    private final Map<Node, List<KeyBind>> registry = new HashMap<>();
    private final List<Exception> errors = new ArrayList<>();

    public JavaFXActionRegister(final Node root) {
        this.root = root;
    }

    @Override
    public void registerKeyMap(final KeyMapSetting setting) {
        errors.clear();
        registry.clear();
        root.lookupAll("*")
                .stream()
                .filter(node -> {
                    if ( node.getClass().getCanonicalName() == null ) {
                        return false;
                    }
                    return node.getId() != null;
                })
                .forEach(node -> {
                    registerCall(node, setting);
                });
    }

    @Override
    public void unregisterAll() {

    }

    @Override
    public List<Exception> getErrors() {
        return errors;
    }

    @SuppressWarnings("unchecked")
    private List<KeyBind> registerCommand(final Node node, final Class<?> commandClass,
                                          final List<KeyBind> keyBinds) throws Exception {
        List<KeyBind> added = new ArrayList<>();
        EventHandler<KeyEvent> command = (EventHandler<KeyEvent>) commandClass.newInstance();
        keyBinds.forEach(bind -> {
            EventHandler<KeyEvent> adapter = event -> {
                if ( bind.getKeyStroke().match(event) ) {
                    command.handle(event);
                }
            };
            node.addEventHandler(KeyEvent.KEY_PRESSED, adapter);
            added.add(bind);
        });
        return added;
    }

    private void registerCall(Node node, KeyMapSetting setting) {
        String nodeClassName = node.getClass().getCanonicalName();
        Map<String, List<KeyBind>> keyBindMap = setting.collectKeyBinds(nodeClassName).get();
        if ( keyBindMap.isEmpty() ) {
            return;
        }
        registry.putIfAbsent(node, new ArrayList<>());
        keyBindMap.forEach((id, binds) -> {
            try {
                Class<?> commandClass = Class.forName(setting.getCommandClassName(id).get());
                List<KeyBind> added = registerCommand(node, commandClass, binds);
                registry.get(node).addAll(added);
            } catch (Exception ex) {
                errors.add(ex);
            }
        });
    }
}
