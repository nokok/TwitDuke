/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.nokok.twitduke.core.type;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author noko <nokok.kz at gmail.com>
 */
public class TweetTest {

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNull() {
        new Tweet(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void tooLongText() {
        new Tweet("hogehogehogehogehogehogehogehogehogehogehogehogehogehogehoge"
                  + "hogehogehogehogehogehogehogehogehogehogehogehogehogehogehoge"
                  + "hogehogehogehogehogehogehogehogehogehogehogehogehogehoge");
    }

    @Test
    public void testGet() {
        String value = "hoge";
        Tweet tweet = new Tweet(value);
        assertEquals(value, tweet.get());
    }
}
