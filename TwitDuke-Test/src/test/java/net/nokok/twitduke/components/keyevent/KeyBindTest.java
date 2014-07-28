package net.nokok.twitduke.components.keyevent;

import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static org.junit.Assert.*;

/**
 * Created by wtnbsts on 2014/07/24.
 */
public class KeyBindTest {

    private static final String fnKeyStroke = "keyStroke";
    private static final String fnComponentName = "targetComponentName";
    private static final String fnComponentCondition = "targetComponentCondition";
    private static final String fnRemoveFunc = "removeFunc";

    private KeyBind bind;

    @Before
    public void setUp() {
        bind = new KeyBind();
    }

    /**
     * コンストラクタ
     */
    @Test
    public void testConstructor() throws Exception {
        assertNull(getStringValue(fnKeyStroke));
        assertNull(getStringValue(fnComponentName));
        assertEquals(getIntValue(fnComponentCondition), 0);
        assertNull(getValue(fnRemoveFunc));
    }

    /**
     *
     */
    @Test
    public void testGetKeyStroke() throws Exception {
        String value = "ctrl K";
        bind.setKeyStroke(value);
        assertEquals(value, getStringValue(fnKeyStroke));
    }

    /**
     *
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
     */
    @Test
    public void testGetTargetComponentName() throws Exception {
        String value = "AnyComponent";
        bind.setTargetComponentName(value);
        assertEquals(value, bind.getTargetComponentName());
        bind.setTargetComponentName(null);
        assertEquals(null, bind.getTargetComponentName());
    }

    /**
     *
     */
    @Test
    public void testSetTargetComponentName() throws Exception {
        String value = "AnyComponent";
        bind.setTargetComponentName(value);
        assertEquals(value, getStringValue(fnComponentName));
        bind.setTargetComponentName(null);
        assertEquals(null, getStringValue(fnComponentName));
    }

    /**
     *
     */
    @Test
    public void testGetTargetComponentCondition() throws Exception {
        int value = JComponent.WHEN_FOCUSED;
        bind.setTargetComponentCondition(value);
        assertEquals(value, bind.getTargetComponentCondition());
        bind.setTargetComponentCondition(JComponent.UNDEFINED_CONDITION);
        assertEquals(JComponent.UNDEFINED_CONDITION, bind.getTargetComponentCondition());
    }

    /**
     *
     */
    @Test
    public void testSetTargetComponentCondition() throws Exception {
        int value = JComponent.WHEN_FOCUSED;
        bind.setTargetComponentCondition(value);
        assertEquals(value, getIntValue(fnComponentCondition));
        bind.setTargetComponentCondition(JComponent.UNDEFINED_CONDITION);
        assertEquals(JComponent.UNDEFINED_CONDITION, getIntValue(fnComponentCondition));
    }

    /**
     *
     */
    @Test
    public void testSetRemoveFunc() throws Exception {
        Consumer<KeyBind> value = src -> {
        };
        bind.setRemoveFunc(value);
        assertEquals(value, getValue(fnRemoveFunc));
        bind.setRemoveFunc(null);
        assertEquals(null, getValue(fnRemoveFunc));
    }

    /**
     *
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
     */
    @Test
    public void testEqualsT2() throws Exception {
        KeyBind b1 = new KeyBind();
        KeyBind b2 = new KeyBind();
        b1.setKeyStroke("ctrl K");
        b2.setKeyStroke("ctrl K");
        b1.setTargetComponentCondition(JComponent.WHEN_IN_FOCUSED_WINDOW);
        b2.setTargetComponentCondition(JComponent.WHEN_IN_FOCUSED_WINDOW);
        b1.setTargetComponentName("Any");
        b2.setTargetComponentName("Any");
        Consumer<KeyBind> func = b1::equals;
        b1.setRemoveFunc(func);
        b2.setRemoveFunc(func);
        assertTrue(b1.hashCode() == b2.hashCode());
        assertTrue(b1.equals(b2));
        assertTrue(b2.equals(b1));
    }

    /**
     * キーが違うときの not equals
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
        b1.setTargetComponentName("Any");
        b2.setTargetComponentName("Other");
        assertTrue(!b1.equals(b2));
        assertTrue(!b2.equals(b1));
    }

    /**
     * ターゲットのフォーカスが違うときの not equal
     *
     * @throws Exception
     */
    @Test
    public void testEqualsF2() throws Exception {
        KeyBind b1 = new KeyBind();
        KeyBind b2 = new KeyBind();
        b1.setTargetComponentCondition(JComponent.WHEN_IN_FOCUSED_WINDOW);
        b2.setTargetComponentCondition(JComponent.WHEN_FOCUSED);
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
