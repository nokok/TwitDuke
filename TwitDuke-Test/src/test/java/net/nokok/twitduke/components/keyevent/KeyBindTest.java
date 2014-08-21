/*
 * The MIT License
 *
 * Copyright 2014 satanabe1.
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

import java.lang.reflect.Field;
import javafx.scene.input.KeyCombination;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Created by wtnbsts on 2014/07/24.
 */
public class KeyBindTest {

    private static final String fnKeyStroke = "keyStroke";
    private static final String fnComponentName = "selector";

    /**
     * インスタンス生成成功
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testConstructorS() throws Exception {
        KeyBind bind = new KeyBind(KeyCombination.NO_MATCH, "any");
        assertEquals(KeyCombination.NO_MATCH, getValue(bind, fnKeyStroke));
        assertEquals("any", getStringValue(bind, fnComponentName));
    }

    /**
     * インスタンス生成失敗
     *
     * @throws Exception
     */
    @Test(expected = NullPointerException.class)
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public void testConstructorF0() throws Exception {
        new KeyBind(KeyCombination.NO_MATCH, null);
    }

    /**
     * インスタンス生成失敗
     *
     * @throws Exception
     */
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    @Test(expected = NullPointerException.class)
    public void testConstructorF1() throws Exception {
        new KeyBind(null, "any");
    }

    /**
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testGetKeyStroke() throws Exception {
        String value = "ctrl+K";
        KeyBind bind = new KeyBind(KeyCombination.keyCombination(value), "any");
        assertEquals(KeyCombination.keyCombination(value), getValue(bind, fnKeyStroke));
    }

    /**
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testGetSelector() throws Exception {
        String value = "AnyComponent";
        KeyBind bind = new KeyBind(KeyCombination.NO_MATCH, value);
        assertEquals(value, bind.getSelector());
    }

    @Test
    public void testHashCode() throws Exception {
        KeyBind bind1 = new KeyBind(KeyCombination.NO_MATCH, "any");
        KeyBind bind2 = new KeyBind(KeyCombination.keyCombination("ctrl+k"), "any");
        assertTrue(bind1.hashCode() != bind2.hashCode());
    }

    /**
     * 自分自身とのequals
     *
     * @throws Exception
     */
    @Test
    public void testEqualsT0() throws Exception {
        KeyBind bind = new KeyBind(KeyCombination.keyCombination("ctrl+k"), "any");
        assertTrue(bind.equals(bind));
    }

    /**
     * 中身のあるオブジェクトのequals
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testEqualsT2() throws Exception {
        KeyBind b1 = new KeyBind(KeyCombination.keyCombination("alt+k"), "any");
        KeyBind b2 = new KeyBind(KeyCombination.keyCombination("alt+k"), "any");
        assertTrue(b1.hashCode() == b2.hashCode());
        assertTrue(b1.equals(b2));
        assertTrue(b2.equals(b1));
    }

    /**
     * キーが違うときの not equals
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testEqualsF0() throws Exception {
        KeyBind b1 = new KeyBind(KeyCombination.keyCombination("alt+k"), "any");
        KeyBind b2 = new KeyBind(KeyCombination.keyCombination("k"), "any");
        assertTrue(!b1.equals(b2));
        assertTrue(!b2.equals(b1));
    }

    /**
     * ターゲット名が違うときの not equal
     *
     * @throws Exception
     */
    @Test
    public void testEqualsF1() throws Exception {
        KeyBind b1 = new KeyBind(KeyCombination.keyCombination("alt+k"), "any");
        KeyBind b2 = new KeyBind(KeyCombination.keyCombination("alt+k"), "other");
        assertTrue(!b1.equals(b2));
        assertTrue(!b2.equals(b1));
    }

    private Field getField(final KeyBind bind, final String fieldName) throws NoSuchFieldException {
        Field field = bind.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field;
    }

    private Object getValue(final KeyBind bind, final String fieldName)
        throws NoSuchFieldException, IllegalAccessException {
        return getField(bind, fieldName).get(bind);
    }

    private String getStringValue(final KeyBind bind, final String fieldName)
        throws NoSuchFieldException, IllegalAccessException {
        return (String) getValue(bind, fieldName);
    }

    private int getIntValue(final KeyBind bind, final String fieldName)
        throws NoSuchFieldException, IllegalAccessException {
        return getField(bind, fieldName).getInt(bind);
    }
}
