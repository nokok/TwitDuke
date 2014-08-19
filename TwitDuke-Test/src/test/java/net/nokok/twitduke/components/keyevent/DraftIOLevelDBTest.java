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
package net.nokok.twitduke.components.keyevent;

import java.util.Optional;
import net.nokok.twitduke.resources.draft.DraftIO;
import net.nokok.twitduke.resources.draft.DraftIOLevelDB;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

public class DraftIOLevelDBTest {

    private DraftIO db;

    @Before
    public void setUp() {
        db = new DraftIOLevelDB();
    }

    @Test
    public void testSaveDraft() {
        assertThat(db.draftList().isEmpty(), is(true));
        db.saveDraft("hoge");
        assertThat(db.draftList().isEmpty(), is(false));
    }

    @Test
    public void testRestore() {
        db.saveDraft("hoge");
        Optional<String> mayBeHoge = db.restore(0);
        assertThat(mayBeHoge.isPresent(), is(true));
        assertThat(mayBeHoge.get(), is("hoge"));
    }

    @Test
    public void testRemove() {
        db.saveDraft("hoge");
        db.saveDraft("fuga");
        db.remove(1);
        assertThat(db.draftList().get(0), is("hoge"));
        assertThat(db.restore(0).isPresent(), is(true));
        assertThat(db.restore(0).get(), is("hoge"));
        db.saveDraft("fuga");
        db.remove(0);
        assertThat(db.draftList().get(0), is("fuga"));
        assertThat(db.restore(0).isPresent(), is(true));
        assertThat(db.restore(0).get(), is("fuga"));
    }

}
