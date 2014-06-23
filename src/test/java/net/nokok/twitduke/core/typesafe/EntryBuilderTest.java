package net.nokok.twitduke.core.typesafe;

import java.util.Arrays;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class EntryBuilderTest {

    public EntryBuilderTest() {
    }

    @Test
    public void testValidValue() {
        Entry<String, Integer> entry = EntryBuilder
                .newEntryType(String.class, Integer.class)
                .key("key")
                .value(123)
                .create();
        assertEquals(entry.getKeyClazz(), String.class);
        assertEquals(entry.getValueClazz(), Integer.class);
        assertEquals(entry.getKey(), "key");
        assertEquals(entry.getValue(), new Integer(123));
    }

    @Test(expected = IllegalStateException.class)
    public void testInvalidState() {
        EntryBuilder.newEntryType(String.class, String.class).create();
    }

    @Test(expected = NullPointerException.class)
    public void testNullClazz1() {
        EntryBuilder.newEntryType(null, null);
    }

    @Test(expected = NullPointerException.class)
    public void testNullClazz2() {
        EntryBuilder.newEntryType(String.class, null);
    }

    @Test(expected = NullPointerException.class)
    public void testNullClazz3() {
        EntryBuilder.newEntryType(null, String.class);
    }

    @Test(expected = NullPointerException.class)
    public void testNewEntryType() {
        EntryBuilder.newEntryType(null, null);
    }

    @Test(expected = NullPointerException.class)
    public void testNullKey() {
        EntryBuilder.newEntryType(String.class, String.class).key(null);
    }

    @Test(expected = IllegalStateException.class)
    public void testCreate() {
        EntryBuilder.newEntryType(String.class, String.class).create();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testKey_GenericType_Predicate() {
        EntryBuilder.newEntryType(String.class, String.class).key("", EntryBuilder.NOT_EMPTY_STRING);
    }

    @Test(expected = NullPointerException.class)
    public void testNullKeyConstraint() {
        EntryBuilder.newEntryType(String.class, String.class).key("", null);
    }

    @Test
    public void testKeyWithConstraint1() {
        EntryBuilder.newEntryType(String.class, String.class).key("abc", str -> str.equals("abc"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testKeyWithConstraint2() {
        EntryBuilder.newEntryType(String.class, String.class).key("abcd", str -> str.length() <= 3);
    }

    @Test(expected = NullPointerException.class)
    public void testNullValue() {
        EntryBuilder.newEntryType(String.class, String.class).value(null);
    }

    @Test(expected = NullPointerException.class)
    public void testNullValueConstraint() {
        EntryBuilder.newEntryType(String.class, String.class).value("", null);
    }

    @Test
    public void testValueWithConstraint1() {
        EntryBuilder.newEntryType(String.class, String.class).value("abc", str -> str.equals("abc"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValueWithConstraint2() {
        EntryBuilder.newEntryType(String.class, String.class).value("", EntryBuilder.NOT_EMPTY_STRING);
    }

    @Test
    public void testAddKeyConstraint() {
        String key = "key";
        Entry<String, Integer> entry = EntryBuilder.newEntryType(String.class, Integer.class)
                .addKeyConstraint(str -> !str.isEmpty())
                .key(key).value(123)
                .create();
        assertEquals(entry.getKey(), "key");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddInvalidKeyConstraint() {
        EntryBuilder<String, Integer> builder = EntryBuilder.newEntryType(String.class, Integer.class)
                .key("key");
        try {
            builder.addKeyConstraint(str -> str.length() == 4);
        } catch (Exception e) {
            throw e;
        }
    }

    @Test
    public void testAddKeyConstraints() {
        EntryBuilder.newEntryType(String.class, Integer.class)
                .addKeyConstraint(Arrays.asList(str -> str.length() > 3,
                                                str -> !str.isEmpty()))
                .key("hoge");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddEmptyKeyConstraints() {
        EntryBuilder.newEntryType(String.class, Integer.class)
                .addKeyConstraint(Arrays.asList())
                .key("hoge");
    }
}
