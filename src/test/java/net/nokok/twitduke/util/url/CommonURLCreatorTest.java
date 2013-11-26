package net.nokok.twitduke.util.url;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class CommonURLCreatorTest {

    CommonURLCreator urlCreator;

    public CommonURLCreatorTest() {
        urlCreator = new CommonURLCreator();
    }

    @Test
    public void testIsValidURLFormat() throws Exception {
        String validURL = "http://google.com/";
        String invalidURL = "ttp://google.com/";
        Boolean successResult = urlCreator.isValidURLFormat(validURL);
        Boolean failResult = urlCreator.isValidURLFormat(invalidURL);
        assertEquals("URLのチェックが正しく行われていません:isValidURLFormat", Boolean.TRUE, successResult);
        assertEquals("URLのチェックが正しく行われていません:isValidURLFormat", Boolean.FALSE, failResult);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCheckURL() throws Exception {
        String validURL = "http://google.com/";
        String blankURL = "";
        String invalidURL = "http//google.com/";
        Boolean validURLResult = urlCreator.checkURL(validURL);
        Boolean blankURLResult = urlCreator.checkURL(blankURL);
        Boolean invalidURLResult = urlCreator.checkURL(invalidURL);
        assertEquals("URLのチェックが正しく行われていません:checkURL これは本来正しいURLです", Boolean.TRUE, validURLResult);
        assertEquals("空のURLのチェックが正しく行われていません:checkURL ", Boolean.FALSE, blankURLResult);
        assertEquals("不正なURLのチェックが正しく行われていません:checkURL", Boolean.FALSE, invalidURLResult);
    }
}
