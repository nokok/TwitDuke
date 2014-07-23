package net.nokok.twitduke.core.io;

import java.util.Properties;
import net.nokok.twitduke.base.io.PropertyWriter;
import net.nokok.twitduke.base.io.Writer;

public class IOSelectorPropertyWriter implements Writer<FileType> {

    private final PropertyWriter writer;

    public IOSelectorPropertyWriter(String path) {
        this.writer = new PropertyWriter(path);
    }

    @Override
    public void write(FileType t) {
        writer.write(fToProperties(t));
    }

    private Properties fToProperties(FileType f) {
        Properties p = new Properties();
        p.put(IOSelectorPath.FILE_TYPE_PROPERTY_KEY, p.toString());
        return p;
    }
}
