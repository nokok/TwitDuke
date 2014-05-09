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
package net.nokok.twitduke.pluginsupport;

import net.nokok.twitduke.core.type.Tweet;
import net.nokok.twitduke.util.CommonTestUtil;
import net.nokok.twitduke.util.MockAsyncTwitter;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class FooterTest {

    private Footer footer;

    @Before
    public void setUp() {
        footer = new Footer(new MockAsyncTwitter());
    }

    /**
     * フッターなしの140文字以上の文字列をつぶやくテスト
     */
    @Test(expected = IllegalArgumentException.class)
    public void testTweetOfTheOver140CharactersWithoutFooter() {
        footer.setFooterText("");
        footer.sendTweet(new Tweet(CommonTestUtil.STRING_OF_141_CHARACTERS));
    }

    /**
     * フッター付きで合計140文字以上の文字列をつぶやくテスト
     */
    @Test(expected = IllegalArgumentException.class)
    public void testTweetOfTheOver140CharactersWithFooter() {
        footer.setFooterText("hoge");
        footer.sendTweet(new Tweet(CommonTestUtil.STRING_OF_140_CHARACTERS));
    }

    /**
     * フッターがきちんとセットされているかのテスト
     */
    @Test
    public void testSetFooterText() {
        footer.setFooterText("a");
        assertTrue(footer.footerLength() == 1);
        footer.setFooterText("ab");
        assertTrue(footer.footerLength() == 2);
        footer.setFooterText("a");
        assertTrue(footer.footerLength() == 1);
        footer.setFooterText(CommonTestUtil.STRING_OF_140_CHARACTERS);
        assertTrue(footer.footerLength() == 140);
    }

    /**
     * フッターに140文字以上の文字列を指定した場合のテスト
     */
    @Test(expected = IllegalArgumentException.class)
    public void whenOver140CharactersInSetFooterTextArg() {
        footer.setFooterText(CommonTestUtil.STRING_OF_141_CHARACTERS);
    }

    /**
     * 初期化直後はフッターが空
     */
    @Test
    public void testFooterIsEmptyInInitialize() {
        assertTrue(footer.footerLength() == 0);
    }

    /**
     * フッターがリセットされるテスト
     */
    @Test
    public void testResetFooter() {
        footer.setFooterText("hoge");
        assertTrue(footer.footerLength() == 4);
        footer.resetFooter();
        assertTrue(footer.footerLength() == 0);
    }
}
