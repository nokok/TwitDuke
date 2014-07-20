package net.nokok.twitduke.xsi.pic;

import java.net.MalformedURLException;
import java.net.URL;
import net.nokok.twitduke.xsi.pic.PictureFetcherFactory.NopPictureFetcher;
import net.nokok.twitduke.xsi.pic.gyazo.GyazoImageImpl;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.matchers.JUnitMatchers;

/**
 *
 * @author noko
 */
public class PictureFetcherFactoryTest extends JUnitMatchers {

    @Test
    public void testGyazoFetcher() throws MalformedURLException {
        URL url = new URL("http://gyazo.com/hogehoge");
        PictureFetcher gyazoFetcher = PictureFetcherFactory.newInstance(url);
        assertTrue(gyazoFetcher instanceof GyazoImageImpl);
    }

    @Test
    public void testNotSupportedServiceFetcher() throws MalformedURLException {
        URL url = new URL("http://google.com");
        PictureFetcher invalidURLFetcher = PictureFetcherFactory.newInstance(url);
        assertFalse(invalidURLFetcher instanceof GyazoImageImpl);
        assertTrue(invalidURLFetcher instanceof NopPictureFetcher);
    }

}
