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

import java.io.IOException;
import static org.hamcrest.CoreMatchers.is;
import org.junit.After;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

public class LevelDBImplTest {

    private final String key = "key";
    private final String value = "value";
    private LevelDB levelDB;

    @Before
    public void setUp() throws IOException {
        levelDB = new LevelDBImpl("tmp_unit_test_dbfile");
    }

    @After
    public void tearDown() throws Exception {
        levelDB.close();
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorNullValue() throws IOException {
        String hoge = null;
        new LevelDBImpl(hoge);
    }

    @Test(expected = NullPointerException.class)
    public void testDeleteNullValue() throws Exception {
        levelDB.delete(null);
    }

    @Test(expected = NullPointerException.class)
    public void testGetNullValue() throws Exception {
        levelDB.get(null);
    }

    @Test(expected = NullPointerException.class)
    public void testPutKeyNullValue() throws Exception {
        levelDB.put(null, value);
    }

    @Test(expected = NullPointerException.class)
    public void testPuValueNullValue() throws Exception {
        levelDB.put(key, null);
    }

    @Test
    public void testDBOperation() throws Exception {
        levelDB.put(key, value);
        assertThat(levelDB.get(key).isPresent(), is(true));
        assertThat(levelDB.get(key).get(), is(value));
        levelDB.delete(key);
        assertThat(levelDB.get(key).isPresent(), is(false));
    }

}
