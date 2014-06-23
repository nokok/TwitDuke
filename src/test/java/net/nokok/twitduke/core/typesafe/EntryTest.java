package net.nokok.twitduke.core.typesafe;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class EntryTest {

    @Test
    public void testGetKey() {
        Entry<String, Integer> entry = new Entry<>();
        assertTrue(entry.getKey() == null);
    }

    @Test
    public void testGetKeyClazz() {
        Entry<String, Integer> entry = new Entry<>();
        assertTrue(entry.getKeyClazz() == null);
    }

    @Test
    public void testGetValue() {
        Entry<String, Integer> entry = new Entry<>();
        assertTrue(entry.getValue() == null);
    }

    @Test
    public void testGetValueClazz() {
        Entry<String, Integer> entry = new Entry<>();
        assertTrue(entry.getValueClazz() == null);
    }

    @Test
    public void testSetKey() {
        Entry<String, Integer> entry = new Entry<>();
        entry.setKey("hoge");
        assertEquals(entry.getKey(), "hoge");
    }

    @Test
    public void testSetKeyClazz() {
        Entry<String, Integer> entry = new Entry<>();
        entry.setKeyClazz(String.class);
        assertEquals(entry.getKeyClazz(), String.class);
    }

    @Test
    public void testSetValue() {
        Entry<String, Integer> entry = new Entry<>();
        entry.setValue(123);
        assertEquals(entry.getValue(), new Integer(123));
    }

    @Test
    public void testSetValueClazz() {
        Entry<String, Integer> entry = new Entry<>();
        entry.setValueClazz(Integer.class);
        assertEquals(entry.getValueClazz(), Integer.class);
    }

    @Test
    public void testToString() {
        Entry<String, Integer> entry = EntryBuilder.newEntryType(String.class, Integer.class).key("hoge").value(123).create();
        assertEquals(entry.toString(), "[KeyClass:java.lang.String,Key:hoge,ValueClass:java.lang.Integer,Value:123]");
    }
}
