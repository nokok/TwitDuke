package net.nokok.twitduke.core.typesafe;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

public class TypeSafeDataStoreTest {

    private TypeSafeDataStore map;

    @Before
    public void setUp() {
        map = new TypeSafeDataStore();
    }

    /**
     * 全てのパラメータはnull以外でなければならない
     */
    @Test(expected = NullPointerException.class)
    public void nullParameterTest() {
        map.addEntry(null, null, null, null);
    }

    @Test(expected = NullPointerException.class)
    public void nullTypePutTest() {
        map.addEntry(String.class, null, "key", "value");
    }

    @Test(expected = NullPointerException.class)
    public void nullKeyPutTest() {
        map.addEntry(String.class, String.class, null, "hoge");
    }

    @Test(expected = NullPointerException.class)
    public void nullValuePutTest() {
        map.addEntry(String.class, String.class, "key", null);
    }

    @Test(expected = NullPointerException.class)
    public void nullTypeGetTest() {
        map.getEntry(null, "key");
    }

    @Test(expected = NullPointerException.class)
    public void nullKeyGetTest() {
        map.getEntry(String.class, null);
    }

    /**
     * キーが重複する場合既にあるキーが破棄され、新しくputされる
     */
    @Test
    public void duplicateKeyTest() {
        String key = "key";
        map.addEntry(String.class, String.class, key, "value1");
        map.addEntry(String.class, String.class, key, "value2");
        assertTrue(map.getEntry(String.class, key).isPresent());
        assertEquals(map.getEntry(String.class, key).get(), "value2");
    }

    /**
     * getObjectで返ってくるオブジェクトは、元の型情報が残っているべき
     */
    @Test
    public void resultTypeTest() {
        Entry<String, String> entry = EntryBuilder.newEntryType(String.class, String.class).key("key").value("value").create();
        map.addEntry(entry);
        String key = "key";
        map.addEntry(String.class, String.class, key, "value");
        assertTrue(map.getEntry(String.class, key).isPresent());
        assertFalse(map.getEntry(Number.class, key).isPresent());
        assertEquals(map.getEntry(String.class, key).get().getClass(), String.class);
        String intKey = "intKey";
        map.addEntry(String.class, Integer.class, intKey, 123);
        assertTrue(map.getEntry(Integer.class, intKey).isPresent());
        assertEquals(map.getEntry(Integer.class, intKey).get().getClass(), Integer.class);
        assertThat(map.getEntry(Number.class, intKey).get().getClass(), is(not(Number.class)));
    }

    /**
     * 指定したキーが存在しなかった場合、Optional.emptyを返すべきである。
     * いかなる例外もスローしてはならない
     */
    @Test
    public void valueNotFoundTest() {
        try {
            assertFalse(map.getEntry(String.class, "invalid key").isPresent());
        } catch (Throwable e) {
            fail();
        }
    }

    @Test
    public void castTest() {
        String key = "key";
        map.addEntry(String.class, Number.class, key, 123);
        assertTrue(map.getEntry(Integer.class, key).isPresent());
        assertTrue(map.getEntry(Integer.class, key).get() == 123);
        long value = 123;
        String longKey = "longkey";
        map.addEntry(String.class, Long.class, longKey, value);
        //LongはNumberへキャスト可能
        assertTrue(map.getEntry(Number.class, longKey).isPresent());
    }

    /**
     * キャスト出来ない場合Optional.emptyを返すべき
     */
    @Test
    public void canNotCastTest() {
        String key = "key";
        map.addEntry(String.class, String.class, key, "123");
        assertTrue(map.getEntry(String.class, key).isPresent());
        //StringはIntegerにキャスト出来ないのでempty
        assertFalse(map.getEntry(Integer.class, key).isPresent());
        //StringのスーパークラスはObjectなのでキャスト可能
        assertTrue(map.getEntry(Object.class, key).isPresent());
        //返ってくる値はObjectではなくString
        assertEquals(map.getEntry(Object.class, key).get(), "123");
    }

    @Test
    public void sameTypeTest() {
        String intkey = "intkey";
        String longkey = "longkey";
        map.addEntry(String.class, Number.class, intkey, 123);
        map.addEntry(String.class, Number.class, longkey, 123L);
        assertEquals(map.getEntry(Number.class, intkey).get().getClass(), Integer.class);
        assertThat(map.getEntry(Number.class, intkey).get().getClass(), is(not(Number.class)));
    }

    @Test
    public void testEquals() {
        String key = "key";
        String value = "value";
        TypeSafeDataStore map1 = new TypeSafeDataStore();
        map1.addEntry(String.class, String.class, key, value);
        TypeSafeDataStore map2 = new TypeSafeDataStore();
        map2.addEntry(String.class, String.class, key, value);
        assertTrue(map1.equals(map2));
        map1.addEntry(String.class, Integer.class, "key1", 123);
        assertFalse(map1.equals(map2));
        assertFalse(map1.equals(new Object()));
        assertFalse(map1.equals(null));
    }

    @Test
    public void testHashCode() {
        String key = "key";
        String value = "value";
        map.addEntry(String.class, String.class, key, value);
        TypeSafeDataStore map1 = new TypeSafeDataStore();
        map1.addEntry(String.class, String.class, key, value);
        assertTrue(map.hashCode() == map1.hashCode());
        map1.addEntry(String.class, Integer.class, "key1", 123);
        assertFalse(map.hashCode() == map1.hashCode());
    }

    @Test
    public void testHasEntry() {
        map.addEntry(String.class, Integer.class, "key", 123);
        assertTrue(map.hasKey("key"));
        assertFalse(map.hasKey("not key"));
    }

    @Test
    public void testCanCast() {
        map.addEntry(String.class, Integer.class, "key", 123);
        assertTrue(map.canCast(Number.class, "key"));
        assertFalse(map.canCast(String.class, "key"));
    }

    @Test
    public void testRemove() {
        String key = "key";
        map.addEntry(String.class, Integer.class, key, 123);
        assertTrue(map.hasKey(key));
        int result = map.remove(Integer.class, key);
        assertFalse(map.hasKey(key));
        assertTrue(result == 123);
    }
}
