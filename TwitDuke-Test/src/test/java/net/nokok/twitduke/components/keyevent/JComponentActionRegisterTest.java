package net.nokok.twitduke.components.keyevent;

import com.google.common.io.Resources;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import javax.swing.KeyStroke;
import net.nokok.twitduke.components.actions.Action_CutUpToLineEnd;
import net.nokok.twitduke.components.actions.Action_Paste;
import net.nokok.twitduke.components.basics.TWPanel;
import net.nokok.twitduke.core.view.ScrollableTimelinePanel;
import net.nokok.twitduke.core.view.TweetTextArea;
import net.nokok.twitduke.core.view.Window;
import org.junit.After;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import twitter4j.auth.AccessToken;

/**
 * Created by wtnbsts on 2014/07/28.
 */
public class JComponentActionRegisterTest {

    private IKeyMapStore store;
    private TestWindowAdapter windowAdapter;
    private IActionRegister register;
    private final URL xmlPath
                      = Resources.getResource(String.join(File.separator, "keyevent", "default_actiontest.xml"));

    @Before
    public void setUp() throws Exception {
        if ( !Desktop.isDesktopSupported() ) {
            return;
        }
        windowAdapter = new TestWindowAdapter();
        register = IActionRegister.newInstance(windowAdapter.getRootContainer());
        store = IKeyMapStore.newInstance();
    }

    @After
    public void tearDown() {
        if ( !Desktop.isDesktopSupported() ) {
            return;
        }
        windowAdapter.getWindow().dispose();
    }

    @Test
    public void testRegisterKeyMap() throws Exception {
        if ( !Desktop.isDesktopSupported() ) {
            return;
        }
        IKeyMapSetting setting = store.load(xmlPath.openStream());
        register.registerKeyMap(setting);
        ActionListener listener;

        listener = windowAdapter.getTextArea().getActionForKeyStroke(KeyStroke.getKeyStroke("ctrl K"));
        assertTrue(listener instanceof Action_CutUpToLineEnd);
        listener = windowAdapter.getTextArea().getActionForKeyStroke(KeyStroke.getKeyStroke("meta K"));
        assertTrue(listener instanceof Action_CutUpToLineEnd);

        listener = windowAdapter.getTextArea().getActionForKeyStroke(KeyStroke.getKeyStroke("ctrl Y"));
        assertTrue(listener instanceof Action_Paste);
        listener = windowAdapter.getTextArea().getActionForKeyStroke(KeyStroke.getKeyStroke("meta Y"));
        assertTrue(listener instanceof Action_Paste);
    }

    @Test
    public void testUnregisterAll() throws Exception {
        if ( !Desktop.isDesktopSupported() ) {
            return;
        }
        IKeyMapSetting setting = store.load(xmlPath.openStream());
        register.registerKeyMap(setting);
        register.unregisterAll();

        assertNull(windowAdapter.getTextArea().getActionForKeyStroke(KeyStroke.getKeyStroke("ctrl K")));
        assertNull(windowAdapter.getTextArea().getActionForKeyStroke(KeyStroke.getKeyStroke("meta K")));

        assertNull(windowAdapter.getTextArea().getActionForKeyStroke(KeyStroke.getKeyStroke("ctrl Y")));
        assertNull(windowAdapter.getTextArea().getActionForKeyStroke(KeyStroke.getKeyStroke("meta Y")));
    }

    private Object getFieldValue(Object parent, String fieldName) throws Exception {
        Field field = parent.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(parent);
    }

    private class TestWindowAdapter {

        private final Window window = new Window();
        private final TweetTextArea textArea;
        private final TWPanel panel;
        private final ScrollableTimelinePanel scrollableTimelinePanel;

        public TestWindowAdapter() {
            scrollableTimelinePanel = new ScrollableTimelinePanel();
            window.addContents(scrollableTimelinePanel);
            AccessToken token = new AccessToken("hoge", "moge");
            textArea = new TweetTextArea(token);
            textArea.setPreferredSize(new Dimension(-1, 50));
            panel = new TWPanel(new BorderLayout());
            panel.add(textArea, BorderLayout.CENTER);
            window.addHeader(panel);
        }

        public Window getWindow() {
            return window;
        }

        public TweetTextArea getTextArea() {
            return textArea;
        }

        public TWPanel getPanel() {
            return panel;
        }

        public ScrollableTimelinePanel getScrollableTimelinePanel() {
            return scrollableTimelinePanel;
        }

        public Container getRootContainer() throws Exception {
            return (Container) getFieldValue(window, "frame");
        }
    }
}
