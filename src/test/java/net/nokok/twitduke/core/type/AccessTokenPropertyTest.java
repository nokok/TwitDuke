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
package net.nokok.twitduke.core.type;

import java.util.Properties;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import twitter4j.auth.AccessToken;

/**
 *
 * @author noko
 */
public class AccessTokenPropertyTest {

    @Test
    public void testConstractor_AccessToken() {
        AccessToken accessToken = new AccessToken("hoge", "hoges");
        AccessTokenProperty accessTokenProperty = new AccessTokenProperty(accessToken);
        assertEquals(accessTokenProperty.token(), "hoge");
        assertEquals(accessTokenProperty.tokenSecret(), "hoges");
    }

    @Test
    public void testConstractor_Properties() {
        Properties properties = new Properties();
        properties.put("token", "token1");
        properties.put("tokensecret", "tokensecret");
        properties.put("name", "noko_k");
        properties.put("id", "1000");
        AccessTokenProperty property = new AccessTokenProperty(properties);
        assertEquals(property.token(), "token1");
        assertEquals(property.tokenSecret(), "tokensecret");
        assertEquals(property.screenName(), "noko_k");
        assertEquals(String.valueOf(property.id()), "1000");
        assertEquals(property.toProperties(), properties);
    }
}
