package net.nokok.twitduke.components.keyevent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by wtnbsts on 2014/07/23.
 */
public class KeyMapSettingImpl implements KeyMapSetting {

    private final String settingName;
    private final Map<String, String> commandClasses = new HashMap<>();
    private final Map<String, List<KeyBind>> commandKeyBinds = new HashMap<>();

    public KeyMapSettingImpl(final String name) {
        Objects.requireNonNull(name);
        settingName = name;
    }

    @Override
    public Optional<String> getSettingName() {
        return Optional.of(settingName);
    }

    @Override
    public Optional<List<String>> getCommandIds() {
        return Optional.of(commandClasses.keySet().stream().collect(Collectors.toList()));
    }

    @Override
    public boolean addCommand(final String id, final String commandClassName) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(commandClassName);
        if ( commandClasses.containsKey(id) ) {
            return false;
        }
        commandClasses.put(id, commandClassName);
        commandKeyBinds.put(id, new ArrayList<>());
        return true;
    }

    @Override
    public boolean removeCommand(final String id) {
        return Objects.isNull(commandClasses.remove(id))
               || Objects.isNull(commandKeyBinds.remove(id));
    }

    @Override
    public Optional<String> getCommandClassName(final String id) {
        return Optional.of(commandClasses.get(id));
    }

    @Override
    public boolean addKeyBind(final String id, final KeyBind keyBind) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(keyBind);
        return commandClasses.containsKey(id) && commandKeyBinds.get(id).add(keyBind);
    }

    @Override
    public void addKeyBinds(final String id, final List<KeyBind> keyBinds) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(keyBinds);
        keyBinds.stream().forEach(bind -> addKeyBind(id, bind));
    }

    @Override
    public boolean removeKeyBind(final String id, final KeyBind keyBind) {
        return commandClasses.containsKey(id) && commandKeyBinds.get(id).remove(keyBind);
    }

    @Override
    public Optional<List<KeyBind>> getKeyBinds(final String id) {
        return Optional.of(commandKeyBinds.get(id));
    }

    @Override
    public Optional<Map<String, List<KeyBind>>> collectKeyBinds(final String targetComponentName) {
        Map<String, List<KeyBind>> result = new HashMap<>();
        commandKeyBinds.forEach((id, binds) -> {
            binds.stream()
                    .filter(bind -> targetComponentName.equals(bind.getSelector()))
                    .forEach(bind -> {
                        result.computeIfAbsent(id, key -> new ArrayList<>());
                        result.get(id).add(bind);
                    });
        });
        return Optional.of(result);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.settingName);
        hash = 83 * hash + Objects.hashCode(this.commandClasses);
        hash = 83 * hash + Objects.hashCode(this.commandKeyBinds);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        final KeyMapSettingImpl other = (KeyMapSettingImpl) obj;
        if ( !Objects.equals(this.settingName, other.settingName) ) {
            return false;
        }
        if ( !Objects.equals(this.commandClasses, other.commandClasses) ) {
            return false;
        }
        if ( !Objects.equals(this.commandKeyBinds, other.commandKeyBinds) ) {
            return false;
        }
        return true;
    }

}
