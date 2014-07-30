package net.nokok.twitduke.resources;

import com.google.common.io.Resources;
import java.io.File;
import java.net.URL;
import java.util.Optional;

/**
 * Created by wtnbsts on 2014/07/28.
 */
public class KeyMapResources {

    public static final Optional<URL> DEFAULT_SETTING
                                      = Optional.ofNullable(Resources.getResource(String.join(File.separator, "keyevent", "default.xml")));

}
