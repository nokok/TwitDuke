/*
 * The MIT License
 *
 * Copyright 2014 noko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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
        return fileType.equals(FileType.PLAINTEXT.toString()) || fileType.equals(FileType.SERIALIZE.toString());
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
