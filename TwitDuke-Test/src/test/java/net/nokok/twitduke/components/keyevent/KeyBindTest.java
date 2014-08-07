package net.nokok.twitduke.components.keyevent;

import java.lang.reflect.Field;
import javafx.scene.input.KeyCombination;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Created by wtnbsts on 2014/07/24.
 */
public class KeyBindTest {

    private static final String fnKeyStroke = "keyStroke";
    private static final String fnComponentName = "selector";

    /**
     * コンストラクタ
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testConstructor() throws Exception {
        KeyBind bind = new KeyBind(null, null);
        assertNull(getStringValue(bind, fnKeyStroke));
        assertNull(getStringValue(bind, fnComponentName));
    }

    /**
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testGetKeyStroke() throws Exception {
        String value = "ctrl+K";
        KeyBind bind = new KeyBind(KeyCombination.keyCombination(value), null);
        assertEquals(KeyCombination.keyCombination(value), getValue(bind, fnKeyStroke));
    }

    /**
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testGetSelector() throws Exception {
        String value = "AnyComponent";
        KeyBind bind = new KeyBind(null, value);
        assertEquals(value, bind.getSelector());
    }

    @Test
    public void testHashCode() throws Exception {
        KeyBind bind1 = new KeyBind(null, null);
        KeyBind bind2 = new KeyBind(KeyCombination.keyCombination("ctrl+k"), null);
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
     * 空っぽのオブジェクトのequals
     *
     * @throws Exception
     */
    @Test
    public void testEqualsT1() throws Exception {
        KeyBind b1 = new KeyBind(null, null);
        KeyBind b2 = new KeyBind(null, null);
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
