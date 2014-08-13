package net.nokok.twitduke.components.keyevent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;

/**
 * Created by wtnbsts on 2014/07/29.
 */
public class JavaFXActionRegister implements ActionRegister {

    private final Node root;
    private final Map<Node, List<EventHandler<KeyEvent>>> registry = new HashMap<>();
    private final List<Exception> errors = new ArrayList<>();

    public JavaFXActionRegister(final Node root) {
        this.root = root;
    }

    @Override
    public void registerKeyMap(final KeyMapSetting setting) {
        root.lookupAll("*")
                .stream()
                .filter(this::isResolvable)
                .forEach(node -> {
                    registerCall(node, setting);
                });
    }

    @Override
    public void unregisterAll() {
        registry.forEach((node, handlerList) -> {
            handlerList.forEach(handler -> {
                try {
                    node.removeEventHandler(KeyEvent.KEY_PRESSED, handler);
                    handlerList.remove(handler);
                } catch (Exception ex) {
                    errors.add(ex);
                }
            });
        });
    }

    @Override
    public List<Exception> getErrors() {
        return errors.stream().collect(Collectors.toList());
    }

    @Override
    public void clearErrors() {
        errors.clear();;
    }

    @SuppressWarnings("unchecked")
    private List<EventHandler<KeyEvent>> registerCommand(final Node node, final Class<?> commandClass,
                                                         final List<KeyBind> keyBinds) throws Exception {
        List<EventHandler<KeyEvent>> added = new ArrayList<>();
        EventHandler<KeyEvent> command = (EventHandler<KeyEvent>) commandClass.newInstance();
        keyBinds.forEach(bind -> {
            EventHandler<KeyEvent> adapter = event -> {
                if ( bind.getKeyStroke().match(event) ) {
                    command.handle(event);
                }
            };
            node.addEventHandler(KeyEvent.KEY_PRESSED, adapter);
            added.add(adapter);
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
                List<EventHandler<KeyEvent>> added = registerCommand(node, commandClass, binds);
                registry.get(node).addAll(added);
            } catch (Exception ex) {
                errors.add(ex);
            }
        });
    }

    private boolean isResolvable(Object obj) {
        return obj.getClass().getCanonicalName() != null;
    }
}
