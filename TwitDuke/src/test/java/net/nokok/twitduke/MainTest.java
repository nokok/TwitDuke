package net.nokok.twitduke;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.fail;
import org.junit.Test;

/**
 *
 * @author noko <nokok.kz at gmail.com>
 */
public class MainTest {

    @Test
    public void testMain() {
        try {
            Main.main(null);
        } catch (Throwable e) {
            fail(e.toString());
        }
    }

    @Test
    public void testDebugMode() {
        try {
            Main main = new Main();
            assertFalse(main.isDebugMode());
        } catch (Throwable e) {
            fail(e.toString());
        }
    }
}
