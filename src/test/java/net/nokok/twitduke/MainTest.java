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
package net.nokok.twitduke;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author noko
 */
public class MainTest {

    private Main main;

    @Before
    public void setUp() {
        main = new Main();
    }

    @Test
    public void testMain() {
        try {
            Main.main(null);
            assertFalse(Main.isCliMode());
            assertFalse(Main.isDebugMode());
        } catch (Throwable e) {
            fail();
        }
    }

    @Test
    public void testIsDebugMode() {
        try {
            Main.main(new String[]{"-debug"});
            assertTrue(Main.isDebugMode());
            assertFalse(Main.isCliMode());
        } catch (Throwable e) {
            fail();
        }
    }

    @Test
    public void testIsCliMode() {
        try {
            Main.main(new String[]{"-cli"});
            assertFalse(Main.isDebugMode());
            assertTrue(Main.isCliMode());
        } catch (Throwable e) {
            fail();
        }
    }

    @Test
    public void testAllOptions() {
        try {
            Main.main(new String[]{"-cli", "-debug"});
            assertTrue(Main.isCliMode());
            assertTrue(Main.isDebugMode());
        } catch (Throwable e) {
            fail();
        }
    }

    @Test
    public void testInvalidOptions() {
        try {
            Main.main(new String[]{"cli", "debug", "-deb", "-", " "});
            assertFalse(Main.isCliMode());
            assertFalse(Main.isDebugMode());
        } catch (Throwable e) {
            fail();
        }
    }
}
