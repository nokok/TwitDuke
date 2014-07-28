package net.nokok.twitduke.components.keyevent;

import com.google.common.io.Resources;
import net.nokok.twitduke.components.actions.Action_CutUpToLineEnd;
import net.nokok.twitduke.components.actions.Action_Paste;
import net.nokok.twitduke.components.basics.TWPanel;
import net.nokok.twitduke.core.view.ScrollableTimelinePanel;
import net.nokok.twitduke.core.view.TweetTextArea;
import net.nokok.twitduke.core.view.Window;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import twitter4j.auth.AccessToken;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static org.junit.Assert.assertTrue;

/**
 * Created by wtnbsts on 2014/07/28.
 */
public class ActionRegisterTest {

    IKeyMapStore store;
    private WindowAdapter windowAdapter;
    private IActionRegister register;
    private URL xmlPath =
            Resources.getResource(String.join(File.separator, "keyevent", "default_actiontest.xml"));

    private static boolean isTDComponent(JComponent component) {
        return component.getClass().getCanonicalName().startsWith("net.nokok");
    }

    private static List<JComponent> collectJComponent(Component root) {
        List<JComponent> components = new ArrayList<>();
        walk(root, components::add);
        return components;
    }

    private static void walk(Component component, Consumer<JComponent> callback) {
        if ( component instanceof JComponent ) {
            callback.accept((JComponent) component);
        }
        if ( component instanceof Container ) {
            Stream.of(((Container) component).getComponents()).forEach(c -> walk(c, callback));
        }
    }

    @Before
    public void setUp() throws Exception {
        windowAdapter = new WindowAdapter();
        register = IActionRegister.newInstance(windowAdapter.getRootContainer());
        store = IKeyMapStore.newInstance();
    }

    @After
    public void tearDown() {
        windowAdapter.getWindow().dispose();
    }

    @Test
    public void test() throws Exception {
        IKeyMapSetting setting = store.load(xmlPath.openStream());
        register.registerKeyMap(setting);
        List<JComponent> components = collectJComponent(windowAdapter.getRootContainer());
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
    @Ignore
    public void test2() throws Exception {
        IKeyMapSetting setting = store.load(xmlPath.openStream());
        register.registerKeyMap(setting);
        List<JComponent> components = collectJComponent(windowAdapter.getRootContainer());
        components.forEach(
                c -> {
                    System.out.println(
                            c.getClass().getCanonicalName() + " " +
                                    c.getRegisteredKeyStrokes().length + " " +
                                    Arrays.asList(c.getRegisteredKeyStrokes())
                    );
                }
        );
        assertTrue(
                components.stream().filter(ActionRegisterTest::isTDComponent)
                          .allMatch(c -> c.getRegisteredKeyStrokes().length == 0)
        );
    }

    private Object getFieldValue(Object parent, String fieldName) throws Exception {
        Field field = parent.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(parent);
    }

    private class WindowAdapter {

        private Window window = new Window();
        private TweetTextArea textArea;
        private TWPanel panel;
        private ScrollableTimelinePanel scrollableTimelinePanel;

        public WindowAdapter() {
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
