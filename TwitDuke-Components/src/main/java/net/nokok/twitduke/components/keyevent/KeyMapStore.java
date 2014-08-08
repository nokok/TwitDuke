package net.nokok.twitduke.components.keyevent;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by wtnbsts on 2014/07/24.
 */
public interface KeyMapStore {

    static KeyMapStore newInstance() {
        return new KeyMapXmlStore();
    }

    KeyMapSetting load(final InputStream source) throws Exception;

    boolean save(final OutputStream dist, final KeyMapSetting setting) throws Exception;
}
