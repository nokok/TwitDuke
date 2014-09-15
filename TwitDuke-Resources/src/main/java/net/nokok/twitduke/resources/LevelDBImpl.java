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
package net.nokok.twitduke.resources;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.Options;
import org.iq80.leveldb.impl.Iq80DBFactory;
import static org.iq80.leveldb.impl.Iq80DBFactory.bytes;

public class LevelDBImpl implements LevelDB, AutoCloseable {

    private final DB db;

    public LevelDBImpl(String dbFileName) throws IOException {
        this(new File(dbFileName));
    }

    public LevelDBImpl(File file) throws IOException {
        Options options = new Options().createIfMissing(true);
        this.db = Iq80DBFactory.factory.open(file, options);
    }

    @Override
    public void close() throws Exception {
        db.close();
    }

    @Override
    public void delete(String key) throws IOException {
        db.delete(bytes(Objects.requireNonNull(key)));
    }

    @Override
    public Optional<String> get(String key) {
        Objects.requireNonNull(key);
        return Optional.ofNullable(Iq80DBFactory.asString(db.get(bytes(key))));
    }

    @Override
    public void put(String key, String value) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(value);
        db.put(bytes(key), bytes(value));
    }

}
