package net.nokok.twitduke.components.keyevent;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Created by wtnbsts on 2014/07/29.
 */
public class JavaFXActionRegister implements IActionRegister {

    private final Node root;
    private Map<Node, List<KeyBind>> registry = new HashMap<>();
    private List<Exception> errors = new ArrayList<>();


    public JavaFXActionRegister(final Node root) {
        this.root = root;
    }

    @Override
    public void registerKeyMap(final IKeyMapSetting setting) {
//        Consumer<Pair<Node, String>> registerCall = nodeAndClassName -> {
//            Map<String, List<KeyBind>> keyBindMap = setting.collectKeyBinds(nodeAndClassName.getValue());
//            if ( keyBindMap.isEmpty() ) {
//                return;
//            }
//            registry.putIfAbsent(nodeAndClassName.getKey(), new ArrayList<>());
//            keyBindMap.forEach(
//                    (id, binds) -> {
//                        try {
//                            Class<?> commandClass = Class.forName(setting.getCommandClassName(id));
//                            List<KeyBind> added = registerCommand(nodeAndClassName.getKey(), commandClass, binds);
//                            registry.get(nodeAndClassName.getKey()).addAll(added);
//                        } catch (Exception ex) {
//                            errors.add(ex);
//                        }
//                    }
//            );
//        };
        root.lookupAll("*")
            .stream()
            .filter(node -> node.getClass().getCanonicalName() != null)
            .forEach(
                    node -> {
                        registerCall(node, setting);
                    }
            );
    }

    @Override
    public void unregisterAll() {

    }

    @Override
    public List<Exception> getErrors() {
        return null;
    }


    @SuppressWarnings("unchecked")
    private List<KeyBind> registerCommand(final Node node, final Class<?> commandClass,
                                          final List<KeyBind> keyBinds) throws Exception {
        List<KeyBind> added = new ArrayList<>();
        EventHandler<KeyEvent> command = (EventHandler<KeyEvent>) commandClass.newInstance();
        keyBinds.forEach(
                bind -> {
                    EventHandler<KeyEvent> adapter = event -> {
                        if ( KeyCombination.keyCombination(bind.getKeyStroke()).match(event) ) {
                            command.handle(event);
                        }
                    };
                    node.addEventHandler(KeyEvent.KEY_PRESSED, adapter);
                    added.add(bind);
                }
        );
        return added;
    }

    private void registerCall(Node node, IKeyMapSetting setting) {
        String nodeClassName = node.getClass().getCanonicalName();
        Map<String, List<KeyBind>> keyBindMap = setting.collectKeyBinds(nodeClassName);
        if ( keyBindMap.isEmpty() ) {
            return;
        }
        registry.putIfAbsent(node, new ArrayList<>());
        keyBindMap.forEach(
                (id, binds) -> {
                    try {
                        Class<?> commandClass = Class.forName(setting.getCommandClassName(id));
                        List<KeyBind> added = registerCommand(node, commandClass, binds);
                        registry.get(node).addAll(added);
                    } catch (Exception ex) {
                        errors.add(ex);
                    }
                }
        );
    }
}
