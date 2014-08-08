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
import net.nokok.twitduke.base.optional.OptionalUtil;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public class OptionalUtilTest {

    public OptionalUtilTest() {
    }

    @Test
    public void testIsPresent() {
        Optional<String> op = Optional.of("hoge");
        assertThat(OptionalUtil.isPresent(op), is(true));
        Optional<String> empty = Optional.empty();
        assertThat(OptionalUtil.isPresent(empty), is(false));
    }

    @Test
    public void testGet() {
        Optional<String> op = Optional.of("fuga");
        assertThat(OptionalUtil.get(op), is("fuga"));
        assertThat(OptionalUtil.get(op), is(not(Optional.empty())));
    }

    @Test
    public void testGetIfPresent() {
        Optional<String> op = Optional.of("piyo");
        assertThat(OptionalUtil.getIfPresent(op), is("piyo"));

    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetIfPresentWithEmptyObject() {
        Optional<String> empty = Optional.empty();
        OptionalUtil.getIfPresent(empty);
    }

}
