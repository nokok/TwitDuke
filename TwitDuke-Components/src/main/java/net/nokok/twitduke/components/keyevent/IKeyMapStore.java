package net.nokok.twitduke.components.keyevent;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by a13612 on 2014/07/24.
 */
public interface IKeyMapStore {

    static IKeyMapStore newInstance() {
        return new KeyMapXmlStore();
    }

    IKeyMapSetting load(final InputStream source) throws Exception;

    boolean save(final OutputStream dist, final IKeyMapSetting setting) throws Exception;
}
