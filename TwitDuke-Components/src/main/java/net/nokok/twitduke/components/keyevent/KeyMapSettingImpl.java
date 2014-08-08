package net.nokok.twitduke.components.keyevent;

import com.google.common.base.Strings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by wtnbsts on 2014/07/23.
 */
public class KeyMapSettingImpl implements KeyMapSetting {

    private final String settingName;
    private final Map<String, String> commandClasses = new HashMap<>();
    private final Map<String, List<KeyBind>> commandKeyBinds = new HashMap<>();

    public KeyMapSettingImpl(final String name) {
        settingName = name;
    }

    @Override
    public String getSettingName() {
        return settingName;
    }

    @Override
    public List<String> getCommandIds() {
        return commandClasses.keySet().stream().collect(Collectors.toList());
    }

    @Override
    public boolean addCommand(final String id, final String commandClassName) throws IllegalArgumentException {
        if ( Strings.isNullOrEmpty(id) || Strings.isNullOrEmpty(commandClassName) ) {
            throw new IllegalArgumentException(id);
        }
        if ( commandClasses.containsKey(id) ) {
            return false;
        }
        commandClasses.put(id, commandClassName);
        commandKeyBinds.put(id, new ArrayList<>());
        return true;
    }

    @Override
    public boolean removeCommand(final String id) throws IllegalArgumentException {
        throw new UnsupportedOperationException("実装してないよ");
    }

    @Override
    public String getCommandClassName(final String id) {
        return commandClasses.get(id);
    }

    @Override
    public boolean addKeyBind(final String id, final KeyBind keyBind) throws IllegalArgumentException {
        if ( Strings.isNullOrEmpty(id) || Objects.isNull(keyBind) ) {
            throw new IllegalArgumentException();
        }
        if ( Objects.isNull(keyBind.getKeyStroke()) || Strings.isNullOrEmpty(keyBind.getSelector()) ) {
            throw new IllegalArgumentException();
        }
        return commandClasses.containsKey(id) && commandKeyBinds.get(id).add(keyBind);
    }

    @Override
    public void addKeyBinds(final String id, final List<KeyBind> keyBinds) throws IllegalArgumentException {
        keyBinds.stream().forEach(bind -> addKeyBind(id, bind));
    }

    @Override
    public boolean removeKeyBind(final String id, final KeyBind keyBind) throws IllegalArgumentException {
        if ( Strings.isNullOrEmpty(id) ) {
            throw new IllegalArgumentException();
        }
        return commandClasses.containsKey(id) && commandKeyBinds.get(id).remove(keyBind);
    }

    @Override
    public List<KeyBind> getKeyBinds(final String id) {
        return commandKeyBinds.get(id);
    }

    @Override
    public Map<String, List<KeyBind>> collectKeyBinds(final String targetComponentName)
            throws IllegalArgumentException {
        Map<String, List<KeyBind>> result = new HashMap<>();
        commandKeyBinds.forEach((id, binds) -> {
            binds.stream()
                    .filter(bind -> targetComponentName.equals(bind.getSelector()))
                    .forEach(bind -> {
                        if ( !result.containsKey(id) ) {
                            result.put(id, new ArrayList<>());
                        }
                        result.get(id).add(bind);
                    });
        });
        return result;
    }

    @Override
    public boolean equals(final Object o) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }

        KeyMapSettingImpl that = (KeyMapSettingImpl) o;

        if ( !commandClasses.equals(that.commandClasses) ) {
            return false;
        }
        if ( !commandKeyBinds.equals(that.commandKeyBinds) ) {
            return false;
        }
        if ( !(settingName != null ? !settingName.equals(that.settingName) : that.settingName != null) ) {
            return true;
        }
        return false;

    }

    @Override
    public int hashCode() {
        int result = settingName != null ? settingName.hashCode() : 0;
        result = 31 * result + commandClasses.hashCode();
        result = 31 * result + commandKeyBinds.hashCode();
        return result;
    }
}
