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

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.nokok.twitduke.resources.LevelDB;

public class DraftIOLevelDB implements DraftIO {

    private final List<String> draftList = new ArrayList<>();
    private final String FILE_NAME = "draft";

    @Override
    public List<String> draftList() {
        return draftList;
    }

    @Override
    public void remove(Integer index) {
        try (LevelDB db = LevelDB.newInstance(FILE_NAME)) {
            db.delete(index.toString());
            draftList.remove(index.intValue()); //call remove(int index)
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } catch (Exception e) {
            //Exception in AutoCloseable#close
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<String> restore(Integer index) {
        return Optional.of(draftList.get(index));
    }

    @Override
    public void saveDraft(String text) {
        try (LevelDB db = LevelDB.newInstance(FILE_NAME)) {
            db.put(String.valueOf(draftList.size()), text);
            draftList.add(text);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
