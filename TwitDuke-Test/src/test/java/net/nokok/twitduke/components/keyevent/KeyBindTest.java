package net.nokok.twitduke.components.keyevent;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by wtnbsts on 2014/07/24.
 */
public class KeyBindTest {

    private static final String fnKeyStroke = "keyStroke";
    private static final String fnComponentName = "selector";
    private static final String fnRemoveFunc = "removeFunc";

    private KeyBind bind;

    @Before
    public void setUp() {
        bind = new KeyBind();
    }

    /**
     * コンストラクタ
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testConstructor() throws Exception {
        assertNull(getStringValue(fnKeyStroke));
        assertNull(getStringValue(fnComponentName));

        assertNull(getValue(fnRemoveFunc));
    }

    /**
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testGetKeyStroke() throws Exception {
        String value = "ctrl K";
        bind.setKeyStroke(value);
        assertEquals(value, getStringValue(fnKeyStroke));
    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void testSetKeyStroke() throws Exception {
        String value = "ctrl K";
        bind.setKeyStroke(value);
        assertEquals(value, getStringValue(fnKeyStroke));
        bind.setKeyStroke(null);
        assertEquals(null, getStringValue(fnKeyStroke));
    }

    /**
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testGetSelector() throws Exception {
        String value = "AnyComponent";
        bind.setSelector(value);
        assertEquals(value, bind.getSelector());
        bind.setSelector(null);
        assertEquals(null, bind.getSelector());
    }

    /**
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testSetSelector() throws Exception {
        String value = "AnyComponent";
        bind.setSelector(value);
        assertEquals(value, getStringValue(fnComponentName));
        bind.setSelector(null);
        assertEquals(null, getStringValue(fnComponentName));
    }

    /**
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testSetRemoveFunc() throws Exception {
        Consumer<KeyBind> value = System.out::println;
        bind.setRemoveFunc(value);
        assertEquals(value, getValue(fnRemoveFunc));
        bind.setRemoveFunc(null);
        assertEquals(null, getValue(fnRemoveFunc));
    }

    /**
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testRemove() throws Exception {
        List<KeyBind> buf = new ArrayList<>();
        bind.setRemoveFunc(buf::add);
        bind.remove();
        assertEquals(1, buf.size());
    }

    @Test
    public void testHashCode() throws Exception {
        assertEquals(bind.hashCode(), 0);
        bind.setKeyStroke("Any");
        assertNotSame(bind.hashCode(), 0);
    }

    /**
     * 自分自身とのequals
     *
     * @throws Exception
     */
    @Test
    public void testEqualsT0() throws Exception {
        assertTrue(bind.equals(bind));
    }

    /**
     * 空っぽのオブジェクトのequals
     *
     * @throws Exception
     */
    @Test
    public void testEqualsT1() throws Exception {
        KeyBind b1 = new KeyBind();
        KeyBind b2 = new KeyBind();
        assertTrue(b1.hashCode() == b2.hashCode());
        assertTrue(b1.equals(b2));
        assertTrue(b2.equals(b1));
    }

    /**
     * 中身のあるオブジェクトのequals
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testEqualsT2() throws Exception {
        KeyBind b1 = new KeyBind();
        KeyBind b2 = new KeyBind();
        b1.setKeyStroke("ctrl K");
        b2.setKeyStroke("ctrl K");
        b1.setSelector("Any");
        b2.setSelector("Any");
        Consumer<KeyBind> func = b1::equals;
        b1.setRemoveFunc(func);
        b2.setRemoveFunc(func);
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
        KeyBind b1 = new KeyBind();
        KeyBind b2 = new KeyBind();
        b1.setKeyStroke("ctrl K");
        b2.setKeyStroke("");
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
        KeyBind b1 = new KeyBind();
        KeyBind b2 = new KeyBind();
        b1.setSelector("Any");
        b2.setSelector("Other");
        assertTrue(!b1.equals(b2));
        assertTrue(!b2.equals(b1));
    }

    /**
     * コールバックが違うときの not equal
     *
     * @throws Exception
     */
    @Test
    public void testEqualsF3() throws Exception {
        KeyBind b1 = new KeyBind();
        KeyBind b2 = new KeyBind();
        b1.setRemoveFunc(b1::equals);
        b2.setRemoveFunc(b2::equals);
        assertTrue(!b1.equals(b2));
        assertTrue(!b2.equals(b1));
    }

    private Field getField(final String fieldName) throws NoSuchFieldException {
        Field field = bind.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field;
    }

    private Object getValue(final String fieldName)
            throws NoSuchFieldException, IllegalAccessException {
        return getField(fieldName).get(bind);
    }

    private String getStringValue(final String fieldName)
            throws NoSuchFieldException, IllegalAccessException {
        return (String) getValue(fieldName);
    }

    private int getIntValue(final String fieldName)
            throws NoSuchFieldException, IllegalAccessException {
        return getField(fieldName).getInt(bind);
    }
}
