package net.nokok.twitduke.core.io;

import java.util.Optional;
import java.util.Properties;
import net.nokok.twitduke.base.io.PropertyReader;
import net.nokok.twitduke.base.io.Reader;

public class IOSelectorPropertyReader implements Reader<FileType> {

    private final PropertyReader reader;

    public IOSelectorPropertyReader(String path) {
        this.reader = new PropertyReader(path);

    }

    @Override
    public Optional<FileType> read() {
        Optional<Properties> mayBeProperties = reader.read();
        if ( !mayBeProperties.isPresent()
             || !isValidProperty(mayBeProperties.get()) ) {
            return Optional.empty();
        }
        return Optional.of(pToFileType(mayBeProperties.get()));
    }

    private boolean isValidProperty(Properties p) {
        String fileType = p.getProperty(IOSelectorPath.FILE_TYPE_PROPERTY_KEY);
        if ( fileType == null ) {
            return false;
        }
        if ( fileType.equals(FileType.PLAINTEXT.toString()) || fileType.equals(FileType.SERIALIZE.toString()) ) {
            return true;
        }
        return false;
    }

    private FileType pToFileType(Properties p) {
        if ( !isValidProperty(p) ) {
            throw new IllegalArgumentException();
        }
        String fileType = p.getProperty(IOSelectorPath.FILE_TYPE_PROPERTY_KEY);
        if ( fileType.equals(FileType.SERIALIZE.toString()) ) {
            return FileType.SERIALIZE;
        } else {
            return FileType.PLAINTEXT;
        }
    }
}
