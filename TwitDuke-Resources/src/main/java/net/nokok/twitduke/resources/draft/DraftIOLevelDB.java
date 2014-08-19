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
package net.nokok.twitduke.resources.draft;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.Options;
import org.iq80.leveldb.impl.Iq80DBFactory;
import static org.iq80.leveldb.impl.Iq80DBFactory.bytes;

public class DraftIOLevelDB implements DraftIO {

    private final List<String> draftList = new ArrayList<>();

    @Override
    public synchronized List<String> draftList() {
        return draftList;
    }

    @Override
    public void remove(Integer index) {
        try (DB db = openDB()) {
            db.delete(bytes(index.toString()));
            draftList.remove(index.intValue()); //call remove(int index)
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public Optional<String> restore(Integer index) {
        return Optional.of(draftList.get(index));
    }

    @Override
    public void saveDraft(String text) {
        try (DB db = openDB()) {
            db.put(bytes(String.valueOf(draftList.size())), text.getBytes());
            draftList.add(text);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private DB openDB() throws IOException {
        File dbFile = new File("draft");
        Options options = new Options().createIfMissing(true);
        return Iq80DBFactory.factory.open(dbFile, options);
    }
}
