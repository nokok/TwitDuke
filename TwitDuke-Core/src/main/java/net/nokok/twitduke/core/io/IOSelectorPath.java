package net.nokok.twitduke.core.io;

import java.io.File;
import net.nokok.twitduke.base.io.Paths;

public interface IOSelectorPath {

    public static final String FILE_PATH = String.join(File.separator, Paths.TWITDUKE_HOME, "filetype.properties");
    static final String FILE_TYPE_PROPERTY_KEY = "filetype";
}
